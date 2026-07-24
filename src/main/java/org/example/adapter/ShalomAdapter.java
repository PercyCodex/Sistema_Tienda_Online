package org.example.adapter;
public class ShalomAdapter implements EnvioAdaptado {
    
    private String idShalom;
    private String location;
    private String statusShalom;
    private double precioShalom;

    public ShalomAdapter(String idShalom, String location, String statusShalom, double precioShalom) {
        this.idShalom = idShalom;
        this.location = location;
        this.statusShalom = statusShalom;
        this.precioShalom = precioShalom;
    }

    @Override
    public String getNumeroRastreo() {
        return idShalom;
    }

    @Override
    public String getDestino() {
        return location;
    }

    @Override
    public String getEstado() {
        switch (statusShalom.toLowerCase()) {
            case "processing":
                return "PENDIENTE";
            case "shipped":
                return "ENVIADO";
            case "delivered":
                return "ENTREGADO";
            default:
                return "DESCONOCIDO";
        }
    }

    @Override
    public String getAgencia() {
        return "Shalom";
    }

    @Override
    public double getCosto() {
        return precioShalom;
    }

    @Override
    public String toString() {
        return "=== ENVÍO SHALOM ===\n" +
                "ID Rastreo: " + getNumeroRastreo() + "\n" +
                "Destino: " + getDestino() + "\n" +
                "Estado: " + getEstado() + "\n" +
                "Agencia: " + getAgencia() + "\n" +
                "Costo: S/" + String.format("%.2f", getCosto());
    }
}