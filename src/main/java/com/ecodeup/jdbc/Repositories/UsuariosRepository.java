package com.ecodeup.jdbc.Repositories;

import com.ecodeup.jdbc.Connection.SQLiteConnection;
import com.ecodeup.jdbc.Entities.UsuariosEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class UsuariosRepository implements Repository<UsuariosEntity> {
    private static UsuariosRepository instance;

    public UsuariosRepository(){}

    public static UsuariosRepository getInstanceOf(){
        if (instance == null){
            instance = new UsuariosRepository();
        }
        return instance;
    }

    @Override
    public void save(UsuariosEntity usuariosEntity) throws SQLException {
        /*try (Connection connection = SQLiteConeccion.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("INSERT INTO alumnos (nombre , apellido , edad , email) VALUES(?, ?, ?, ?)")) {
            preparedStatement.setString(1, alumnosEntidad.getNombre());
            preparedStatement.setString(2, alumnosEntidad.getApellido());
            preparedStatement.setInt(3, alumnosEntidad.getEdad());
            preparedStatement.setString(4, alumnosEntidad.getEmail());
            preparedStatement.executeUpdate();*/
        String sql = "INSERT INTO usuarios (nombre, apellido, dni, email, fecha_creacion) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,usuariosEntity.getNombre());
            ps.setString(2,usuariosEntity.getApellido());
            ps.setString(3,usuariosEntity.getDni());
            ps.setString(4,usuariosEntity.getEmail());
            ps.setTimestamp(5, Timestamp.valueOf(usuariosEntity.getFechaDeCreacion()));
            ps.executeUpdate();
            System.out.println("El usuario se ha agregado con exito.");
        }catch (SQLException e){
            System.out.println("Error al agregar un usuario al sistema." + e.getMessage());
        }

    }

    public UsuariosEntity ultimoUsuarioRegistrado(){
        String sql = "SELECT * from usuarios order by id_usuario desc limit 1";
        try (Connection connection = SQLiteConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                UsuariosEntity usuarios = new UsuariosEntity(
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("dni"),
                        resultSet.getString("email"),
                        resultSet.getTimestamp("fecha_creacion").toLocalDateTime()); //fechacrecion es localdatetime
                return usuarios;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public ArrayList<UsuariosEntity> findAll() {
        return null;
    }

    @Override
    public Optional<UsuariosEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Integer count() {
        return 0;
    }
}
