package org.example.observer;

public class AlertaStockBajo implements ObservadorStock {

    @Override
    public void actualizar(String nombreProducto, int stockRestante) {
        if (stockRestante <= 2) {
            System.out.println("⚠ ALERTA: Stock bajo de '"
                    + nombreProducto + "'. Solo quedan: "
                    + stockRestante + " unidades.");
        }
    }
}