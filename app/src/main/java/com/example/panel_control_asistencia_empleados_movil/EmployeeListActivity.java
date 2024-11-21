package com.example.panel_control_asistencia_empleados_movil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class EmployeePanelActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private TextInputEditText searchEditText;
    private EmployeeAdapter employeeAdapter;

    private static final int MENU_ITEM_EMPLOYEES = R.id.menu_item_employees;
    private static final int MENU_ITEM_ADD = R.id.menu_item_add;
    private static final int MENU_ITEM_CONFIG = R.id.menu_item_config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_panel);

        // Inicializar Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Inicializar BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == MENU_ITEM_EMPLOYEES) {
                    // Acciones para la vista de empleados
                    Intent intentEmployees = new Intent(EmployeePanelActivity.this, EmployeeListActivity.class);
                    startActivity(intentEmployees);
                } else if (item.getItemId() == MENU_ITEM_ADD) {
                    // Acciones para la vista de agregar empleados
                    Intent intentAdd = new Intent(EmployeePanelActivity.this, AddEmployeeActivity.class);
                    startActivity(intentAdd);
                } else if (item.getItemId() == MENU_ITEM_CONFIG) {
                    // Acciones para la vista de configuración
                    Intent intentConfig = new Intent(EmployeePanelActivity.this, ConfigActivity.class);
                    startActivity(intentConfig);
                }
                return true; // Regresa "true" para indicar que el elemento se seleccionó
            }
        });

    }
}