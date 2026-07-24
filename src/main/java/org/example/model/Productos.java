package org.example.model;

public abstract class Productos {
    private int idProductos;
    private String nombre;
    private double precio;
    private int stock;

    public Productos(int idProductos, String nombre, double precio, int stock) {
        this.idProductos = idProductos;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public abstract String obtenerDetalleComercial();

    public int getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(int idProductos) {
        this.idProductos = idProductos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}