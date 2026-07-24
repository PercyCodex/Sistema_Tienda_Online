package org.example.decorator;


public abstract class DecoradorProducto implements ComponenteProducto {
    
    protected ComponenteProducto componente;

    public DecoradorProducto(ComponenteProducto componente) {
        this.componente = componente;
    }

    @Override
    public double getPrecioFinal() {
        return componente.getPrecioFinal();
    }

    @Override
    public String getDetalles() {
        return componente.getDetalles();
    }
}
