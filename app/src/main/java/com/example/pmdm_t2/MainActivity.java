package com.example.pmdm_t2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
    }

    private void initializeViews() {
        setUpBtnConnectionClick();
        setUpBtnSensorClick();

        text = findViewById(R.id.txtText);

        setUpBtnSaveClick();
        setUpBtnLoadClick();
        setUpWebView();
    }

    private void setUpWebView() {
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

    private void setUpBtnLoadClick() {
        Button btnLoad = findViewById(R.id.btCargar);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View s) {
                SharedPreferences sharePref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
                String textComplete = sharePref.getString("datos", "");
                text.setText(textComplete);
                Toast.makeText(getApplicationContext(), "Datos cargados", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpBtnSaveClick() {
        Button btnSave = findViewById(R.id.btGuardar);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View c) {
                if (c.getId() == R.id.btGuardar) {
                    String textComplete = text.getText().toString();
                    SharedPreferences sharePref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharePref.edit();
                    edit.putString("datos", textComplete);
                    edit.apply();

                    Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setUpBtnSensorClick() {
        Button btSensor = findViewById(R.id.btSensor);
        btSensor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View z) {
                if (isThereSensor()) {
                    Toast.makeText(getApplicationContext(), "Existe el sensor acelerómetro", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No hay sensor acelerómetro", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void setUpBtnConnectionClick() {
        Button btnConnection = findViewById(R.id.btConexion);
        btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Hay conexión a internet", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexión a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isThereSensor() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        return (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null);
    }

    public boolean isConnected(Context context) {
        boolean connectivity = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            Network currentNetwork = connectivityManager.getActiveNetwork();
            if (currentNetwork != null) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork);
                if (networkCapabilities != null) {
                    connectivity = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                }
            }
        }

        return connectivity;
    }

}