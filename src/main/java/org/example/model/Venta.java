package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Venta {
    private int idVenta;
    private Cliente cliente;
    private Productos producto;
    private int cantidad;
    private double precioUnitario;
    private double precioTotal;
    private LocalDateTime fechaVenta;
    private int idEnvioAsociado;

    public Venta(int idVenta, Cliente cliente, Productos producto, 
                 int cantidad, double precioUnitario, double precioTotal, int idEnvioAsociado) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.precioTotal = precioTotal;
        this.fechaVenta = LocalDateTime.now();
        this.idEnvioAsociado = idEnvioAsociado;
    }

    // Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public int getIdEnvioAsociado() {
        return idEnvioAsociado;
    }

    public void setIdEnvioAsociado(int idEnvioAsociado) {
        this.idEnvioAsociado = idEnvioAsociado;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Venta{" +
                "ID=" + idVenta +
                ", Cliente=" + cliente.getNombre() +
                ", Producto=" + producto.getNombre() +
                ", Cantidad=" + cantidad +
                ", Precio Unitario=S/" + String.format("%.2f", precioUnitario) +
                ", Total=S/" + String.format("%.2f", precioTotal) +
                ", Fecha=" + fechaVenta.format(formatter) +
                ", Envío ID=" + idEnvioAsociado +
                '}';
    }
}
