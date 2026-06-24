package org.example.service;

import org.example.model.Productos;
import java.util.ArrayList;
import java.util.List;

public class RepositorioProductos {
    private static RepositorioProductos instancia;
    private List<Productos> listaProductos;


    private RepositorioProductos() {
        listaProductos = new ArrayList<>();
    }

    public static RepositorioProductos getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioProductos();
        }
        return instancia;
    }

    public void agregarProducto(Productos p) {
        listaProductos.add(p);
    }



    /*public List<Productos> getListaProductos() {
        return listaProductos;
    }*/

    public List<Productos> getListaProductos() {
        return new ArrayList<>(listaProductos);
    }



    public void mostrarProductos() {
        for (Productos p : listaProductos) {
            System.out.println("- " + p.getNombre()
                    + " | Precio: S/." + p.getPrecio()
                    + " | " + p.obtenerDetalleComercial());
        }
    }
}