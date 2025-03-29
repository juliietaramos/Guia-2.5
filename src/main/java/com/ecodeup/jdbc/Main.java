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
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        UsuariosEntity user = new UsuariosEntity("Julieta", "Ramos", "44859420", "julieta@gmail.com");
        UsuariosRepository usuariosRepository = UsuariosRepository.getInstanceOf();
        //usuariosRepository.save(user);
        UsuariosEntity ultimoUser = usuariosRepository.ultimoUsuarioRegistrado();
        CredencialesEntity credenciales = new CredencialesEntity(ultimoUser.getId(), "julieetaramos", "12345", ENUM_permiso.CLIENTE);
        CredencialesRepository credencialesRepository = CredencialesRepository.getInstanceOf();
        //credencialesRepository.save(credenciales);
        CuentasRepository cuentasRepository = CuentasRepository.getInstanceOf();
        CuentasEntity cuentas = new CuentasEntity(ultimoUser.getId(), ENUM_tipo.CAJA_AHORRO, 0.0);
        //cuentasRepository.save(cuentas);
        //usuariosRepository.deleteById(usuariosRepository.ultimoUsuarioRegistrado().getId());
//        SQLiteConnection.testConnection();
//        usuariosRepository.verificar();
        //credencialesRepository.deleteById(6);
        //cuentasRepository.deleteById(6);
        List<UsuariosEntity> listaUsuarios = usuariosRepository.findAll();
        //listaUsuarios.forEach(System.out::println);
        List<CuentasEntity> listaCuentas = cuentasRepository.findAll();
        //listaCuentas.forEach(System.out::println);
        List<CredencialesEntity> listaCredenciales = credencialesRepository.findAll();
        //listaCredenciales.forEach(System.out::println);
        //System.out.println(usuariosRepository.findById(30).get()); //preguntar por que tira nosuchelementexception si la catcheo en el metodo!!!!!
        //System.out.println(cuentasRepository.findById(1).get());
        System.out.println(credencialesRepository.findById(3).get());
    }
}