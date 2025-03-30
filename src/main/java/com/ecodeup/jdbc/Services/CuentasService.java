package com.ecodeup.jdbc.Services;

import com.ecodeup.jdbc.Entities.CuentasEntity;
import com.ecodeup.jdbc.Entities.UsuariosEntity;
import com.ecodeup.jdbc.Enum.ENUM_tipo;
import com.ecodeup.jdbc.Repositories.CredencialesRepository;
import com.ecodeup.jdbc.Repositories.CuentasRepository;
import com.ecodeup.jdbc.Repositories.UsuariosRepository;

import java.sql.SQLException;
import java.util.List;

public class CuentasService {
    private static CuentasService instance;
    private UsuariosRepository usuariosRepository;
    private CredencialesRepository credencialesRepository;
    private CuentasRepository cuentasRepository;
    private List<CuentasEntity> listaDeCuentas;

    public CuentasService() {
        usuariosRepository = UsuariosRepository.getInstanceOf();
        credencialesRepository = CredencialesRepository.getInstanceOf();
        cuentasRepository = CuentasRepository.getInstanceOf();
        this.listaDeCuentas = cuentasRepository.findAll();
    }

    public static CuentasService getInstanceOf() {
        if (instance == null) {
            instance = new CuentasService();
        }
        return instance;
    }

    public void agregarCuentaAhorro(UsuariosEntity usuario){
        try {
            cuentasRepository.save(new CuentasEntity(usuario.getId(), ENUM_tipo.CAJA_AHORRO,0.0));
            System.out.println("Caja de ahorro creada con exito.");
        }catch (SQLException e){
            System.out.println("Error al agregar la cuenta. " + e.getMessage());
        }
    }
}
