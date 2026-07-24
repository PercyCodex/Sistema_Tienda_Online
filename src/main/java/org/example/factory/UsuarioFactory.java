package org.example.factory;

import org.example.model.Usuario;

public class UsuarioFactory {
    public static Usuario crearUsuario(String rol, String username, String password) {
        
        switch (rol.toUpperCase()) {
            case "ADMIN":
                return new Usuario(username, password, "ADMIN");
            
            case "CLIENTE":
                return new Usuario(username, password, "CLIENTE");
            
            default:
                throw new IllegalArgumentException("Error: El rol '" + rol + 
                        "' no existe. Use 'ADMIN' o 'CLIENTE'.");
        }
    }
}
