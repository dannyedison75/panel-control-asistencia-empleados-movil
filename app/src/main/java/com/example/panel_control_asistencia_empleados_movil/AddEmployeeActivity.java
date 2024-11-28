package com.example.panel_control_asistencia_empleados_movil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asistify_mobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddEmployeeActivity extends AppCompatActivity {

    // Variables para los campos EditText
    private EditText idEditText, nombreEditText, apellidoEditText, ciudadEditText, carreraEditText, edadEditText, emailEditText, nivelEducativoEditText;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        // Asignación de las vistas
        idEditText = findViewById(R.id.id);
        nombreEditText = findViewById(R.id.nombre);
        apellidoEditText = findViewById(R.id.apellido);
        ciudadEditText = findViewById(R.id.ciudad);
        carreraEditText = findViewById(R.id.carrera);
        edadEditText = findViewById(R.id.edad);
        emailEditText = findViewById(R.id.email);
        nivelEducativoEditText = findViewById(R.id.nivelEducativo);
        btnEnviar = findViewById(R.id.bL1);

        // Listener para el botón Enviar
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos del formulario
                String id = idEditText.getText().toString();
                String nombre = nombreEditText.getText().toString();
                String apellido = apellidoEditText.getText().toString();
                String ciudad = ciudadEditText.getText().toString();
                String carrera = carreraEditText.getText().toString();
                int edad = Integer.parseInt(edadEditText.getText().toString());
                String email = emailEditText.getText().toString();
                String nivelEducativo = nivelEducativoEditText.getText().toString();

                // Crear un mapa con los datos
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                String ids = db.collection("empleados").document().getId();
                Employee empleado = new Employee(id, nombre, apellido, ciudad, carrera, edad, email, nivelEducativo);

                db.collection("empleados").document(id).set(empleado)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(AddEmployeeActivity.this, "Usuario agregado", Toast.LENGTH_SHORT).show();
                            Intent listIntent = new Intent(AddEmployeeActivity.this, EmployeeListActivity.class);
                            startActivity(listIntent);
                            finish();
                        })
                        .addOnFailureListener(e -> Toast.makeText(AddEmployeeActivity.this, "Error al agregar usuario", Toast.LENGTH_SHORT).show());
            }
        });
    }
}


