package com.example.panel_control_asistencia_empleados_movil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asistify_mobile.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditEmployeeActivity extends AppCompatActivity {

    // Variables para los campos EditText
    private EditText idEditText, nombreEditText, apellidoEditText, carreraEditText, edadEditText, emailEditText, nivelEducativoEditText;
    private Button btnEnviar;
    private FirebaseFirestore db;
    private Employee employee; //Para guardar el objeto empleado


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        db = FirebaseFirestore.getInstance();

        // Obtener el objeto Employee del Intent
        employee = getIntent().getParcelableExtra("employee");

        // Asignación de las vistas
        idEditText = findViewById(R.id.id);
        nombreEditText = findViewById(R.id.nombre);
        apellidoEditText = findViewById(R.id.apellido);
        carreraEditText = findViewById(R.id.carrera);
        edadEditText = findViewById(R.id.edad);
        emailEditText = findViewById(R.id.email);
        nivelEducativoEditText = findViewById(R.id.nivelEducativo);
        btnEnviar = findViewById(R.id.bL1);

        // Manejo de errores si no se recibe el empleado
        if (employee == null) {
            Toast.makeText(this, "Error al obtener datos del empleado", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad
            return;
        }

        // Rellenar los campos con los datos del objeto Employee
        idEditText.setText(employee.id);
        nombreEditText.setText(employee.nombre);
        apellidoEditText.setText(employee.apellido);
        carreraEditText.setText(employee.carrera);
        edadEditText.setText(String.valueOf(employee.edad));
        emailEditText.setText(employee.email);
        nivelEducativoEditText.setText(employee.nivelEducativo);

        // Listener para el botón Enviar (actualiza y redirige)
        btnEnviar.setOnClickListener(v -> actualizarEmpleado());
    }


    private void actualizarEmpleado() {
        // Obtener los nuevos datos de los campos ANTES de actualizar el objeto employee
        String nuevoId = idEditText.getText().toString();
        String nuevoNombre = nombreEditText.getText().toString();
        String nuevoApellido = apellidoEditText.getText().toString();
        String nuevaCarrera = carreraEditText.getText().toString();
        String nuevaEdadStr = edadEditText.getText().toString();
        String nuevoEmail = emailEditText.getText().toString();
        String nuevoNivelEducativo = nivelEducativoEditText.getText().toString();

        //Validación básica de datos
        if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevaCarrera.isEmpty() || nuevoEmail.isEmpty() || nuevoNivelEducativo.isEmpty() || nuevaEdadStr.isEmpty()){
            Toast.makeText(this, "Por favor, llene todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        long nuevaEdad;
        try {
            nuevaEdad = Long.parseLong(nuevaEdadStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Edad debe ser un número entero.", Toast.LENGTH_SHORT).show();
            return; //Detener la ejecución si hay un error de formato
        }

        //Actualizar el objeto employee CON los nuevos datos
        employee.id = nuevoId;
        employee.nombre = nuevoNombre;
        employee.apellido = nuevoApellido;
        employee.carrera = nuevaCarrera;
        employee.edad = nuevaEdad;
        employee.email = nuevoEmail;
        employee.nivelEducativo = nuevoNivelEducativo;

        // Actualizar los datos en Firestore usando update()
        DocumentReference empleadoRef = db.collection("empleados").document(employee.id);
        Map<String, Object> updates = new HashMap<>();
        updates.put("id", employee.id);
        updates.put("nombre", employee.nombre);
        updates.put("apellido", employee.apellido);
        updates.put("carrera", employee.carrera);
        updates.put("edad", employee.edad);  // Asegúrate de que 'edad' es de tipo Number en Firestore
        updates.put("email", employee.email);
        updates.put("nivelEducativo", employee.nivelEducativo);

        empleadoRef.update(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Empleado actualizado correctamente", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al actualizar empleado: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}