package com.example.panel_control_asistencia_empleados_movil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

        // Declaración de variables para los campos de texto y botones
        private EditText txtUser, txtPass;
        private Button btnIngresar, btnRegistrar;

        // Credenciales predefinidas
        private static final String USERNAME = "admin";
        private static final String PASSWORD = "password123";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Se establece el layout para la actividad actual
            setContentView(R.layout.activity_login);

            // Inicialización de los elementos de la interfaz de usuario
            txtUser = findViewById(R.id.username);
            txtPass = findViewById(R.id.password);
            btnIngresar = findViewById(R.id.bL1);
            btnRegistrar = findViewById(R.id.btnRgr);

            // Configuración del listener para el botón de ingresar
            btnIngresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Llama al método para verificar las credenciales cuando se presiona el botón
                    verificarCredenciales();
                }
            });

            // Configuración del listener para el botón de registrar
            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crear un Intent para redirigir a la pantalla de registro (RegisterActivity)
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent); // Inicia la actividad de registro

                    // Finalizar la actividad actual para evitar que el usuario regrese al login
                    finish();
                }
            });

        }

        // Método para verificar las credenciales ingresadas
        private void verificarCredenciales() {

            // Obtener los valores ingresados en los campos de texto
            String username = txtUser.getText().toString();
            String password = txtPass.getText().toString();

            // Comprobar si el nombre de usuario y la contraseña coinciden con los valores predefinidos
            if (USERNAME.equals(username) && PASSWORD.equals(password)) {
                // Si las credenciales son correctas, mostrar un mensaje de éxito
                Toast.makeText(LoginActivity.this, "Ingreso exitoso", Toast.LENGTH_SHORT).show();

                // Crear un Intent para redirigir al usuario a la pantalla inicio
                Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                startActivity(intent); // Iniciar la nueva actividad

                // Finalizar la actividad actual para que no se pueda volver al login
                finish();
            } else {
                // Si las credenciales son incorrectas, mostrar un mensaje de error
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        }
    }
