package com.ecodeup.jdbc.Services;

import com.ecodeup.jdbc.Entities.CredencialesEntity;
import com.ecodeup.jdbc.Entities.CuentasEntity;
import com.ecodeup.jdbc.Entities.UsuariosEntity;
import com.ecodeup.jdbc.Enum.ENUM_tipo;
import com.ecodeup.jdbc.Repositories.CredencialesRepository;
import com.ecodeup.jdbc.Repositories.CuentasRepository;
import com.ecodeup.jdbc.Repositories.UsuariosRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void actualizarLista (){
        this.listaDeCuentas = cuentasRepository.findAll();
    }

    public List<CuentasEntity> listarCuentasPoId (int id){
        return cuentasRepository
                .findAll()
                .stream()
                .filter(c -> c.getId_usuario() == id)
                .toList();
    }

    public Double contarSaldoPorId (int id){
        return listaDeCuentas
                .stream()
                .filter(c -> c.getId_usuario() == id)
                .mapToDouble(c -> c.getSaldo())
                .reduce(0, (c1, c2) -> c1+c2);
    }

    public int modificarSaldo (int id_cuenta, Double saldo){
        int filasModificadas = cuentasRepository.modificarSaldo(id_cuenta,saldo);
        actualizarLista();
        return filasModificadas;
    }

    public Double recuperarSaldo(int id_cuenta){
        return listaDeCuentas
                .stream()
                .filter(c -> c.getId()== id_cuenta)
                .mapToDouble(c -> c.getSaldo())
                .findFirst()
                .orElse(0);
    }

    public void agregarCuentaAhorro(UsuariosEntity usuario){
        try {
            cuentasRepository.save(new CuentasEntity(usuario.getId(), ENUM_tipo.CAJA_AHORRO,0.0));
            actualizarLista();
            System.out.println("Caja de ahorro creada con exito.");
        }catch (SQLException e){
            System.out.println("Error al agregar la cuenta. " + e.getMessage());
        }
    }

    public void agregarCuentaCorriente (UsuariosEntity usuario){
        try {
            cuentasRepository.save(new CuentasEntity(usuario.getId(), ENUM_tipo.CUENTA_CORRIENTE,0.0));
            actualizarLista();
            System.out.println("Cuenta corriente creada con exito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar la cuenta. " + e.getMessage());
        }
    }

    public void elimiarCuenta (int id){
        try {
            cuentasRepository.deleteById(id); //elimina todas las cuentas del idusuario
            actualizarLista();
        } catch (SQLException e) {
            System.out.println("Error al eliminar la cuenta . " + e.getMessage());
        }
    }

    public List<CuentasEntity> mostrarCuenta (int id){
        List<CuentasEntity> listaCuentas = new ArrayList<>();
        try{
            listaCuentas = cuentasRepository.findAllById(id);
//            for(CuentasEntity cuentas : listaCuentas){
//                System.out.println(cuentas.toString());
//            }
            return listaCuentas;
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<CuentasEntity> mostrarListaCuentas (){
        return cuentasRepository.findAll();
    }

    public void mostarInfoCuentas (){
        List<CuentasEntity>listaDeCuentas = cuentasRepository.findAll();
        for(CuentasEntity cuenta : listaDeCuentas){
            System.out.println("Id de la cuenta: " + cuenta.getId() +
                    ", titular de la cuenta: " + usuariosRepository.findById(cuenta.getId_usuario()).get().getNombre() +
                    " " + usuariosRepository.findById(cuenta.getId_usuario()).get().getApellido());
        }
    }
}
