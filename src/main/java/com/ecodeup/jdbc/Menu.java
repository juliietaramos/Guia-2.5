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

    public Menu() {
        this.usuariosService = UsuariosService.getInstanceOf();
        this.credencialesService = CredencialesService.getInstanceOf();
        this.cuentasService = CuentasService.getInstanceOf();
    }

    private static UsuariosEntity crearUsuario() {
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
    }

    public void guardarUsuario() {
        UsuariosEntity usuario = crearUsuario();
        usuariosService.crearUsuario(usuario);
        usuario.setId(usuariosService.ultimoUsuario().getId());
        credencialesService.crearCredencial(usuario);
        cuentasService.agregarCuentaAhorro(usuario);
    }

    public void eliminarUsuario() {
        System.out.println("Ingrese el id del usuario a eliminar: ");
        int id = scanner.nextInt();
        usuariosService.eliminarUsuario(id);
        credencialesService.elimiarCredencial(id);
        cuentasService.elimiarCuenta(id);
    }

    public void mostrarInformacionUsuario() {
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
    }
}


