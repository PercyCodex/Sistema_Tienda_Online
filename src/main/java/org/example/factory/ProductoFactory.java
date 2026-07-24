package org.example.factory;

import org.example.model.ProductoCosmetico;
import org.example.model.ProductoRopa;
import org.example.model.Productos;

public class ProductoFactory {
    public static Productos crearProducto(String tipo, int id, String nombre,
                                          double precio, int stock, String atributoExtra) {

        switch (tipo.toUpperCase()) {
            case "ROPA":
                return new ProductoRopa(id, nombre, precio, stock, atributoExtra, "M");

            case "COSMETICO":
                return new ProductoCosmetico(id, nombre, precio, stock, atributoExtra);
            default:
                throw new IllegalArgumentException("Error: El tipo '" + tipo +
                        "' no existe en Scarlet Line.");
        }
    }

    public static Productos crearRopa(int id, String nombre, double precio, int stock, String color, String talla) {
        return new ProductoRopa(id, nombre, precio, stock, color, talla);
    }

    public static Productos crearCosmetico(int id, String nombre, double precio, int stock, String marca) {
        return new ProductoCosmetico(id, nombre, precio, stock, marca);
    }
}
