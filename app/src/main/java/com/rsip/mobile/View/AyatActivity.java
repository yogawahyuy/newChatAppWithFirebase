package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rsip.mobile.Adapter.AyatAdapter;
import com.rsip.mobile.Model.AyatModel;
import com.rsip.mobile.R;
import com.rsip.mobile.Utils.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AyatActivity extends AppCompatActivity {
    Intent intent;
    int idSurah;
    RecyclerView recyclerView;
    ArrayList<AyatModel> ayatModels=new ArrayList<>();
    ArrayList<AyatModel> modelsAyat=new ArrayList<>();
    AyatAdapter ayatAdapter;
    JsonUtil jsonUtil=new JsonUtil();
    ProgressDialog progressDialog;
    TextView textTitle,tvTypeSurah,tvTranslation,tvJumlahAyat;
    private ImageView imagePlayPause;
    private TextView textCurentTime,textTotalDuration;
    private SeekBar playerSeekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler=new Handler();
    AyatModel ayatModel=new AyatModel();
    String urlMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayat);
        recyclerView=findViewById(R.id.ayat_recylcer);
        textTitle=findViewById(R.id.text_title);
        tvTypeSurah=findViewById(R.id.tv_typesurah);
        tvTranslation=findViewById(R.id.tv_translation);
        tvJumlahAyat=findViewById(R.id.tv_jumlahayat);
        imagePlayPause=findViewById(R.id.playimage);
        textCurentTime=findViewById(R.id.textCurrentTime);
        textTotalDuration=findViewById(R.id.totalDuration);
        playerSeekBar=findViewById(R.id.playerSeekbar);
        mediaPlayer=new MediaPlayer();
        playerSeekBar.setMax(100);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        intent=getIntent();
        idSurah=intent.getIntExtra("idposisi",2);
        toAdapter();
        progresDialog();
        jsonUtil.getAyat(this, ayatAdapter,ayatModels,progressDialog,idSurah,textTitle,tvTypeSurah,tvTranslation,tvJumlahAyat);
        jsonUtil.getAyat(this,ayatAdapter,ayatModels,idSurah);

        getRectitation(this,idSurah);
        imagePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    imagePlayPause.setImageResource(R.drawable.play);
                }else{
                    mediaPlayer.start();
                    imagePlayPause.setImageResource(R.drawable.pause);
                    updateSeekBar();
                }


            }
        });

        playerSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar=(SeekBar) v;
                int playPosition=(mediaPlayer.getDuration()/100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                textCurentTime.setText(millisSecondToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playerSeekBar.setSecondaryProgress(percent);
            }
        });

    }
    public void toAdapter(){
        ayatAdapter=new AyatAdapter(this,ayatModels);
        recyclerView.setAdapter(ayatAdapter);
    }
    private void progresDialog() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mengambil Ayat");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        handler.removeCallbacks(updater);
        mediaPlayer.stop();
        mediaPlayer.release();
        finish();
    }


    private void prepareMediaPlayer(String url){
        try{
            //urlMusic="https://download.quranicaudio.com/quran/abdurrahmaan_as-sudays/001.mp3";
            //urlMusic=ayatModel.getRectiation().toString();
            Log.d("urlmusic", "prepareMediaPlayer: "+urlMusic );
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(urlMusic);
            mediaPlayer.prepare();
            textTotalDuration.setText(millisSecondToTimer(mediaPlayer.getDuration()));
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration=mediaPlayer.getCurrentPosition();
            textCurentTime.setText(millisSecondToTimer(currentDuration));
        }
    };

    private void updateSeekBar(){
        if (mediaPlayer.isPlaying()){
            playerSeekBar.setProgress((int)(((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration())*100));
            handler.postDelayed(updater,1000);
        }
    }

    private String millisSecondToTimer(long millisecond){
        String timerString="";
        String secondsString;

        int hours=(int)(millisecond / (1000*60*60));
        int minutes=(int)(millisecond % (1000*60*60)) / (1000*60);
        int seconds=(int) ((millisecond % (1000*60*60)) % (1000*60) / 1000);

        if (hours>0){
            timerString=hours+":";
        }
        if (seconds<10){
            secondsString="0"+seconds;
        }else {
            secondsString=""+seconds;
        }

        timerString=timerString + minutes + ":" +secondsString;

        return timerString;
    }
    private void getRectitation(Context context,int idSurah){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://raw.githubusercontent.com/penggguna/QuranJSON/master/surah/" + idSurah + ".json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length()>0){
                    try{
                        JSONArray recitation=response.getJSONArray("recitations");
                        for (int i = 0; i <recitation.length() ; i++) {
                            JSONObject data=recitation.getJSONObject(i);
                            ayatModel.setNameRectitation(data.getString("name"));
                            ayatModel.setRectiation(data.getString("audio_url"));
                            urlMusic=data.getString("audio_url");
                            prepareMediaPlayer(data.getString("audio_url"));
                            modelsAyat.add(ayatModel);
                        }
                       // urlMusic=ayatModel.getRectiation();
                        Log.d("helooayat", "onCreate: "+ayatModel.getRectiation());
                        Log.d("helooayat1", "onCreate: "+urlMusic);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
}
