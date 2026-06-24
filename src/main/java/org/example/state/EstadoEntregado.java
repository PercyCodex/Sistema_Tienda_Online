package org.example.state;

public class EstadoEntregado implements EstadoEnvio {

    @Override
    public void manejarEstado() {
        System.out.println("[ENVÍO] Estado: ENTREGADO " +
                "- El pedido llegó correctamente.");
    }

    @Override
    public String obtenerNombre() {
        return "ENTREGADO";
    }
}