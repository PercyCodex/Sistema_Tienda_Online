package org.example.service;

import org.example.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class RepositorioClientes {
    private static RepositorioClientes instancia;
    private List<Cliente> listaClientes;

    private RepositorioClientes() {
        listaClientes = new ArrayList<>();
    }

    public static RepositorioClientes getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioClientes();
        }
        return instancia;
    }

    public void agregarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public Cliente buscarPorDni(String dni) {
        for (Cliente c : listaClientes) {
            if (c.getDni().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    public Cliente buscarPorNombre(String nombre) {
        for (Cliente c : listaClientes) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    public void mostrarClientes() {
        System.out.println("=== Clientes Registrados ===");
        for (Cliente c : listaClientes) {
            System.out.println("- DNI: " + c.getDni() + 
                             " | Nombre: " + c.getNombre() + 
                             " | Teléfono: " + c.getTelefono());
        }
    }

    public List<Cliente> getListaClientes() {
        return new ArrayList<>(listaClientes);
    }
}
