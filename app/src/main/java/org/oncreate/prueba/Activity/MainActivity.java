package org.oncreate.prueba.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.DataBlock;
import com.johnhiott.darkskyandroidlib.models.DataPoint;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import org.oncreate.prueba.Base.AppController;
import org.oncreate.prueba.Base.GPSTracker;
import org.oncreate.prueba.R;
import org.oncreate.prueba.Utils.AndroidUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends GPSTracker {

    TextView tempAct, tempL, tempM, tempMi, tempJ, tempV, fecha, veloc, humedad, ciudad, direccion,texto;
    LinearLayout panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempAct = (TextView) findViewById(R.id.text_temp);
        fecha = (TextView) findViewById(R.id.text_fecha);
        ciudad = (TextView) findViewById(R.id.text_ciudad);
        texto= (TextView) findViewById(R.id.texto);
        veloc = (TextView) findViewById(R.id.text_Velocidad);
        humedad = (TextView) findViewById(R.id.text_humedad);
        direccion = (TextView) findViewById(R.id.text_direccion);
        panel = (LinearLayout) findViewById(R.id.panel);
        tempL = (TextView) findViewById(R.id.texttempL);
        tempM = (TextView) findViewById(R.id.texttempM);
        tempMi = (TextView) findViewById(R.id.texttempMi);
        tempJ = (TextView) findViewById(R.id.texttempJ);
        tempV = (TextView) findViewById(R.id.texttempV);


        recargar();

    }

    private void recargar() {

        if (buscando) {
            Toast.makeText(this, "Buscando ubicación... Espere por favor", Toast.LENGTH_SHORT).show();
            return;
        }


        if (longitudeGPS != 0.0) {

            panel.setVisibility(View.VISIBLE);
            texto.setVisibility(View.GONE);

            cargarDatos();



        } else {

            panel.setVisibility(View.GONE);
            texto.setVisibility(View.VISIBLE);

        }
    }


    private void cargarDatos() {



        RequestBuilder weather = new RequestBuilder();

        Request request = new Request();
        request.setLat(String.valueOf(latitudeGPS));
        request.setLng(String.valueOf(longitudeGPS));
        request.setUnits(Request.Units.US);
        request.setLanguage(Request.Language.SPANISH);
        request.addExcludeBlock(Request.Block.MINUTELY);
        request.addExcludeBlock(Request.Block.HOURLY);

        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
                //Do something
                Log.d(AppController.TAG, "Success" + response.toString());
                MostrarDatos(weatherResponse);

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(AppController.TAG, "Error while calling: " + retrofitError.getUrl());
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.refresh:
                cargarDatos();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void MostrarDatos(WeatherResponse weatherResponse) {

        DataPoint r = weatherResponse.getCurrently();


        tempAct.setText(String.valueOf(r.getTemperature() + "º"));
        String s = AndroidUtils.getDate(r.getTime());
        fecha.setText(s);

        this.getSupportActionBar().setTitle("Bogota");


        veloc.setText(r.getWindSpeed() + " MPH");
        int h = (int) (Double.valueOf(r.getHumidity()) * 100);
        humedad.setText(h + "%");


        DataBlock diario = weatherResponse.getDaily();


        for (int i = 0; diario.getData().size() > i; i++) {

            DataPoint day = diario.getData().get(i);
            String dayNum = AndroidUtils.getDateDayNum(day.getTime());
            if (!dayNum.equals("sáb.") || !dayNum.equals("dom.")){

                switch (dayNum) {
                    case "lun.":
                        tempL.setText(String.valueOf(day.getTemperatureMin()) + "º");
                        break;
                    case "mar.":
                        tempM.setText(String.valueOf(day.getTemperatureMin()) + "º");
                        break;
                    case "mié.":
                        tempMi.setText(String.valueOf(day.getTemperatureMin() + "º"));
                        break;
                    case "jue.":
                        tempJ.setText(String.valueOf(day.getTemperatureMin() + "º"));
                        break;
                    case "vie.":
                        tempV.setText(String.valueOf(day.getTemperatureMin() + "º"));
                        break;

                }}

        }

    }
}
