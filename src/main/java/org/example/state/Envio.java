package org.example.state;

public class Envio {

    private EstadoEnvio estadoActual;
    private String destino;

    public Envio(String destino) {
        this.destino = destino;
        this.estadoActual = new EstadoPendiente(); // inicia en Pendiente
    }

    public void cambiarEstado(EstadoEnvio nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void mostrarEstado() {
        System.out.println("Destino: " + destino);
        estadoActual.manejarEstado();
    }

    public String getDestino() {
        return destino;
    }
}