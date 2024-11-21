package com.example.panel_control_asistencia_empleados_movil;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable {
    public String id;
    public String nombre;
    public String apellido;
    public String carrera;
    public long edad;
    public String email;
    public String nivelEducativo;

    // Constructor vacío necesario para Firebase
    public Employee() {}

    public Employee(String id, String nombre, String apellido, String carrera, long edad, String email, String nivelEducativo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.carrera = carrera;
        this.edad = edad;
        this.email = email;
        this.nivelEducativo = nivelEducativo;
    }

    protected Employee(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        carrera = in.readString();
        edad = in.readLong();
        email = in.readString();
        nivelEducativo = in.readString();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    // Getters y Setters (Ya los tienes)

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(carrera);
        dest.writeLong(edad);
        dest.writeString(email);
        dest.writeString(nivelEducativo);
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public long getEdad() {
        return edad;
    }

    public void setEdad(long edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNivelEducativo() {
        return nivelEducativo;
    }

    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}