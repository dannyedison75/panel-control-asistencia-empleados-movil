package com.example.panel_control_asistencia_empleados_movil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asistify_mobile.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private List<Employee> employees;
    private Context context;

    public EmployeeAdapter(List<Employee> employees, Context context) {
        this.employees = employees;
        this.context = context;
    }

    public void submitList(List<Employee> list) {
        if (employees != null) employees.clear();
        if (list != null) employees.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.nombre.setText(employee.nombre + " " + employee.apellido);
        holder.email.setText(employee.email);

        holder.btnEditar.setOnClickListener(v -> {
            Log.d("EmployeeAdapter", "ID del empleado a editar: " + employee.id); // <-- Agrega esta línea
            Intent intent = new Intent(context, EditEmployeeActivity.class);
            intent.putExtra("employee", employee);
            ((Activity) context).startActivityForResult(intent, 1);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            eliminarEmployee(employee.id);
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, email;
        ImageButton btnEditar, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreEmployee);
            email = itemView.findViewById(R.id.emailEmployee);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    private void eliminarEmployee(String employeeId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmar Eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar este empleado?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("empleados").document(employeeId)
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(context, "Empleado eliminado", Toast.LENGTH_SHORT).show();
                                employees.removeIf(employee -> employee.id.equals(employeeId));
                                notifyDataSetChanged();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(context, "Error al eliminar empleado: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                Log.e("EmployeeAdapter", "Error al eliminar empleado: ", e); // registra el error en el Log
                            });
                })
                .setNegativeButton("No", null)
                .show();
    }
}