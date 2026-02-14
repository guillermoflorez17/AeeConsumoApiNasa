package com.eusa.miapinasa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ImageView imgApod;
    private TextView tvTitle, tvDate, tvExplanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pillamos los componentes del layout
        imgApod = findViewById(R.id.imgApod);
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvExplanation = findViewById(R.id.tvExplanation);

        // Mensaje inicial mientras carga
        tvTitle.setText("Cargando...");
        tvDate.setText("");
        tvExplanation.setText("");

        // La llamada a internet no se puede hacer en el hilo principal
        // Así que lo hago en un Thread para que no explote la app
        new Thread(() -> {
            try {
                NasaApodClient client = new NasaApodClient();
                JSONObject apodData = client.getApodData();

                // Sacamos lo importante del JSON
                String title = apodData.optString("title", "Sin título");
                String date = apodData.optString("date", "Sin fecha");
                String explanation = apodData.optString("explanation", "Sin explicación");
                String imageUrl = apodData.optString("url", "");

                // Para tocar la UI hay que volver al hilo principal
                runOnUiThread(() -> {
                    tvTitle.setText(title);
                    tvDate.setText(date);
                    tvExplanation.setText(explanation);

                    // Cargamos la imagen con Glide
                    if (!imageUrl.isEmpty()) {
                        Glide.with(MainActivity.this)
                                .load(imageUrl)
                                .into(imgApod);
                    }
                });

            } catch (Exception e) {
                // Si algo falla, lo mostramos en pantalla para enterarnos rápido
                runOnUiThread(() -> {
                    tvTitle.setText("Error");
                    tvDate.setText("");
                    tvExplanation.setText(e.getMessage());
                });
            }
        }).start();
    }
}
