package org.example.decorator;

public class DecoradorEnvioExpress extends DecoradorProducto {
    
    private double costoEnvio;

    public DecoradorEnvioExpress(ComponenteProducto componente, double costoEnvio) {
        super(componente);
        this.costoEnvio = costoEnvio;
    }

    @Override
    public double getPrecioFinal() {
        return super.getPrecioFinal() + costoEnvio;
    }

    @Override
    public String getDetalles() {
        return super.getDetalles() +
                "\n  └─ Envío Express añadido: S/" + String.format("%.2f", costoEnvio) +
                " | Precio Final con Envío: S/" + String.format("%.2f", getPrecioFinal());
    }
}