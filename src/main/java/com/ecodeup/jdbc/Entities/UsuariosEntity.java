package com.ecodeup.jdbc.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsuariosEntity { //tabla usuarios
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private LocalDateTime fechaDeCreacion;
    private CredencialesEntity credencial; //un dato tipo credencial de la tabla credenciales
    private List<CuentasEntity> listaDeCuentas; //una lista de datos de tipo cuentas de la tabla cuentas

    public UsuariosEntity(int id, String nombre, String apellido, String dni, String email, LocalDateTime fechaDeCreacion, CredencialesEntity credencial, List<CuentasEntity> listaDeCuentas) {
        //metodo para traerme los usuarios de la bbd
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaDeCreacion = fechaDeCreacion;
        this.credencial = credencial;
        this.listaDeCuentas = listaDeCuentas;

    }

    public UsuariosEntity(String nombre, String apellido, String dni, String email, CredencialesEntity credencial) {
        //metodo para enviar los usuarios a la bbd
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaDeCreacion = LocalDateTime.now();
        this.credencial = credencial;
        this.listaDeCuentas = new ArrayList<>();
    }

    public UsuariosEntity() {
        listaDeCuentas=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public CredencialesEntity getCredencial() {
        return credencial;
    }

    public void setCredencial(CredencialesEntity credencial) {
        this.credencial = credencial;
    }

    public List<CuentasEntity> getListaDeCuentas() {
        return listaDeCuentas;
    }

    public void setListaDeCuentas(List<CuentasEntity> listaDeCuentas) {
        this.listaDeCuentas = listaDeCuentas;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", fechaDeCreacion=" + fechaDeCreacion +
                ", credencial=" + credencial.toString() +
                ", cuentas=" + listaDeCuentas.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UsuariosEntity usuarios = (UsuariosEntity) o;
        return id == usuarios.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
