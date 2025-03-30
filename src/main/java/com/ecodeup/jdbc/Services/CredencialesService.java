package com.ecodeup.jdbc.Services;

import com.ecodeup.jdbc.Entities.CredencialesEntity;
import com.ecodeup.jdbc.Entities.CuentasEntity;
import com.ecodeup.jdbc.Entities.UsuariosEntity;
import com.ecodeup.jdbc.Enum.ENUM_permiso;
import com.ecodeup.jdbc.Repositories.CredencialesRepository;
import com.ecodeup.jdbc.Repositories.CuentasRepository;
import com.ecodeup.jdbc.Repositories.UsuariosRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CredencialesService {
    private static CredencialesService instance;
    private UsuariosRepository usuariosRepository;
    private CredencialesRepository credencialesRepository;
    private CuentasRepository cuentasRepository;
    private List<UsuariosEntity> listaDeUsuarios;
    private List<CredencialesEntity> listaDeCredenciales;
    private List<CuentasEntity> listaDeCuentas;

    private CredencialesService (){
        this.usuariosRepository=UsuariosRepository.getInstanceOf();
        this.credencialesRepository=CredencialesRepository.getInstanceOf();
        this.cuentasRepository=CuentasRepository.getInstanceOf();
        this.listaDeUsuarios=usuariosRepository.findAll();
        this.listaDeCredenciales=credencialesRepository.findAll();
        this.listaDeCuentas=cuentasRepository.findAll();
    }

    public static CredencialesService getInstanceOf(){
        if (instance == null){
            instance=new CredencialesService();
        }
        return instance;
    }

    public boolean verificarUsername(String username) { //retorna true si lo encuentra
        return listaDeCredenciales
                .stream()
                .anyMatch(c -> c.getUsername().equals(username));
    }

    public boolean verificarId_Usuario(int id_usuario) { //returna true si lo encuentra
        return listaDeCredenciales
                .stream()
                .anyMatch(c -> c.getId_usuario()==id_usuario);
    }

    private CredencialesEntity crearCredencial(int id_usuario, String username, String password, ENUM_permiso permiso) {
        CredencialesEntity credencial = new CredencialesEntity(id_usuario, username, password, permiso);
        Optional<CredencialesEntity> credencialesExistentes = credencialesRepository
                .findById(id_usuario);
        if (credencialesExistentes.isPresent()){
            return null;
        }
        else return credencial;
    }

    public void agregarCredencial(CredencialesEntity credencial){
        try{
            credencialesRepository.save(credencial);
        }catch (SQLException e){
            System.out.println("Error al guardar la credencial " + e.getMessage());
        }
    }

}
