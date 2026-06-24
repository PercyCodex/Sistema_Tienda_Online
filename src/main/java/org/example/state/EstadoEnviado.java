package org.example.state;

public class EstadoEnviado implements EstadoEnvio {

    @Override
    public void manejarEstado() {
        System.out.println("[ENVÍO] Estado: ENVIADO " +
                "- El pedido va en camino a provincia.");
    }

    @Override
    public String obtenerNombre() {
        return "ENVIADO";
    }
}