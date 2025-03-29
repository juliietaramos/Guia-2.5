package com.ecodeup.jdbc.Repositories;

import com.ecodeup.jdbc.Connection.SQLiteConnection;
import com.ecodeup.jdbc.Entities.CredencialesEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CredencialesRepository implements Repository <CredencialesEntity> {
    private static CredencialesRepository instance;

    public CredencialesRepository (){
    }

    public static CredencialesRepository getInstanceOf(){
        if (instance == null){
            instance = new CredencialesRepository();
        }
        return instance;
    }
    @Override
    public void save(CredencialesEntity credencialesEntity) throws SQLException {
        String sql = "INSERT INTO credenciales (id_usuario , username , password , permiso) VALUES (?, ?, ?, ?)";
        try(Connection connection = SQLiteConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,credencialesEntity.getId_usuario());
            preparedStatement.setString(2,credencialesEntity.getUsername());
            preparedStatement.setString(3,credencialesEntity.getPassword());
            preparedStatement.setString(4,credencialesEntity.getPermiso().toString());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println("Error al agregar una credencial." + e.getMessage());
        }

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public ArrayList<CredencialesEntity> findAll() {
        return null;
    }

    @Override
    public Optional<CredencialesEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Integer count() {
        return 0;
    }
}
