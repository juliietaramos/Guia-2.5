package com.ecodeup.jdbc.Repositories;

import com.ecodeup.jdbc.Connection.SQLiteConnection;
import com.ecodeup.jdbc.Entities.CredencialesEntity;
import com.ecodeup.jdbc.Entities.CuentasEntity;
import com.ecodeup.jdbc.Enum.ENUM_permiso;
import com.ecodeup.jdbc.Enum.ENUM_tipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CredencialesRepository implements Repository<CredencialesEntity> {
    private static CredencialesRepository instance;

    public CredencialesRepository() {
    }

    public static CredencialesRepository getInstanceOf() {
        if (instance == null) {
            instance = new CredencialesRepository();
        }
        return instance;
    }

    @Override
    public void save(CredencialesEntity credencialesEntity) throws SQLException {
        String sql = "INSERT INTO credenciales (id_usuario , username , password , permiso) VALUES (?, ?, ?, ?)";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, credencialesEntity.getId_usuario());
            preparedStatement.setString(2, credencialesEntity.getUsername());
            preparedStatement.setString(3, credencialesEntity.getPassword());
            preparedStatement.setString(4, credencialesEntity.getPermiso().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar una credencial." + e.getMessage());
        }

    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM credenciales WHERE id_usuario = ?;";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int filas = preparedStatement.executeUpdate();
            System.out.println("Se han eliminado " + filas + " cuenta/s con exito.");
        } catch (SQLException e) {
            System.out.println("Ocurrio un error al momento de eliminar una cuenta " + e.getMessage());
        }

    }

    @Override
    public ArrayList<CredencialesEntity> findAll() {
        ArrayList<CredencialesEntity> credencialesEntityArrayList = new ArrayList<>();
        String sql = "SELECT * FROM credenciales;";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CredencialesEntity nuevo = new CredencialesEntity(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            ENUM_permiso.valueOf(resultSet.getString(5))); // Conversión de String a Enum
                    //resultSet.getString(3), // el dato en la bbd es de tipo string, pero la clase tiene atributo tipo enum
                    credencialesEntityArrayList.add(nuevo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar a las cuentas " + e.getMessage());
        }
        return credencialesEntityArrayList;
    }

    @Override
    public Optional<CredencialesEntity> findById(Integer id) {
        String sql = "SELECT * FROM credenciales WHERE id_usuario = ?;";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) { //PREGUNTAR COMO HAGO SI HAY + DE UNA CUENTA POR IDUSUARIo
                    return Optional.of(new CredencialesEntity(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            ENUM_permiso.valueOf(resultSet.getString(5))));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el usuario ingresado. " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("No se encontro el usuario ingresado. " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Integer count() {
        return 0;
    }
}
