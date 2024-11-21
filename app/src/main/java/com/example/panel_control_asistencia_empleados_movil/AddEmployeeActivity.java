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
    private EditText nombreEditText, apellidoEditText, carreraEditText, edadEditText, emailEditText, nivelEducativoEditText;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        // Asignación de las vistas
        nombreEditText = findViewById(R.id.nombre);
        apellidoEditText = findViewById(R.id.apellido);
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
                String nombre = nombreEditText.getText().toString();
                String apellido = apellidoEditText.getText().toString();
                String carrera = carreraEditText.getText().toString();
                String edad = edadEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String nivelEducativo = nivelEducativoEditText.getText().toString();

                // Crear un mapa con los datos
                Map<String, Object> nuevoEmpleado = new HashMap<>();
                nuevoEmpleado.put("nombre", nombre);
                nuevoEmpleado.put("apellido", apellido);
                nuevoEmpleado.put("carrera", carrera);
                nuevoEmpleado.put("edad", Integer.parseInt(edad));
                nuevoEmpleado.put("email", email);
                nuevoEmpleado.put("nivelEducativo", nivelEducativo);

                // Agregar el nuevo empleado a Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("empleados")
                        .add(nuevoEmpleado)
                        .addOnSuccessListener(documentReference -> {
                            // Si es exitoso, redirigir a la lista de empleados
                            Intent listIntent = new Intent(AddEmployeeActivity.this, EmployeeListActivity.class);
                            startActivity(listIntent);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            // Si ocurre un error
                            Toast.makeText(AddEmployeeActivity.this, "Error al agregar empleado: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            }
        });
    }
}
