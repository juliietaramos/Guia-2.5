package com.ecodeup.jdbc.Services;

import com.ecodeup.jdbc.Entities.UsuariosEntity;
import com.ecodeup.jdbc.Repositories.CredencialesRepository;
import com.ecodeup.jdbc.Repositories.CuentasRepository;
import com.ecodeup.jdbc.Repositories.UsuariosRepository;

import java.sql.SQLException;
import java.util.List;

import static com.ecodeup.jdbc.Main.scanner;

public class UsuariosService {
    private static UsuariosService instance;
    private UsuariosRepository usuariosRepository;
    private CredencialesRepository credencialesRepository;
    private CuentasRepository cuentasRepository;
    private List<UsuariosEntity> listaDeUsuarios;

    public UsuariosService() {
        usuariosRepository = UsuariosRepository.getInstanceOf();
        credencialesRepository = CredencialesRepository.getInstanceOf();
        cuentasRepository = CuentasRepository.getInstanceOf();
        this.listaDeUsuarios = usuariosRepository.findAll();
    }

    public static UsuariosService getInstanceOf() {
        if (instance == null) {
            instance = new UsuariosService();
        }
        return instance;
    }

    private boolean verificarId_Usuario(int id_usuario) { //returna true si lo encuentra
        return listaDeUsuarios
                .stream()
                .anyMatch(u -> u.getId() == id_usuario);
    }

    private boolean verificarDni(String dni) {
        return listaDeUsuarios
                .stream()
                .anyMatch(u -> u.getDni()
                        .equals(dni));
    }

    private boolean verificarEmail(String email) {
        return listaDeUsuarios
                .stream()
                .anyMatch(u -> u.getEmail()
                        .equals(email));
    }

    public void crearUsuario (){
        System.out.println("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese su apellido: ");
        String apellido = scanner.nextLine();
        int flag = 0;
        String dni= new String();
        while (flag == 0){
            System.out.println("Ingrese su dni: ");
            dni = scanner.nextLine();
            if (verificarDni(dni)){
                System.out.println("El dni ingresado ya figura en el sistema. Ingrese uno nuevo. ");
            }else flag = 1;
        }
        String email = new String();
        flag = 0;
        while (flag == 0){
            System.out.println("Ingrese un email: ");
            email=scanner.nextLine();
            if(verificarEmail(email)){
                System.out.println("El email ingresado ya figura en el sistema. Ingrese uno nuevo.");
            }else flag = 1;
        }
        agregarUsuario(new UsuariosEntity(nombre,apellido,dni,email));
        System.out.println("Usuario registrado con exito.");
    }

    public UsuariosEntity ultimoUsuario (){
        return usuariosRepository.ultimoUsuarioRegistrado();
    }

    private void agregarUsuario(UsuariosEntity usuario){
        try{
            usuariosRepository.save(usuario);
        }catch (SQLException e){
            System.out.println("Error al guardar el usuario. " + e.getMessage());
        }
    }


}
