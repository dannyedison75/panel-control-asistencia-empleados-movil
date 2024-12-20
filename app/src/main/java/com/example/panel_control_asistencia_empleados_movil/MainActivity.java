package com.example.panel_control_asistencia_empleados_movil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asistify_mobile.R;

public class MainActivity extends AppCompatActivity {

    private ProgressBar carga;
    private int progressStatus = 0;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carga = findViewById(R.id.cargador);

        // Simular la carga con un hilo
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            carga.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(30); // Simula el tiempo de carga
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Iniciar la siguiente actividad
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }).start();
    }
}