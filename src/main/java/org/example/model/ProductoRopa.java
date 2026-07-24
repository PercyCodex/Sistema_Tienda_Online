package org.example.model;

public class ProductoRopa extends Productos {
    private String color;
    private String talla;

    public ProductoRopa(int idProducto, String nombre, double precio, int stock, String color, String talla) {
        super(idProducto, nombre, precio, stock);
        this.color = color;
        this.talla = talla;
    }

    @Override
    public String obtenerDetalleComercial() {
        return "Color: " + color + " | Talla: " + talla;
    }

    public String getColor() {
        return color;
    }

    public String getTalla() {
        return talla;
    }
}
