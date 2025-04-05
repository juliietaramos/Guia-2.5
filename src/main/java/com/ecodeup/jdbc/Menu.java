package com.ecodeup.jdbc;

import com.ecodeup.jdbc.Entities.UsuariosEntity;
import com.ecodeup.jdbc.Services.CredencialesService;
import com.ecodeup.jdbc.Services.CuentasService;
import com.ecodeup.jdbc.Services.UsuariosService;

import java.util.Scanner;


public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static UsuariosService usuariosService;
    private static CredencialesService credencialesService;
    private static CuentasService cuentasService;
    private static UsuariosEntity usuarioEnLinea;

    public Menu() {
        this.usuariosService = UsuariosService.getInstanceOf();
        this.credencialesService = CredencialesService.getInstanceOf();
        this.cuentasService = CuentasService.getInstanceOf();
        usuarioEnLinea = null;
    }

    public void menu(){
        System.out.println("1. Registrarme.");
        System.out.println("2. Iniciar sesion.");
        int opcion = 0;
        switch (opcion){
            case 1:
                System.out.println("Registrarme:");
                usuarioEnLinea=guardarUsuario();
                break;
            case 2:
                System.out.println("Iniciar sesion:");
                usuarioEnLinea=iniciarSesion();
                break;
        }
    }

    private static UsuariosEntity crearUsuario() { //FUNCIONA BIEN :)
        System.out.println("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese su apellido: ");
        String apellido = scanner.nextLine();
        int flag = 0;
        String dni = new String();
        while (flag == 0) {
            System.out.println("Ingrese su dni: ");
            dni = scanner.nextLine();
            System.out.println("dni: " + dni);
            if (usuariosService.verificarDni(dni)) {
                System.out.println("El dni ingresado ya figura en el sistema. Ingrese uno nuevo. ");
            } else flag = 1;
        }
        String email = new String();
        flag = 0;
        while (flag == 0) {
            System.out.println("Ingrese un email: ");
            email = scanner.nextLine();
            if (usuariosService.verificarEmail(email)) {
                System.out.println("El email ingresado ya figura en el sistema. Ingrese uno nuevo.");
            } else flag = 1;
        }
        return new UsuariosEntity(nombre, apellido, dni, email);
    } //FUNCIONA BIEN :)

    public UsuariosEntity iniciarSesion(){
        boolean flag = false;
        String user = new String();
        String password = new String();
        while (!flag){
            System.out.println("Ingrese su usuario: ");
            user = scanner.nextLine();
            flag=credencialesService.verificarUsername(user);
            if (!flag){
                System.out.println("Usuario no encontrado. Intente nuevamente.");
            }
        }
        flag=false;
        while (!flag){
            System.out.println("Ingrese su contraseña: ");
            password = scanner.nextLine();
            flag= credencialesService.verificarPassword(password);
            if (!flag){
                System.out.println("Contraseña incorrecta. Intente nuevamente.");
            }
        }
        int id = credencialesService.idCuenta(user,password);
        System.out.println("Usuario:");
        System.out.println(usuariosService.mostrarUsuario(id).toString());

        return usuariosService.mostrarUsuario(id);
    }

    public UsuariosEntity guardarUsuario() {
        UsuariosEntity usuario = crearUsuario();
        usuariosService.crearUsuario(usuario);
        usuario.setId(usuariosService.ultimoUsuario().getId());
        credencialesService.crearCredencial(usuario);
        cuentasService.agregarCuentaAhorro(usuario);
        return usuario;
    } //FUNCIONA BIEN :)

    public void eliminarUsuario() {
        System.out.println("Ingrese el id del usuario a eliminar: ");
        int id = scanner.nextInt();
        usuariosService.eliminarUsuario(id);
        credencialesService.elimiarCredencial(id);
        cuentasService.elimiarCuenta(id);
    } //FUNCIONA BIEN :)

    public static void mostrarInformacionUsuario() {
        System.out.println("Ingrese el id: ");
        int id = scanner.nextInt();
        try {
            System.out.println(usuariosService.mostrarUsuario(id).toString());
        } catch (NullPointerException e) {
            System.out.println("No se encontró un usuario con el id ingresado.");
        }
        try{
            System.out.println(credencialesService.mostrarCredencial(id));
        }catch (NullPointerException e){
            System.out.println("No se encontró la credencial del id ingresado.");
        }
        try {
            cuentasService.mostrarCuenta(id).toString();
        }catch (NullPointerException e){
            System.out.println("No se encontraron cuentas asociadas al id ingresado.");
        }
    } //FUNCIONA BIEN :)
}


