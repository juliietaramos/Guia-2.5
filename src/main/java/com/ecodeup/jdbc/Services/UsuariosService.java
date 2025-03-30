package com.ecodeup.jdbc.Services;

import com.ecodeup.jdbc.Repositories.CredencialesRepository;
import com.ecodeup.jdbc.Repositories.CuentasRepository;
import com.ecodeup.jdbc.Repositories.UsuariosRepository;

public class UsuariosService {
    private static UsuariosService instance;
    private UsuariosRepository usuariosRepository;
    private CredencialesRepository credencialesRepository;
    private CuentasRepository cuentasRepository;

    public UsuariosService(){
        usuariosRepository=UsuariosRepository.getInstanceOf();
        credencialesRepository=CredencialesRepository.getInstanceOf();
        cuentasRepository=CuentasRepository.getInstanceOf();
    }

    public static UsuariosService getInstanceOf(){
        if (instance == null){
            instance= new UsuariosService();
        }
        return instance;
    }

    //public static void agregarUsuario


}
