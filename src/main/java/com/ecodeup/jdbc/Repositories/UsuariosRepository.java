package com.ecodeup.jdbc.Repositories;

import com.ecodeup.jdbc.Connection.SQLiteConnection;
import com.ecodeup.jdbc.Entities.UsuariosEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UsuariosRepository implements Repository<UsuariosEntity> {
    private static UsuariosRepository instance;

    public UsuariosRepository() {
    }

    public static UsuariosRepository getInstanceOf() {
        if (instance == null) {
            instance = new UsuariosRepository();
        }
        return instance;
    }

    @Override
    public void save(UsuariosEntity usuariosEntity) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellido, dni, email, fecha_creacion) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuariosEntity.getNombre());
            ps.setString(2, usuariosEntity.getApellido());
            ps.setString(3, usuariosEntity.getDni());
            ps.setString(4, usuariosEntity.getEmail());
            ps.setTimestamp(5, Timestamp.valueOf(usuariosEntity.getFechaDeCreacion()));
            ps.executeUpdate();
            System.out.println("El usuario se ha agregado con exito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar un usuario al sistema." + e.getMessage());
        }
    }

    public UsuariosEntity ultimoUsuarioRegistrado() {
        String sql = "SELECT * from usuarios order by id_usuario desc limit 1";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UsuariosEntity usuarios = new UsuariosEntity(
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("dni"),
                        resultSet.getString("email"),
                        resultSet.getTimestamp("fecha_creacion").toLocalDateTime()); //fechacrecion es localdatetime
                return usuarios;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?;";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int filas = preparedStatement.executeUpdate();
            System.out.println("Se eliminaron " + filas + " usuario/s.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario " + e.getMessage());
        }

    }

    @Override
    public ArrayList<UsuariosEntity> findAll() {
        ArrayList<UsuariosEntity> usuariosEntityList = new ArrayList<>();
        String sql = "SELECT * FROM usuarios;";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UsuariosEntity nuevo = new UsuariosEntity(
                            resultSet.getInt("id_usuario"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getString("dni"),
                            resultSet.getString("email"),
                            resultSet.getTimestamp("fecha_creacion").toLocalDateTime());
                    usuariosEntityList.add(nuevo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar a los usuarios " + e.getMessage());
        }
        return usuariosEntityList;
    }

    @Override
    public Optional<UsuariosEntity> findById(Integer id) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?;";
        try(Connection connection = SQLiteConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    return Optional.of(new UsuariosEntity(
                            resultSet.getInt("id_usuario"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getString("dni"),
                            resultSet.getString("email"),
                            resultSet.getTimestamp("fecha_creacion").toLocalDateTime()));
                } else {
                    return Optional.empty();
                }
            }
        }catch (SQLException e){
            System.out.println("Error al buscar el usuario ingresado. " + e.getMessage());
        }catch (NoSuchElementException e){
            System.out.println("No se encontro el usuario ingresado. " + e.getMessage());
        }
        return Optional.empty();
    }


}
