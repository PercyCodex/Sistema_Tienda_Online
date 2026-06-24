package org.example.state;

public class EstadoPendiente implements EstadoEnvio {

    @Override
    public void manejarEstado() {
        System.out.println("[ENVÍO] Estado: PENDIENTE " +
                "- El pedido está siendo preparado.");
    }

    @Override
    public String obtenerNombre() {
        return "PENDIENTE";
    }
}