package com.example.panel_control_asistencia_empleados_movil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asistify_mobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    // Declaración de variables para los campos de texto y botones
    private EditText txtUser, txtPass;
    private Button btnIngresar, btnRegistrar;

    private FirebaseAuth mAuth; // Para autenticar al usuario
    private FirebaseFirestore db; // Para acceder a Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Asegúrate de que tu layout esté configurado correctamente

        // Inicialización de las variables de la interfaz de usuario
        txtUser = findViewById(R.id.username);  // Asegúrate de que el ID coincida con el layout
        txtPass = findViewById(R.id.password);  // Asegúrate de que el ID coincida con el layout
        btnIngresar = findViewById(R.id.bL1);   // ID del botón de ingresar
        btnRegistrar = findViewById(R.id.btnRgr); // ID del botón de registrar

        // Inicializar FirebaseAuth y FirebaseFirestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Configurar el listener para el botón de ingresar
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar al método para verificar las credenciales
                verificarCredenciales();
                // Crear un Intent para redirigir a la pantalla de registro
                Intent intent = new Intent(LoginActivity.this, EmployeeListActivity.class);
                startActivity(intent);  // Inicia la actividad de registro
                finish();  // Finaliza la actividad de login para que no se pueda regresar
            }
        });

        // Configuración del listener para el botón de registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para redirigir a la pantalla de registro
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);  // Inicia la actividad de registro
                finish();  // Finaliza la actividad de login para que no se pueda regresar
            }
        });
    }

    // Método para verificar las credenciales ingresadas
    private void verificarCredenciales() {
        // Obtener los valores ingresados en los campos de texto
        String email = txtUser.getText().toString();
        String password = txtPass.getText().toString();

        // Verificar que los campos no estén vacíos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Por favor ingrese correo y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        // Iniciar sesión con Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // El inicio de sesión fue exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Verificar si el usuario es un administrador
                            verificarAdmin(user.getEmail());
                        }
                    } else {
                        // Si la autenticación falla, mostrar un mensaje de error
                        Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para verificar si el usuario es un administrador en Firestore
    private void verificarAdmin(String email) {
        // Buscar al administrador en Firestore usando el correo
        db.collection("administradores")
                .whereEqualTo("correo", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        // Si se encontró un administrador con ese correo
                        QuerySnapshot result = task.getResult();
                        for (QueryDocumentSnapshot document : result) {
                            // Aquí puedes obtener los datos del administrador si es necesario
                            String nombre = document.getString("nombre");
                            String apellido = document.getString("apellido");

                            // Mostrar un mensaje de éxito
                            Toast.makeText(LoginActivity.this, "Ingreso exitoso como " + nombre, Toast.LENGTH_SHORT).show();

                            // Redirigir al panel del empleado
                            Intent intent = new Intent(LoginActivity.this, EmployeeListActivity.class);
                            startActivity(intent);
                            finish(); // Finalizar la actividad de login
                        }
                    } else {
                        // Si no se encuentra el administrador, mostrar mensaje de error
                        Toast.makeText(LoginActivity.this, "No es un administrador", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}