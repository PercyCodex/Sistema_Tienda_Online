package org.example.adapter;

public class OlvaAdapter implements EnvioAdaptado {
    
    private String codigoOlva;
    private String provinciaDestino;
    private String estadoEnvio;
    private double montoCosto;

    public OlvaAdapter(String codigoOlva, String provinciaDestino, String estadoEnvio, double montoCosto) {
        this.codigoOlva = codigoOlva;
        this.provinciaDestino = provinciaDestino;
        this.estadoEnvio = estadoEnvio;
        this.montoCosto = montoCosto;
    }

    @Override
    public String getNumeroRastreo() {
        return codigoOlva;
    }

    @Override
    public String getDestino() {
        return provinciaDestino;
    }

    @Override
    public String getEstado() {
        switch (estadoEnvio.toLowerCase()) {
            case "en tránsito":
                return "ENVIADO";
            case "entregado":
                return "ENTREGADO";
            case "pendiente":
                return "PENDIENTE";
            default:
                return "DESCONOCIDO";
        }
    }

    @Override
    public String getAgencia() {
        return "Olva Courier";
    }

    @Override
    public double getCosto() {
        return montoCosto;
    }

    @Override
    public String toString() {
        return "=== ENVÍO OLVA ===\n" +
                "Código Rastreo: " + getNumeroRastreo() + "\n" +
                "Destino: " + getDestino() + "\n" +
                "Estado: " + getEstado() + "\n" +
                "Agencia: " + getAgencia() + "\n" +
                "Costo: S/" + String.format("%.2f", getCosto());
    }
}