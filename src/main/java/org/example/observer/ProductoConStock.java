package org.example.observer;

import java.util.ArrayList;
import java.util.List;

public class ProductoConStock {

    private String nombre;
    private int stock;
    private List<ObservadorStock> observadores = new ArrayList<>();

    public ProductoConStock(String nombre, int stockInicial) {
        this.nombre = nombre;
        this.stock = stockInicial;
    }

    public void agregarObservador(ObservadorStock o) {
        observadores.add(o);
    }

    public void vender(int cantidad) {
        this.stock -= cantidad;
        System.out.println("Venta registrada: " + cantidad
                + " unidad(es) de '" + nombre
                + "'. Stock actual: " + stock);
        notificarObservadores();
    }

    private void notificarObservadores() {
        for (ObservadorStock o : observadores) {
            o.actualizar(nombre, stock);
        }
    }

    public int getStock() { return stock; }
    public String getNombre() { return nombre; }
}