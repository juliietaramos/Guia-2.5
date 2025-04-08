package com.ecodeup.jdbc;

import com.ecodeup.jdbc.Entities.*;
import com.ecodeup.jdbc.Enum.*;
import com.ecodeup.jdbc.Exceptions.*;
import com.ecodeup.jdbc.Services.*;

import java.util.*;


public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static UsuariosService usuariosService;
    private static CredencialesService credencialesService;
    private static CuentasService cuentasService;
    private static Optional<UsuariosEntity> usuarioEnLinea;
//    private static UsuariosEntity ;

    public Menu() {
        this.usuariosService = UsuariosService.getInstanceOf();
        this.credencialesService = CredencialesService.getInstanceOf();
        this.cuentasService = CuentasService.getInstanceOf();
        usuarioEnLinea = null;
    }

    public void menu() {
        int opcion = -1;
        do {
            System.out.println("1. Registrarme.");
            System.out.println("2. Iniciar sesion.");
            System.out.println("0. Salir.");
            System.out.println("Elija una opcion:");
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    System.out.println("Registrarme:");
                    usuarioEnLinea = guardarUsuario();
                    sesion();
                    break;
                case 2:
                    System.out.println("Iniciar sesion:");
                    usuarioEnLinea = iniciarSesion();
                    sesion();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    usuarioEnLinea = null;
                    break;
            }
        } while (opcion != 0 && usuarioEnLinea != null);
    }

    private static void sesion() {
        System.out.println("Bienvenido/a " + usuarioEnLinea.get().getNombre() + " " + usuarioEnLinea.get().getApellido());
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("1. Listar usuarios del sistema."); //gestores y administradores
            System.out.println("2. Buscar usuario."); //gestores y administradores
            System.out.println("3. Modificar datos de un usuario."); //clientes (sus propios datos), gestores (cualquier cliente), administradores (cualquier usuario)
            System.out.println("4. Eliminar un usuario."); //gestores (solo clientes), administradores (cualquier usuario)
            System.out.println("5. Listar las cuentas de un usuario."); //clientes (solo sus propias cuentas), gestores y administradres (cuentas de cualquier usuario)
            System.out.println("6. Obtener el saldo total de un usuario"); //clientes (solo su saldo), gestor y administrador (cualquier usuario)
            System.out.println("7. Realizar un deposito en una cuenta."); //clientes (cuentas propias), gestores (en cuentas de clientes), administradores (cualquier cuenta)
            System.out.println("8. Realizar una transferencia."); //clientes (entre sus propias cuentas o a otros), getores y administrativos (entre != usuarios)
            System.out.println("9. Obtener la cantidad de usuario por tipo de permiso."); //gestor y administrados.
            System.out.println("10. Obtener la cantidad total de cuentas por tipo y mostrarlas."); //gestor y administrador
            System.out.println("11. Obtener el usuario con mayor saldo total."); //solo administrador
            System.out.println("12. Listar los usuarios ordenados por su saldo total."); //de mayor a menor. // administrador
            System.out.println("0. Cerrar sesion.");
            System.out.println("Ingrese una opcion:");
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    System.out.println("Listar usuarios del sistema."); //gestores y administradores
                    try {
                        listarUsuariosDelSistema();
                    } catch (NoAutorizadoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Buscar usuario."); //gestores y administradores
                    try {
                        buscarUsuario();
                    } catch (NoAutorizadoException e) {
                        System.out.println(e.getMessage());
                    } catch (NoSuchElementException e) {
                        System.out.println("Usuario no encontrado. " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("3. Modificar datos de un usuario."); //clientes (sus propios datos), gestores (cualquier cliente), administradores (cualquier usuario)
                    break;
                case 4:
                    System.out.println("Eliminar un usuario."); //gestores (solo clientes), administradores (cualquier usuario)
                    try {
                        eliminarUsuario();
                    } catch (NoAutorizadoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Listar las cuentas de un usuario."); //clientes (solo sus propias cuentas), gestores y administradres (cuentas de cualquier usuario)
                    try {
                        listarCuentas();
                    } catch (NoSuchElementException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Obtener el saldo total de un usuario"); //clientes (solo su saldo), gestor y administrador (cualquier usuario)
                    obtenerSaldoTotal();
                    break;
                case 7:
                    System.out.println("Realizar un deposito en una cuenta."); //clientes (cuentas propias), gestores (en cuentas de clientes), administradores (cualquier cuenta)
                    realizarDeposito();
                    break;
                case 8:
                    System.out.println("8. Realizar una transferencia."); //clientes (entre sus propias cuentas o a otros), getores y administrativos (entre != usuarios)
                    break;
                case 9:
                    System.out.println("9. Obtener la cantidad de usuario por tipo de permiso."); //gestor y administrados.
                    break;
                case 10:
                    System.out.println("10. Obtener la cantidad total de cuentas por tipo y mostrarlas."); //gestor y administrador
                    break;
                case 11:
                    System.out.println("11. Obtener el usuario con mayor saldo total."); //solo administrador
                    break;
                case 12:
                    System.out.println("12. Listar los usuarios ordenados por su saldo total."); //de mayor a menor. // administrador
                    break;
                case 0:
                    System.out.println("0. Cerrar sesion.");
                    usuarioEnLinea = null;
                    break;
            }
        }
    }

    private static void listarUsuariosDelSistema() throws NoAutorizadoException {
        if (verificarPermiso().equals(ENUM_permiso.CLIENTE)) {
            throw new NoAutorizadoException();
        } else {
            List<UsuariosEntity> listaDeUsuarios = new ArrayList<>();
            listaDeUsuarios = usuariosService.listarUsuarios();
            listaDeUsuarios.forEach(System.out::println);
        }
    } //FUNCIONA BIEN

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

    public static Optional<UsuariosEntity> iniciarSesion() {
        boolean flag = false;
        String user = new String();
        String password = new String();
        while (!flag) {
            System.out.println("Ingrese su usuario: ");
            user = scanner.nextLine();
            flag = credencialesService.verificarUsername(user);
            if (!flag) {
                System.out.println("Usuario no encontrado. Intente nuevamente.");
            }
        }
        flag = false;
        while (!flag) {
            System.out.println("Ingrese su contraseña: ");
            password = scanner.nextLine();
            flag = credencialesService.verificarPassword(password);
            if (!flag) {
                System.out.println("Contraseña incorrecta. Intente nuevamente.");
            }
        }
        int id = credencialesService.idCuenta(user, password);
        return Optional.of(usuariosService.mostrarUsuario(id));
    } //FUNCIONA BIEN

    public static Optional<UsuariosEntity> guardarUsuario() {
        try {
            UsuariosEntity usuario = crearUsuario();
            usuariosService.crearUsuario(usuario);
            usuario.setId(usuariosService.ultimoUsuario().getId());
            credencialesService.crearCredencial(usuario);
            cuentasService.agregarCuentaAhorro(usuario);
            return Optional.of(usuario);
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado.");
            return Optional.empty();
        }
    } //FUNCIONA BIEN

    public static void eliminarUsuario() throws NoAutorizadoException {
        if (verificarPermiso().equals(ENUM_permiso.CLIENTE)) {
            throw new NoAutorizadoException();
        } else {
            System.out.println("Ingrese el id del usuario a eliminar: ");
            int id = scanner.nextInt();
            if (verificarPermiso().equals(ENUM_permiso.GESTOR) && !credencialesService.mostrarCredencial(id)
                    .getPermiso().equals(ENUM_permiso.CLIENTE)) { //gestor solo puede eliminar clientes
                throw new NoAutorizadoException("Solo puedes eliminar clientes.");
            } else { // administrador puede eliminar cualquier usuario
                usuariosService.eliminarUsuario(id);
                credencialesService.elimiarCredencial(id);
                cuentasService.elimiarCuenta(id);
            }
        }
    } //FUNCIONA BIEN

    public static void mostrarInformacionUsuario() {
        System.out.println("Ingrese el id: ");
        int id = scanner.nextInt();
        try {
            System.out.println(usuariosService.mostrarUsuario(id).toString());
        } catch (NullPointerException e) {
            System.out.println("No se encontró un usuario con el id ingresado.");
        }
        try {
            System.out.println(credencialesService.mostrarCredencial(id));
        } catch (NullPointerException e) {
            System.out.println("No se encontró la credencial del id ingresado.");
        }
        try {
            cuentasService.mostrarCuenta(id).toString();
        } catch (NullPointerException e) {
            System.out.println("No se encontraron cuentas asociadas al id ingresado.");
        }
    } //FUNCIONA BIEN :)

    private static ENUM_permiso verificarPermiso() {
        return credencialesService
                .mostrarCredencial(usuarioEnLinea.get().getId())
                .getPermiso();
    } //FUNCIONA BIEN

    private static void buscarUsuario() throws NoAutorizadoException {
        if (verificarPermiso().equals(ENUM_permiso.CLIENTE)) {
            throw new NoAutorizadoException();
        } else {
            int opcion = -1;
            while (opcion != 0) {
                System.out.println("1. Buscar por dni.");
                System.out.println("2. Buscar por mail.");
                System.out.println("Ingrese una opcion.");
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        String dni = new String();
                        System.out.println("Ingrese el dni: ");
                        dni = scanner.nextLine();
                        System.out.println(usuariosService.filtrarUsuariosPorDni(dni).get().toString());
                        opcion = 0;
                        break;
                    case 2:
                        String mail = new String();
                        System.out.println("Ingrese el mail: ");
                        mail = scanner.nextLine();
                        System.out.println(usuariosService.filtrarUsuariosPorMail(mail).get().toString());
                        opcion = 0;
                        break;
                }
            }
        }
    } //FUNCIONA BIEN

    private static void listarCuentas() throws NoSuchElementException {
        if (verificarPermiso().equals(ENUM_permiso.CLIENTE)) {
            System.out.println(cuentasService.mostrarCuenta(usuarioEnLinea.get().getId()).toString());
        } else {
            //try {
            System.out.println("Ingrese el id del usuario: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            List<CuentasEntity> listaCuentas = cuentasService.listarCuentasPoId(id);
            if (listaCuentas.isEmpty()) {
                throw new NoSuchElementException("No hay cuentas con el id ingresado.");
            } else {
                listaCuentas.forEach(System.out::println);
            }
        }
    } //FUNCIONA BIEN

    private static void obtenerSaldoTotal() {
        /*CLIENTES pueden ver solo su saldo.
● GESTORES y ADMINISTRADORES pueden ver el saldo de cualquier usuario.
● Utilizar Stream y reduce para calcular el saldo total.*/
        if (verificarPermiso().equals(ENUM_permiso.CLIENTE)){
            double saldo = cuentasService.contarSaldoPorId(usuarioEnLinea.get().getId());
            System.out.println("El saldo total de su cuenta es: $" + saldo);
        }
        else {
            try {
                System.out.println("Ingrese el id del usuario: ");
                int id_usuario = scanner.nextInt();
                scanner.nextLine();
                double saldo = cuentasService.contarSaldoPorId(id_usuario);
                System.out.println("El saldo del usuario es de: $" + saldo);
            }catch (NoSuchElementException e){
                System.out.println("El usuario ingresado es incorrecto. " + e.getMessage());
            }
        }
    } //FUNCIONA BIEN

    private static void realizarDeposito (){
        /*CLIENTES pueden depositar en sus propias cuentas.
● GESTORES pueden depositar en cuentas de CLIENTES.
● ADMINISTRADORES pueden depositar en cualquier cuenta.
● Utilizar Optional para verificar la existencia de la cuenta.*/
        ENUM_permiso permiso = verificarPermiso();
        switch (permiso){
            case CLIENTE -> {
                listarCuentas();
                System.out.println("Ingrese el id de la cuenta a depositar: ");
                int id_cuenta = scanner.nextInt(); scanner.nextLine();
                System.out.println("Ingrese el monto a depositar: ");
                Double deposito = scanner.nextDouble(); scanner.nextLine();
                while (deposito<=0){
                    System.out.println("Ingrese un valor valido. ");
                    deposito = scanner.nextDouble(); scanner.nextLine();
                }
                deposito = deposito + cuentasService.recuperarSaldo(id_cuenta);
                cuentasService.modificarSaldo(id_cuenta,deposito);
                System.out.println("Deposito realizado con exito.");
                listarCuentas();
            }
        }
    }

}


