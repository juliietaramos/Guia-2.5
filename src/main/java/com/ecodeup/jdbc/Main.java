package com.ecodeup.jdbc;

import com.ecodeup.jdbc.Entities.CredencialesEntity;
import com.ecodeup.jdbc.Entities.CuentasEntity;
import com.ecodeup.jdbc.Entities.UsuariosEntity;
import com.ecodeup.jdbc.Enum.ENUM_permiso;
import com.ecodeup.jdbc.Enum.ENUM_tipo;
import com.ecodeup.jdbc.Repositories.CredencialesRepository;
import com.ecodeup.jdbc.Repositories.CuentasRepository;
import com.ecodeup.jdbc.Repositories.UsuariosRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UsuariosEntity user = new UsuariosEntity("Julieta" , "Ramos" , "44859420" , "julieta@gmail.com");
        UsuariosRepository instancia = UsuariosRepository.getInstanceOf();
       //instancia.save(user);
        UsuariosEntity ultimoUser = instancia.ultimoUsuarioRegistrado();
        CredencialesEntity credenciales = new CredencialesEntity(ultimoUser.getId(),"julieetaramos" , "12345" , ENUM_permiso.CLIENTE);
        CredencialesRepository credencialesRepository = CredencialesRepository.getInstanceOf();
        //credencialesRepository.save(credenciales);
        CuentasRepository cuentasRepository = CuentasRepository.getInstanceOf();
        CuentasEntity cuentas = new CuentasEntity(ultimoUser.getId(), ENUM_tipo.CAJA_AHORRO,0.0);
        cuentasRepository.save(cuentas);
//        SQLiteConnection.testConnection();
//        instancia.verificar();
    }
}