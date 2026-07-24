package org.example.service;

import java.util.ArrayList;
import java.util.List;

import org.example.model.Venta;

public class RepositorioVentas {
    private static RepositorioVentas instancia;
    private List<Venta> listaVentas;
    private int contadorVentas;

    private RepositorioVentas() {
        listaVentas = new ArrayList<>();
        contadorVentas = 1;
    }

    public static RepositorioVentas getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioVentas();
        }
        return instancia;
    }

    public void agregarVenta(Venta venta) {
        venta.setIdVenta(contadorVentas++);
        listaVentas.add(venta);
    }

    public Venta obtenerVentaPorId(int id) {
        for (Venta v : listaVentas) {
            if (v.getIdVenta() == id) {
                return v;
            }
        }
        return null;
    }

    public List<Venta> obtenerVentasPorCliente(String dni) {
        List<Venta> ventas = new ArrayList<>();
        for (Venta v : listaVentas) {
            if (v.getCliente().getDni().equals(dni)) {
                ventas.add(v);
            }
        }
        return ventas;
    }

    public List<Venta> getListaVentas() {
        return new ArrayList<>(listaVentas);
    }

    public void mostrarVentas() {
        System.out.println("=== VENTAS REGISTRADAS ===");
        for (Venta v : listaVentas) {
            System.out.println(v.toString());
        }
    }

    public double obtenerTotalVentas() {
        double total = 0;
        for (Venta v : listaVentas) {
            total += v.getPrecioTotal();
        }
        return total;
    }
}
