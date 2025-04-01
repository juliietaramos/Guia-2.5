package com.ecodeup.jdbc.Repositories;

import com.ecodeup.jdbc.Connection.SQLiteConnection;
import com.ecodeup.jdbc.Entities.CuentasEntity;
import com.ecodeup.jdbc.Entities.UsuariosEntity;
import com.ecodeup.jdbc.Enum.ENUM_tipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CuentasRepository implements Repository<CuentasEntity> {
    private static CuentasRepository instance;

    public CuentasRepository() {
    }

    public static CuentasRepository getInstanceOf() {
        if (instance == null) {
            instance = new CuentasRepository();
        }
        return instance;
    }

    @Override
    public void save(CuentasEntity cuentasEntity) throws SQLException {
        String sql = "INSERT INTO cuentas (id_usuario, tipo, saldo, fecha_creacion) VALUES (?,?,?,?);";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cuentasEntity.getId_usuario());
            preparedStatement.setString(2, cuentasEntity.getTipo().toString());
            preparedStatement.setDouble(3, cuentasEntity.getSaldo());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(cuentasEntity.getFecha_creacion()));
            preparedStatement.executeUpdate();
            System.out.println("Cuenta agregada con exito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar la cuenta.");
        }

    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM cuentas WHERE id_usuario = ?;";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int filas = preparedStatement.executeUpdate();
            System.out.println("Se eliminaron " + filas + " cuenta/s correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar la/s cuenta/s " + e.getMessage());
        }
    }

    @Override
    public ArrayList<CuentasEntity> findAll() {
        ArrayList<CuentasEntity> cuentasEntityArrayList = new ArrayList<>();
        String sql = "SELECT * FROM cuentas;";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CuentasEntity nuevo = new CuentasEntity(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            ENUM_tipo.valueOf(resultSet.getString(3)), // Conversión de String a Enum
                            //resultSet.getString(3), // el dato en la bbd es de tipo string, pero la clase tiene atributo tipo enum
                            resultSet.getDouble(4),
                            resultSet.getTimestamp(5).toLocalDateTime());
                    cuentasEntityArrayList.add(nuevo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar a las cuentas " + e.getMessage());
        }
        return cuentasEntityArrayList;
    }

    @Override
    public Optional<CuentasEntity> findById(Integer id) {
        String sql = "SELECT * FROM cuentas WHERE id_cuenta = ?;";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) { //PREGUNTAR COMO HAGO SI HAY + DE UNA CUENTA POR IDUSUARIo
                    return Optional.of(new CuentasEntity(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            ENUM_tipo.valueOf(resultSet.getString(3)),
                            resultSet.getDouble(4),
                            resultSet.getTimestamp(5).toLocalDateTime()));
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

    public List<CuentasEntity> findAllById(Integer id_usuario) {
        String sql = "SELECT * FROM cuentas WHERE id_usuario = ?;";
        List<CuentasEntity> listaDeCuentas = new ArrayList<>();
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_usuario);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    listaDeCuentas.add(new CuentasEntity(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            ENUM_tipo.valueOf(resultSet.getString(3)),
                            resultSet.getDouble(4),
                            resultSet.getTimestamp(5).toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el usuario ingresado. " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("No se encontro el usuario ingresado. " + e.getMessage());
        }
        return listaDeCuentas;
    }


}
