package com.example.panel_control_asistencia_empleados_movil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> employees = new ArrayList<>();
    private EmployeePanelActivity activity;

    public EmployeeAdapter(EmployeePanelActivity activity) {
        this.activity = activity;
    }

    // Método para agregar empleados a la lista (de momento, datos de ejemplo)
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.nameTextView.setText(employee.getName());
        holder.positionTextView.setText(employee.getPosition());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public void filter(String text) {
        // Aquí debes implementar la lógica para filtrar la lista de empleados
        // según el texto de búsqueda.
        // Por ejemplo:
        List<Employee> filteredList = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getName().toLowerCase().contains(text.toLowerCase()) ||
                    employee.getPosition().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(employee);
            }
        }
        this.employees = filteredList;
        notifyDataSetChanged();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView positionTextView;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.employeeName);
            positionTextView = itemView.findViewById(R.id.employeePosition);
        }
    }
}