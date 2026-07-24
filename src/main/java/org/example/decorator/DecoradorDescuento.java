package org.example.decorator;


public class DecoradorDescuento extends DecoradorProducto {
    
    private double porcentajeDescuento;

    public DecoradorDescuento(ComponenteProducto componente, double porcentajeDescuento) {
        super(componente);
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double getPrecioFinal() {
        double precioBase = super.getPrecioFinal();
        double descuento = precioBase * (porcentajeDescuento / 100);
        return precioBase - descuento;
    }

    @Override
    public String getDetalles() {
        return super.getDetalles() +
                "\n  └─ Descuento aplicado: " + porcentajeDescuento + "%" +
                " | Ahorro: S/" + String.format("%.2f",
                   super.getPrecioFinal() * (porcentajeDescuento / 100)) +
                " | Precio Final: S/" + String.format("%.2f", getPrecioFinal());
    }
}