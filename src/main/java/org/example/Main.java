package org.example;

import org.example.factory.ProductoFactory;
import org.example.model.Productos;
import org.example.service.RepositorioProductos;
import org.example.state.*;
import org.example.observer.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== FACTORY METHOD ===");
        Productos prod1 = ProductoFactory.crearProducto(
                "ROPA", 1, "Vestido Casual", 85.00, "Algodón Lino");

        Productos prod2 = ProductoFactory.crearProducto(
                "COSMETICO", 2, "Labial Larga Duración", 32.50, "Maybelline");

        System.out.println("Creado: " + prod1.getNombre() + " | " + prod1.obtenerDetalleComercial());
        System.out.println("Creado: " + prod2.getNombre() + " | " + prod2.obtenerDetalleComercial());


        System.out.println("\n=== SINGLETON - Repositorio ===");
        RepositorioProductos repo = RepositorioProductos.getInstancia();

        repo.agregarProducto(prod1);
        repo.agregarProducto(prod2);
        repo.mostrarProductos();


        System.out.println("\n=== STATE - Estado del Envío ===");
        Envio envio = new Envio("Trujillo");
        envio.mostrarEstado();
        envio.cambiarEstado(new EstadoEnviado());
        envio.mostrarEstado();
        envio.cambiarEstado(new EstadoEntregado());
        envio.mostrarEstado();


        System.out.println("\n=== OBSERVER - Control de Stock ===");
        ProductoConStock vestido = new ProductoConStock("Vestido Casual", 5);
        vestido.agregarObservador(new AlertaStockBajo());
        vestido.vender(2);
        vestido.vender(1);
        vestido.vender(1);
    }
}