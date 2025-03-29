package com.ecodeup.jdbc.Repositories;

import com.ecodeup.jdbc.Connection.SQLiteConnection;
import com.ecodeup.jdbc.Entities.CuentasEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

public class CuentasRepository implements Repository <CuentasEntity> {
    private static CuentasRepository instance;

    public CuentasRepository (){}

    public static CuentasRepository getInstanceOf(){
        if (instance == null){
            instance = new CuentasRepository();
        }
        return instance;
    }

    @Override
    public void save(CuentasEntity cuentasEntity) throws SQLException {
        String sql = "INSERT INTO cuentas (id_usuario, tipo, saldo, fecha_creacion) VALUES (?,?,?,?);";
        try(Connection connection = SQLiteConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,cuentasEntity.getId_usuario());
            preparedStatement.setString(2,cuentasEntity.getTipo().toString());
            preparedStatement.setDouble(3,cuentasEntity.getSaldo());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(cuentasEntity.getFecha_creacion()));
            preparedStatement.executeUpdate();
            System.out.println("Cuenta agregada con exito.");
        }catch (SQLException e){
            System.out.println("Error al agregar la cuenta.");
        }

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public ArrayList<CuentasEntity> findAll() {
        return null;
    }

    @Override
    public Optional<CuentasEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Integer count() {
        return 0;
    }
}
