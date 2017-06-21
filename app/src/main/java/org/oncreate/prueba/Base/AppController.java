package org.oncreate.prueba.Base;

import android.app.Application;

import com.johnhiott.darkskyandroidlib.ForecastApi;

/**
 * Created by azulandres92 on 6/21/17.
 */

public class AppController extends Application {

    public static final String TAG = "Weather";


    @Override
    public void onCreate() {
        super.onCreate();
        ForecastApi.create("c15cb78b36c66ce3ee098d7f99b72465");
    }



}
