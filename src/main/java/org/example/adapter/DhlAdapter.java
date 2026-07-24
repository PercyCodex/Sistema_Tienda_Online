package org.example.adapter;

public class DhlAdapter implements EnvioAdaptado {
    
    private String trackingDhl;
    private String destination;
    private String deliveryStatus;
    private double costDhl;

    public DhlAdapter(String trackingDhl, String destination, String deliveryStatus, double costDhl) {
        this.trackingDhl = trackingDhl;
        this.destination = destination;
        this.deliveryStatus = deliveryStatus;
        this.costDhl = costDhl;
    }

    @Override
    public String getNumeroRastreo() {
        return trackingDhl;
    }

    @Override
    public String getDestino() {
        return destination;
    }

    @Override
    public String getEstado() {
        switch (deliveryStatus.toLowerCase()) {
            case "pending":
                return "PENDIENTE";
            case "on_way":
                return "ENVIADO";
            case "delivered":
                return "ENTREGADO";
            default:
                return "DESCONOCIDO";
        }
    }

    @Override
    public String getAgencia() {
        return "DHL Express";
    }

    @Override
    public double getCosto() {
        return costDhl;
    }

    @Override
    public String toString() {
        return "=== ENVÍO DHL ===\n" +
                "Tracking: " + getNumeroRastreo() + "\n" +
                "Destino: " + getDestino() + "\n" +
                "Estado: " + getEstado() + "\n" +
                "Agencia: " + getAgencia() + "\n" +
                "Costo: S/" + String.format("%.2f", getCosto());
    }
}