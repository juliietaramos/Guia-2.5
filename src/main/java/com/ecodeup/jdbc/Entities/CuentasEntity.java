package com.ecodeup.jdbc.Entities;

import com.ecodeup.jdbc.Enum.ENUM_tipo;

import java.time.LocalDateTime;
import java.util.Objects;

public class CuentasEntity {
    private int id;
    private int id_usuario;
    private ENUM_tipo tipo;
    private Double saldo;
    private LocalDateTime fecha_creacion;

    public CuentasEntity(int id_usuario, ENUM_tipo tipo, Double saldo, LocalDateTime fecha_creacion) {
        this.id_usuario = id_usuario;
        this.tipo = tipo;
        this.saldo = saldo;
        this.fecha_creacion = fecha_creacion;
    }

    public CuentasEntity(int id_usuario, ENUM_tipo tipo, Double saldo) {
        this.id_usuario = id_usuario;
        this.tipo = tipo;
        this.saldo = saldo;
        this.fecha_creacion = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public ENUM_tipo getTipo() {
        return tipo;
    }

    public void setTipo(ENUM_tipo tipo) {
        this.tipo = tipo;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CuentasEntity cuentas = (CuentasEntity) o;
        return id == cuentas.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cuentas{" +
                "id=" + id +
                ", id_usuario=" + id_usuario +
                ", tipo=" + tipo +
                ", saldo=" + saldo +
                ", fecha_creacion=" + fecha_creacion +
                '}';
    }
}
