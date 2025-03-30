package com.ecodeup.jdbc.Repositories;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface Repository <T>{
    public void save(T t) throws SQLException;
    public void deleteById (Integer id) throws SQLException;
    public ArrayList<T> findAll();
    public Optional<T> findById (Integer id);
}
