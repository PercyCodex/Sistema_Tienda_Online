package org.example.factory;

import org.example.model.ProductoCosmetico;
import org.example.model.ProductoRopa;
import org.example.model.Productos;

public class ProductoFactory {
    public static Productos crearProducto(String tipo, int id, String nombre,
                                          double precio, String atributoExtra) {

        switch (tipo.toUpperCase()) {
            case "ROPA":
                return new ProductoRopa(id, nombre, precio, atributoExtra);

            case "COSMETICO":
                return new ProductoCosmetico(id, nombre, precio, atributoExtra);
            default:
                throw new IllegalArgumentException("Error: El tipo '" + tipo +
                        "' no existe en Scarlet Line.");
        }
    }
}
