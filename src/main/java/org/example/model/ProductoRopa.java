package org.example.model;

public class ProductoRopa extends Productos {
    private String tipoTela;

    public ProductoRopa(int idProducto, String nombre, double precio, String tipoTela) {
        super(idProducto, nombre, precio);
        this.tipoTela = tipoTela;
    }

    @Override
    public String obtenerDetalleComercial() {
        return "Prenda de Vestir confeccionada con: " + tipoTela;
    }
}
