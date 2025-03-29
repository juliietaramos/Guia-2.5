package com.ecodeup.jdbc.Entities;

import com.ecodeup.jdbc.Enum.ENUM_permiso;

import java.util.Objects;

public class CredencialesEntity {
    private int id;
    private int id_usuario;
    private String username;
    private String password;
    private ENUM_permiso permiso;

    public CredencialesEntity(int id_usuario, String username, String password, ENUM_permiso permiso) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.password = password;
        this.permiso = permiso;
    }

    public CredencialesEntity (){}

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ENUM_permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(ENUM_permiso permiso) {
        this.permiso = permiso;
    }

    @Override
    public String toString() {
        return "CredencialesEntity{" +
                "id=" + id +
                ", id_usuario=" + id_usuario +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permiso=" + permiso +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredencialesEntity that = (CredencialesEntity) o;
        return id == that.id && id_usuario == that.id_usuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, id_usuario);
    }
}
