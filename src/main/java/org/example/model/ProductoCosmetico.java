package org.example.model;

public class ProductoCosmetico extends Productos {
    private String marcaProveedor;

    public ProductoCosmetico(int idProducto, String nombre, double precio, int stock, String marcaProveedor) {
        super(idProducto, nombre, precio, stock);
        this.marcaProveedor = marcaProveedor;
    }

    @Override
    public String obtenerDetalleComercial() {
        return "Cosmético Multimarca importado de: "
                + marcaProveedor;
    }
    
    public String getMarcaProveedor() {
        return marcaProveedor;
    }
}