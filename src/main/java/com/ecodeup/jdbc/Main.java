package com.ecodeup.jdbc;

import com.ecodeup.jdbc.Services.CredencialesService;
import com.ecodeup.jdbc.Services.CuentasService;
import com.ecodeup.jdbc.Services.UsuariosService;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static UsuariosService usuariosService = UsuariosService.getInstanceOf();
    public static CredencialesService credencialesService = CredencialesService.getInstanceOf();
    public static CuentasService cuentasService = CuentasService.getInstanceOf();

    public static void main(String[] args)  {
        Menu menu = new Menu();

        //menu.guardarUsuario();
        menu.eliminarUsuario();
        //menu.mostrarInformacionUsuario();
        //System.out.println(menu.getCuentasService().mostrarCuenta(1).toString());
        //cuentasService.mostrarCuenta(1).toString();
        //menu.guardarUsuario();
        //menu.mostrarInformacionUsuario();


    }


}