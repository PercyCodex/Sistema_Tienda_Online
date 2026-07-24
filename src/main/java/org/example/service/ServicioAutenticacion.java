package org.example.service;

import java.util.ArrayList;
import java.util.List;

import org.example.model.Usuario;

public class ServicioAutenticacion {
    private static ServicioAutenticacion instancia;
    private List<Usuario> listaUsuarios;

    private ServicioAutenticacion() {
        listaUsuarios = new ArrayList<>();
    }

    public static ServicioAutenticacion getInstancia() {
        if (instancia == null) {
            instancia = new ServicioAutenticacion();
        }
        return instancia;
    }

    public void registrarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
        System.out.println("Usuario registrado: " + usuario.getUsername() + " (" + usuario.getRol() + ")");
    }

    public Usuario autenticar(String username, String password) {
        for (Usuario u : listaUsuarios) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public void mostrarUsuarios() {
        System.out.println("=== Usuarios Registrados ===");
        for (Usuario u : listaUsuarios) {
            System.out.println("- Username: " + u.getUsername() + 
                            " | Rol: " + u.getRol());
        }
    }

    public List<Usuario> getListaUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }
}
