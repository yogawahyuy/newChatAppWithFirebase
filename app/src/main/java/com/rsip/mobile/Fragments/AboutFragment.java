package com.rsip.mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rsip.mobile.Model.AboutModel;
import com.rsip.mobile.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    View rootView;
    TextView headlineVisi,descVisi,headlineMisi,descMisi,headlineFalsafah,descFalsafah, headlineKontak, descKontak;
    ArrayList<AboutModel> aboutModels=new ArrayList<>();
    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_about, container, false);
        headlineVisi=rootView.findViewById(R.id.visi);
        descVisi=rootView.findViewById(R.id.visiDesc);

        headlineMisi=rootView.findViewById(R.id.misi);
        descMisi=rootView.findViewById(R.id.misiDesc);

        headlineFalsafah=rootView.findViewById(R.id.falsafah);
        descFalsafah=rootView.findViewById(R.id.falsafahDesc);

        headlineKontak=rootView.findViewById(R.id.kontak);
        descKontak=rootView.findViewById(R.id.kontakDesc);

        aboutModels.add(new AboutModel("Visi Rumah Sakit Islam Purwokerto","Rumah Sakit pilihan utama masyarakat yang berpusat pada pasien dengan mengutamakan mutu dan keselamatan pasien. "));
        headlineVisi.setText(aboutModels.get(0).getHeadline());
        descVisi.setText(aboutModels.get(0).getDeskripsi());

        aboutModels.add(new AboutModel("Misi Rumah Sakit Islam Purwokerto","1. memberikan pelayanan kesehatan prima secara paripurna, sesuai kebutuhan, dan tidak diskriminatif;\n" +
                "\n" +
                "2. pengelolaan aset secara efektif dan efisien dengan mensinergikan kesejahteraan sumber daya manusia, organisasi, ilmu pengetahuan, dan teknologi;\n" +
                "\n" +
                "3. menjalin dan mengembangkan jejaring (networking) baik dengan instansi pemerintah maupun swasta; dan\n" +
                "\n" +
                "4. menjalankan fungsi pendidikan dan pelatihan untuk internal maupun eksternal."));
        headlineMisi.setText(aboutModels.get(1).getHeadline());
        descMisi.setText(aboutModels.get(1).getDeskripsi());


        aboutModels.add(new AboutModel("Falsafah Rumah Sakit Islam Purwokerto","Falsafah Rumah Sakit Islam Purwokerto adalah Wa idza maridltu fahuwa yasyfiin (Al-Qur'an surat Asyua'ara ayat 80)"));
        headlineFalsafah.setText(aboutModels.get(2).getHeadline());
        descFalsafah.setText(aboutModels.get(2).getDeskripsi());

        aboutModels.add(new AboutModel("Kontak Rumah Sakit Islam Purwokerto ","Informasi  (0281) 630019\n" +
                "Facsimile  (0281) 635394\n" +
                "Email rsislam.purwokerto@gmail.com\n" +
                "WA +62 858-7503-5300\n"+
                "Jam Kunjungan Pasien Rawat Inap (Jam Besuk Pasien)\n" +
                        "Siang : 11.00 sampai dengan 13.00\n" +
                        "Sore   : 17.00 sampai dengan 20.00"
        ));
        headlineKontak.setText(aboutModels.get(3).getHeadline());
        descKontak.setText(aboutModels.get(3).getDeskripsi());



        return  rootView;
    }



}
