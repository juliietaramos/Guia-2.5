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
import java.util.Scanner;

public class CredencialesService {
    private Scanner scanner = new Scanner(System.in);
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

    public void crearCredencial(UsuariosEntity usuario) {
        int flag = 0;
        String username = new String();
        while (flag==0){
            System.out.println("Ingrese un nombre de usuario: ");
            username = scanner.nextLine();
            if(verificarUsername(username)){
                System.out.println("El usuario ya existe. Intente nuevamente."); //USAR EXCEPCIONES!!!!!
            }
            else flag=1;
        }
        System.out.println("Ingrese una contraseña: ");
        String contraseña = scanner.nextLine();
        ENUM_permiso permiso = ENUM_permiso.CLIENTE;
        int id_usuario = usuariosRepository.ultimoUsuarioRegistrado().getId();
        agregarCredencial(new CredencialesEntity(id_usuario,username,contraseña,permiso));
        System.out.println("Credencial guardada con exito.");
    }

    private void agregarCredencial(CredencialesEntity credencial){
        try{
            credencialesRepository.save(credencial);
        }catch (SQLException e){
            System.out.println("Error al guardar la credencial " + e.getMessage());
        }
    }

    public void elimiarCredencial (int id){
        try{
            credencialesRepository.deleteById(id);
        }catch (SQLException e){
            System.out.println("Error al eliminar la credencial. " + e.getMessage());
        }
    }

    public CredencialesEntity mostrarCredencial (int id){
        try{
            Optional<CredencialesEntity> credencialOpt = credencialesRepository.findById(id);
            if(credencialOpt.isPresent()){
                return credencialOpt.get();
            }
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean verificarPassword (String password){ //returna true si lo encuentra
        return listaDeCredenciales
                .stream()
                .anyMatch(c -> c.getPassword().equals(password));
    }

    public int idCuenta (String username, String password){
        return listaDeCredenciales
                .stream()
                .filter(c -> c.getUsername().equals(username) && c.getPassword().equals(password))
                .map(c -> c.getId())
                .toList()
                .getFirst();
    }

}
