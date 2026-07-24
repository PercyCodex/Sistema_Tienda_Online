package org.example.decorator;

import org.example.model.Productos;

/**
 * Convierte un Producto en ComponenteProducto
 * para que pueda ser decorado
 */
public class ProductoBase implements ComponenteProducto {
    
    private Productos producto;

    public ProductoBase(Productos producto) {
        this.producto = producto;
    }

    @Override
    public double getPrecioFinal() {
        return producto.getPrecio();
    }

    @Override
    public String getDetalles() {
        return "Producto: " + producto.getNombre() +
                " | Precio Base: S/" + String.format("%.2f", producto.getPrecio());
    }

    public Productos getProducto() {
        return producto;
    }
}