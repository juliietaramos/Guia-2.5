package com.ecodeup.jdbc.Services;

import com.ecodeup.jdbc.Entities.UsuariosEntity;
import com.ecodeup.jdbc.Repositories.CredencialesRepository;
import com.ecodeup.jdbc.Repositories.CuentasRepository;
import com.ecodeup.jdbc.Repositories.UsuariosRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    public boolean verificarDni(String dni) {
        return listaDeUsuarios
                .stream()
                .anyMatch(u -> u.getDni()
                        .equals(dni));
    }

    public boolean verificarEmail(String email) {
        return listaDeUsuarios
                .stream()
                .anyMatch(u -> u.getEmail()
                        .equals(email));
    }

    public void crearUsuario(UsuariosEntity usuario) {
        agregarUsuario(usuario);
    }

    public UsuariosEntity ultimoUsuario() {
        return usuariosRepository.ultimoUsuarioRegistrado();
    }

    private void agregarUsuario(UsuariosEntity usuario) {
        try {
            usuariosRepository.save(usuario);
            System.out.println("Usuario registrado con exito.");
        } catch (SQLException e) {
            System.out.println("Error al guardar el usuario. " + e.getMessage());
        }
    }

    public void eliminarUsuario(int id) {
        try {
            usuariosRepository.deleteById(id);
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario. " + e.getMessage());
        }
    }

    public UsuariosEntity mostrarUsuario(int id) {
        try {
            Optional<UsuariosEntity> usuarioOpt = usuariosRepository.findById(id);
            if (usuarioOpt.isPresent()) {
                return usuarioOpt.get();
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
