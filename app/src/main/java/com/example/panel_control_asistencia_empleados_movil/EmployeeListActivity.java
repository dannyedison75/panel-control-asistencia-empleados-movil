package com.example.panel_control_asistencia_empleados_movil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asistify_mobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private EmployeeAdapter employeeAdapter;
    private List<Employee> employeeList = new ArrayList<>();

    private static final int MENU_ITEM_EMPLOYEES = R.id.menu_item_employees;
    private static final int MENU_ITEM_ADD = R.id.menu_item_add;
    private static final int MENU_ITEM_CONFIG = R.id.menu_item_config;

    private EditText searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        searchEditText = findViewById(R.id.searchEditText); // Initialize EditText
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita acción antes del cambio de texto
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filtrar la lista mientras se escribe
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita acción después del cambio de texto
            }
        });

        // Inicializar Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        employeeAdapter = new EmployeeAdapter(employeeList, this);
        recyclerView.setAdapter(employeeAdapter);

        // Cargar empleados desde Firestore
        cargarEmpleados();

        // Inicializar BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == MENU_ITEM_EMPLOYEES) {
                    // Acciones para la vista de empleados
                    Intent intentEmployees = new Intent(EmployeeListActivity.this, EmployeeListActivity.class);
                    startActivity(intentEmployees);
                } else if (item.getItemId() == MENU_ITEM_ADD) {
                    // Acciones para la vista de agregar empleados
                    Intent intentAdd = new Intent(EmployeeListActivity.this, AddEmployeeActivity.class);
                    startActivity(intentAdd);
                } else if (item.getItemId() == MENU_ITEM_CONFIG) {
                    // Acciones para la vista de configuración
                    Intent intentConfig = new Intent(EmployeeListActivity.this, ConfigActivity.class);
                    startActivity(intentConfig);
                }
                return true;
            }
        });
    }

    private void filterList(String text) {
        List<Employee> filteredList = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee.getNombre().toLowerCase().contains(text.toLowerCase())) { //Comparación insensible a mayúsculas y minúsculas
                filteredList.add(employee);
            }
        }
        employeeAdapter.submitList(filteredList); // Actualiza el RecyclerView con la lista filtrada
    }

    private void cargarEmpleados() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("empleados")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot value,
                                        @javax.annotation.Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore", "Error al cargar empleados", error);
                            Toast.makeText(EmployeeListActivity.this, "Error al cargar empleados", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (value != null && !value.isEmpty()) {
                            List<Employee> employees = value.toObjects(Employee.class);
                            employeeAdapter.submitList(employees); // Actualizar la lista del adapter
                        } else {
                            Log.e("Firestore", "No se encontraron empleados en la colección.");
                        }
                    }
                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            cargarEmpleados(); // Recarga la lista después de editar un empleado
        }
    }
}
