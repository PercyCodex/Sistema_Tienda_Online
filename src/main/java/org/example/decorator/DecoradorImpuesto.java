package org.example.decorator;

public class DecoradorImpuesto extends DecoradorProducto {
    
    private double porcentajeIgv;

    public DecoradorImpuesto(ComponenteProducto componente) {
        super(componente);
        this.porcentajeIgv = 18.0;
    }

    public DecoradorImpuesto(ComponenteProducto componente, double porcentajeIgv) {
        super(componente);
        this.porcentajeIgv = porcentajeIgv;
    }

    @Override
    public double getPrecioFinal() {
        double precioBase = super.getPrecioFinal();
        double igv = precioBase * (porcentajeIgv / 100);
        return precioBase + igv;
    }

    @Override
    public String getDetalles() {
        double precioBase = super.getPrecioFinal();
        double igv = precioBase * (porcentajeIgv / 100);
        
        return super.getDetalles() +
                "\n  └─ IGV aplicado: " + porcentajeIgv + "%" +
                " | Impuesto: S/" + String.format("%.2f", igv) +
                " | Precio Final con IGV: S/" + String.format("%.2f", getPrecioFinal());
    }
}