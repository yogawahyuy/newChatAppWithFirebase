package com.rsip.mobile.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.rsip.mobile.MainActivity;
import com.rsip.mobile.R;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;

public class SplashScreenActivity extends AppCompatActivity {

    private AppUpdateManager mUpdateManager;
    AppUpdateManager appUpdateManager;
    private FakeAppUpdateManager fakeAppUpdateManager;
    private static final int RC_UPDATE_APP = 530;
    InstallStateUpdatedListener installStateUpdatedListener;
    Task<AppUpdateInfo> appUpdateInfoTask;
    InAppUpdateManager inAppUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        inAppUpdateManager=InAppUpdateManager.Builder(this,RC_UPDATE_APP)
//                .resumeUpdates(true)
//                .mode(Constants.UpdateMode.IMMEDIATE)
//                .snackBarMessage("Update berhasil di download")
//                .snackBarAction("Muat Ulang")
//                .handler(this);
//        inAppUpdateManager = InAppUpdateManager.Builder(this,RC_UPDATE_APP)
//                .resumeUpdates(true)
//                .mode(Constants.UpdateMode.IMMEDIATE);
//        inAppUpdateManager.checkForAppUpdate();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, StartActivity.class));
                finish();
            }
        },1000);

    }


    private void updateCheck(){
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this);

// Returns an intent object that you use to check for an update.
        appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.

                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.FLEXIBLE,this,RC_UPDATE_APP);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }else {


            }
        });
    }


    private void checkUpdate(){
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState state) {
                if (state.installStatus() == InstallStatus.DOWNLOADED){
                    //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                    popupSnackbarForCompleteUpdate();
                } else if (state.installStatus() == InstallStatus.INSTALLED){
                    if (mUpdateManager != null){
                        mUpdateManager.unregisterListener(installStateUpdatedListener);
                    }

                } else {
                    Log.i("update aplikasi", "InstallStateUpdatedListener: state: " + state.installStatus());
                }
            }
        };
        mUpdateManager = AppUpdateManagerFactory.create(this);

        mUpdateManager.registerListener(installStateUpdatedListener);

        mUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)){

                try {
                    mUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, SplashScreenActivity.this, RC_UPDATE_APP);

                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                popupSnackbarForCompleteUpdate();
            } else {
                Log.e("update aplikasi", "checkForAppUpdateAvailability: something else");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_UPDATE_APP) {
            if (resultCode != RESULT_OK) {
                Log.e("update aplikasi", "onActivityResult: app download failed");
            }
        }

    }
    private void popupSnackbarForCompleteUpdate() {

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.relsplash),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mUpdateManager != null){
                mUpdateManager.completeUpdate();
            }
        });


        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
