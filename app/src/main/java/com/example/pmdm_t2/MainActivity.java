package com.example.pmdm_t2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.view.View;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
    }

    private void initializeViews() {
        Button btPulsandoConexion = (Button) findViewById(R.id.btConexion);
        btPulsandoConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hayConectividad(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Hay conexión a internet", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexión a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btPulsandoSensor = (Button) findViewById(R.id.btSensor);
        btPulsandoSensor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View z) {
                if (haySensor()) {
                    Toast.makeText(getApplicationContext(), "Existe el sensor acelerómetro", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No hay sensor acelerómetro", Toast.LENGTH_SHORT).show();
                }
            }

        });

        Button btPulsandoCargar = (Button) findViewById(R.id.btCargar);
        Button btPulsandoGuardar = (Button) findViewById(R.id.btGuardar);

        EditText Texto = findViewById(R.id.txtText);

        btPulsandoGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View c) {
                if (c.getId() == R.id.btGuardar) {
                    String TextoCompleto = Texto.getText().toString();

                    SharedPreferences sharePref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharePref.edit();
                    edit.putString("datos", TextoCompleto);
                    edit.apply();

                    Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_LONG).show();

                }
            }
        });

        btPulsandoCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View s) {
                SharedPreferences sharePref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
                String TextoCompleto = sharePref.getString("datos", "");

                Texto.setText(TextoCompleto);

                Toast.makeText(getApplicationContext(), "Datos cargados", Toast.LENGTH_LONG).show();

            }

        });

        WebView myWebView = (WebView) findViewById(R.id.WebView);
        myWebView.clearCache(true);
        myWebView.clearHistory();
        myWebView.loadUrl("https://www.hbomax.com/es/es/series/urn:hbo:series:GXdbR_gOXWJuAuwEAACVH");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Uri webpage = Uri.parse("https://www.hbomax.com/es/es/series/urn:hbo:series:GXdbR_gOXWJuAuwEAACVH");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public boolean haySensor() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        return (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null);
    }

    public boolean hayConectividad(Context context) {
        boolean conectividad = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {

            Network redActual = connectivityManager.getActiveNetwork();
            if (redActual != null) {
                NetworkCapabilities propiedadesRed = connectivityManager.getNetworkCapabilities(redActual);
                if (propiedadesRed != null) {
                    if (propiedadesRed.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        conectividad = true;
                    }
                    if (propiedadesRed.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        conectividad = true;
                    }
                }
            }
        }
        return conectividad;
    }

}