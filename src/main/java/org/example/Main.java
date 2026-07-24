package org.example;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.example.adapter.EnvioAdaptado;
import org.example.decorator.ComponenteProducto;
import org.example.decorator.DecoradorDescuento;
import org.example.decorator.DecoradorImpuesto;
import org.example.decorator.ProductoBase;
import org.example.facade.FacadeSistemaVentas;
import org.example.model.Cliente;
import org.example.model.Productos;
import org.example.model.Usuario;
import org.example.model.Venta;

public class Main {
    
    private static FacadeSistemaVentas facade;
    private static Map<Integer, EnvioAdaptado> envios;
    private static int contadorEnvios = 1;

    public static void main(String[] args) {
        facade = new FacadeSistemaVentas();
        envios = new HashMap<>();
        
        boolean loginExitoso = false;
        Usuario usuarioActual = null;
        
        while (!loginExitoso) {
            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
            JTextField userField = new JTextField(15);
            JPasswordField passField = new JPasswordField(15);
            
            panel.add(new JLabel("Usuario:"));
            panel.add(userField);
            panel.add(new JLabel("Contraseña:"));
            panel.add(passField);
            panel.add(new JLabel(""));
            
            int result = JOptionPane.showConfirmDialog(null, panel, 
                "🔐 LOGIN - SCARLET LINE", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                System.exit(0);
            }
            
            String username = userField.getText();
            String password = new String(passField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "❌ Por favor ingrese usuario y contraseña.", 
                    "⚠️ ERROR", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            
            usuarioActual = facade.autenticar(username, password);
            
            if (usuarioActual != null) {
                loginExitoso = true;
                JOptionPane.showMessageDialog(null, 
                    "¡Bienvenido " + usuarioActual.getUsername() + "!\n\n" +
                    "Sistema de Ventas Online - SCARLET LINE",
                    "✅ LOGIN EXITOSO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "❌ Credenciales incorrectas, intente nuevamente.",
                    "⚠️ ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        while (true) {
            String[] opciones = {
                "📦 Productos",
                "👤 Clientes",
                "💰 Ventas",
                "📮 Envíos",
                "🎁 Descuentos",
                "💵 Reportes",
                "🚪 Salir"
            };
            
            int seleccion = JOptionPane.showOptionDialog(
                null,
                "=== MENÚ PRINCIPAL ===\n\nSeleccione una opción:",
                "🏪 SCARLET LINE - SISTEMA DE VENTAS",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
            );
            
            if (seleccion == -1 || seleccion == 6) {
                JOptionPane.showMessageDialog(null, 
                    "¡Hasta luego!\n\nGracias por usar SCARLET LINE", 
                    "👋 DESPEDIDA", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            
            switch (seleccion) {
                case 0: menuProductos(); break;
                case 1: menuClientes(); break;
                case 2: menuVentas(); break;
                case 3: menuEnvios(); break;
                case 4: aplicarDescuentos(); break;
                case 5: menuReportes(); break;
            }
        }
    }
    
    private static void crearProducto() {
        try {
            String[] tipos = {"👗 ROPA", "💄 COSMETICO"};
            String tipo = (String) JOptionPane.showInputDialog(
                null, "Seleccione el tipo de producto:", "📦 CREAR PRODUCTO",
                JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
            if (tipo == null) return;
            
            String idStr = JOptionPane.showInputDialog(null, "Ingrese el ID del producto:", "📦 CREAR PRODUCTO", JOptionPane.QUESTION_MESSAGE);
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);
            
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto:", "📦 CREAR PRODUCTO", JOptionPane.QUESTION_MESSAGE);
            if (nombre == null) return;
            
            String precioStr = JOptionPane.showInputDialog(null, "Ingrese el precio del producto (S/):", "📦 CREAR PRODUCTO", JOptionPane.QUESTION_MESSAGE);
            if (precioStr == null) return;
            double precio = Double.parseDouble(precioStr);
            
            String stockStr = JOptionPane.showInputDialog(null, "Ingrese el stock del producto:", "📦 CREAR PRODUCTO", JOptionPane.QUESTION_MESSAGE);
            if (stockStr == null) return;
            int stock = Integer.parseInt(stockStr);
            
            if (tipo.contains("ROPA")) {
                String color = JOptionPane.showInputDialog(null, "Ingrese el color:", "📦 CREAR PRODUCTO", JOptionPane.QUESTION_MESSAGE);
                if (color == null) return;
                String talla = JOptionPane.showInputDialog(null, "Ingrese la talla (XS, S, M, L, XL, XXL):", "📦 CREAR PRODUCTO", JOptionPane.QUESTION_MESSAGE);
                if (talla == null) return;
                facade.crearProductoRopa(id, nombre, precio, stock, color, talla);
            } else {
                String marca = JOptionPane.showInputDialog(null, "Ingrese la marca:", "📦 CREAR PRODUCTO", JOptionPane.QUESTION_MESSAGE);
                if (marca == null) return;
                facade.crearProductoCosmetico(id, nombre, precio, stock, marca);
            }
            
            JOptionPane.showMessageDialog(null, "✅ ¡Producto creado exitosamente!", "✅ ÉXITO", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ Por favor ingrese valores numéricos válidos.", "⚠️ ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static void verInventario() {
        var productos = facade.obtenerTodosProductos();
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ No hay productos en el inventario.", "📋 INVENTARIO", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder("===== 📋 INVENTARIO ACTUAL =====\n\n");
        int contador = 1;
        for (Productos p : productos) {
            sb.append(contador).append(".\n")
            .append("  ID: ").append(p.getIdProductos()).append("\n")
            .append("  Nombre: ").append(p.getNombre()).append("\n")
            .append("  Precio: S/").append(String.format("%.2f", p.getPrecio())).append("\n")
            .append("  Stock: ").append(p.getStock()).append(" unidades\n")
            .append("  Detalles: ").append(p.obtenerDetalleComercial()).append("\n\n");
            contador++;
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "📋 INVENTARIO", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void registrarCliente() {
        String dni = JOptionPane.showInputDialog(null, "Ingrese el DNI del cliente:", "👤 REGISTRAR CLIENTE", JOptionPane.QUESTION_MESSAGE);
        if (dni == null) return;
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del cliente:", "👤 REGISTRAR CLIENTE", JOptionPane.QUESTION_MESSAGE);
        if (nombre == null) return;
        String telefono = JOptionPane.showInputDialog(null, "Ingrese el teléfono del cliente:", "👤 REGISTRAR CLIENTE", JOptionPane.QUESTION_MESSAGE);
        if (telefono == null) return;
        
        facade.registrarCliente(dni, nombre, telefono);
        JOptionPane.showMessageDialog(null, "✅ ¡Cliente registrado exitosamente!", "✅ ÉXITO", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void verClientes() {
        var clientes = facade.obtenerTodosClientes();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ No hay clientes registrados.", "👥 CLIENTES", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder("===== 👥 CLIENTES REGISTRADOS =====\n\n");
        int contador = 1;
        for (Cliente c : clientes) {
            sb.append(contador).append(".\n")
              .append("  DNI: ").append(c.getDni()).append("\n")
              .append("  Nombre: ").append(c.getNombre()).append("\n")
              .append("  Teléfono: ").append(c.getTelefono()).append("\n\n");
            contador++;
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "👥 CLIENTES", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void venderProductoCompleto() {
        try {
            String dni = JOptionPane.showInputDialog(null, "Ingrese el DNI del cliente:", "💰 VENDER PRODUCTO", JOptionPane.QUESTION_MESSAGE);
            if (dni == null) return;
            
            Cliente cliente = facade.obtenerClientePorDni(dni);
            
            if (cliente == null) {
                int opcion = JOptionPane.showConfirmDialog(null, "❌ Cliente no encontrado.\n\n¿Desea registrarlo ahora?", "👤 CLIENTE NO ENCONTRADO", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    registrarCliente();
                    cliente = facade.obtenerClientePorDni(dni);
                    if (cliente == null) return;
                } else {
                    return;
                }
            }
            
            JOptionPane.showMessageDialog(null, "✅ Cliente encontrado:\n\nDNI: " + cliente.getDni() + "\nNombre: " + cliente.getNombre() + "\nTeléfono: " + cliente.getTelefono(), "👤 DATOS DEL CLIENTE", JOptionPane.INFORMATION_MESSAGE);
            
            var productos = facade.obtenerTodosProductos();
            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "❌ No hay productos en el inventario.", "⚠️ ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String[] nombresProductos = new String[productos.size()];
            for (int i = 0; i < productos.size(); i++) {
                nombresProductos[i] = productos.get(i).getNombre() + " - S/" + String.format("%.2f", productos.get(i).getPrecio()) + " (Stock: " + productos.get(i).getStock() + ")";
            }
            
            String seleccionProducto = (String) JOptionPane.showInputDialog(null, "Seleccione el producto a vender:", "💰 VENDER PRODUCTO", JOptionPane.QUESTION_MESSAGE, null, nombresProductos, nombresProductos[0]);
            if (seleccionProducto == null) return;
            
            int indexProducto = -1;
            for (int i = 0; i < nombresProductos.length; i++) {
                if (nombresProductos[i].equals(seleccionProducto)) {
                    indexProducto = i;
                    break;
                }
            }
            Productos producto = productos.get(indexProducto);
            
            String cantidadStr = JOptionPane.showInputDialog(null, "Ingrese la cantidad a vender:", "💰 VENDER PRODUCTO", JOptionPane.QUESTION_MESSAGE);
            if (cantidadStr == null) return;
            int cantidad = Integer.parseInt(cantidadStr);
            
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(null, "❌ La cantidad debe ser mayor a 0.", "⚠️ ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!facade.verificarStock(producto, cantidad)) {
                JOptionPane.showMessageDialog(null, "❌ Stock insuficiente.\nStock disponible: " + producto.getStock(), "⚠️ ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int aplicarDesc = JOptionPane.showConfirmDialog(null, "¿Desea aplicar descuento a esta venta?", "🎁 DESCUENTO", JOptionPane.YES_NO_OPTION);
            
            double precioUnitario = producto.getPrecio();
            double precioTotal;
            
            if (aplicarDesc == JOptionPane.YES_OPTION) {
                String descuentoStr = JOptionPane.showInputDialog(null, "Ingrese el porcentaje de descuento (0-100):", "🎁 DESCUENTO", JOptionPane.QUESTION_MESSAGE);
                if (descuentoStr == null) return;
                double descuento = Double.parseDouble(descuentoStr);
                
                ComponenteProducto base = new ProductoBase(producto);
                ComponenteProducto conDescuento = new DecoradorDescuento(base, descuento);
                precioUnitario = conDescuento.getPrecioFinal();
                precioTotal = precioUnitario * cantidad;
            } else {
                precioTotal = facade.calcularVenta(producto, cantidad);
            }
            
            int envioId = crearEnvioAutomatico(cliente, producto, cantidad);
            
            facade.actualizarStock(producto, cantidad);
            facade.registrarVenta(cliente, producto, cantidad, precioUnitario, precioTotal, envioId);
            
            JOptionPane.showMessageDialog(null, "✅ ¡VENTA REALIZADA CON ÉXITO!\n\n═══════════════════════════════\nCliente: " + cliente.getNombre() + "\nDNI: " + cliente.getDni() + "\nProducto: " + producto.getNombre() + "\nCantidad: " + cantidad + " unidades\nPrecio Unitario: S/" + String.format("%.2f", precioUnitario) + "\n─────────────────────────────\nTOTAL: S/" + String.format("%.2f", precioTotal) + "\n═══════════════════════════════\nEnvío ID Asociado: " + envioId, "✅ VENTA EXITOSA", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ Por favor ingrese valores válidos.", "⚠️ ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static int crearEnvioAutomatico(Cliente cliente, Productos producto, int cantidad) {
        String[] agencias = {"📦 Olva Courier", "🚚 Shalom", "✈️ DHL Express"};
        String agencia = (String) JOptionPane.showInputDialog(null, "Seleccione la agencia de transporte:", "📮 CREAR ENVÍO", JOptionPane.QUESTION_MESSAGE, null, agencias, agencias[0]);
        if (agencia == null) return -1;
        
        String provincia = JOptionPane.showInputDialog(null, "Ingrese la provincia de destino:", "📮 CREAR ENVÍO", JOptionPane.QUESTION_MESSAGE);
        if (provincia == null) return -1;
        
        String costoStr = JOptionPane.showInputDialog(null, "Ingrese el costo del envío (S/):", "📮 CREAR ENVÍO", JOptionPane.QUESTION_MESSAGE);
        if (costoStr == null) return -1;
        double costo = Double.parseDouble(costoStr);
        
        EnvioAdaptado envio = null;
        String numeroRastreo = generarNumeroRastreo();
        
        if (agencia.contains("Olva")) {
            envio = facade.crearEnvioOlva(numeroRastreo, provincia, "Pendiente", costo);
        } else if (agencia.contains("Shalom")) {
            envio = facade.crearEnvioShalom(numeroRastreo, provincia, "processing", costo);
        } else if (agencia.contains("DHL")) {
            envio = facade.crearEnvioDhl(numeroRastreo, provincia, "pending", costo);
        }
        
        envios.put(contadorEnvios, envio);
        return contadorEnvios++;
    }
    
    private static void crearEnvio() {
        try {
            String[] agencias = {"📦 Olva Courier", "🚚 Shalom", "✈️ DHL Express"};
            String agenciaSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione la agencia de transporte:", "📮 CREAR ENVÍO", JOptionPane.QUESTION_MESSAGE, null, agencias, agencias[0]);
            if (agenciaSeleccionada == null) return;
            
            String numeroRastreo = JOptionPane.showInputDialog(null, "Ingrese el número de rastreo:", "📮 CREAR ENVÍO", JOptionPane.QUESTION_MESSAGE);
            if (numeroRastreo == null) return;
            
            String destino = JOptionPane.showInputDialog(null, "Ingrese el destino:", "📮 CREAR ENVÍO", JOptionPane.QUESTION_MESSAGE);
            if (destino == null) return;
            
            String estado = JOptionPane.showInputDialog(null, "Ingrese el estado (Pendiente/Enviado/Entregado):", "📮 CREAR ENVÍO", JOptionPane.QUESTION_MESSAGE);
            if (estado == null) return;
            
            String costoStr = JOptionPane.showInputDialog(null, "Ingrese el costo del envío (S/):", "📮 CREAR ENVÍO", JOptionPane.QUESTION_MESSAGE);
            if (costoStr == null) return;
            double costo = Double.parseDouble(costoStr);
            
            EnvioAdaptado envio = null;
            
            if (agenciaSeleccionada.contains("Olva")) {
                envio = facade.crearEnvioOlva(numeroRastreo, destino, estado, costo);
            } else if (agenciaSeleccionada.contains("Shalom")) {
                envio = facade.crearEnvioShalom(numeroRastreo, destino, estado, costo);
            } else if (agenciaSeleccionada.contains("DHL")) {
                envio = facade.crearEnvioDhl(numeroRastreo, destino, estado, costo);
            }
            
            envios.put(contadorEnvios++, envio);
            JOptionPane.showMessageDialog(null, "✅ ¡Envío creado exitosamente!\n\n" + envio.toString(), "✅ ENVÍO CREADO", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ Por favor ingrese valores válidos.", "⚠️ ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static void verEstadosEnvios() {
        if (envios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ No hay envíos registrados.", "📍 ENVÍOS", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder("===== 📍 ESTADOS DE ENVÍOS =====\n\n");
        for (Map.Entry<Integer, EnvioAdaptado> entry : envios.entrySet()) {
            EnvioAdaptado envio = entry.getValue();
            sb.append("ID Envío: ").append(entry.getKey()).append("\n").append(envio.toString()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "📍 ENVÍOS", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void aplicarDescuentos() {
        try {
            var productos = facade.obtenerTodosProductos();
            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "❌ No hay productos.", "⚠️ ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String[] nombresProductos = new String[productos.size()];
            for (int i = 0; i < productos.size(); i++) {
                nombresProductos[i] = productos.get(i).getNombre();
            }
            
            String seleccionProducto = (String) JOptionPane.showInputDialog(null, "Seleccione el producto:", "🎁 APLICAR DESCUENTOS", JOptionPane.QUESTION_MESSAGE, null, nombresProductos, nombresProductos[0]);
            if (seleccionProducto == null) return;
            
            int indexProducto = -1;
            for (int i = 0; i < nombresProductos.length; i++) {
                if (nombresProductos[i].equals(seleccionProducto)) {
                    indexProducto = i;
                    break;
                }
            }
            Productos producto = productos.get(indexProducto);
            
            String[] opciones = {"💰 Solo Descuento", "💰 Descuento + IGV (18%)", "💰 Descuento + IGV + Envío Express"};
            int seleccion = JOptionPane.showOptionDialog(null, "Seleccione el tipo de cálculo:", "🎁 APLICAR DESCUENTOS", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            if (seleccion == -1) return;
            
            String descuentoStr = JOptionPane.showInputDialog(null, "Ingrese el porcentaje de descuento (0-100):", "🎁 DESCUENTOS", JOptionPane.QUESTION_MESSAGE);
            if (descuentoStr == null) return;
            double descuento = Double.parseDouble(descuentoStr);
            
            StringBuilder resultado = new StringBuilder("===== 🎁 CÁLCULO DE PRECIO =====\n\n");
            
            switch (seleccion) {
                case 0:
                    ComponenteProducto base1 = new ProductoBase(producto);
                    ComponenteProducto con1 = new DecoradorDescuento(base1, descuento);
                    resultado.append(con1.getDetalles());
                    break;
                case 1:
                    ComponenteProducto base2 = new ProductoBase(producto);
                    ComponenteProducto con2 = new DecoradorDescuento(base2, descuento);
                    ComponenteProducto con2b = new DecoradorImpuesto(con2);
                    resultado.append(con2b.getDetalles());
                    break;
                case 2:
                    String envioStr = JOptionPane.showInputDialog(null, "Ingrese el costo del envío express (S/):", "🎁 DESCUENTOS", JOptionPane.QUESTION_MESSAGE);
                    if (envioStr == null) return;
                    double costoEnvio = Double.parseDouble(envioStr);
                    resultado.append(facade.obtenerDetallesConDecoradores(producto, descuento, costoEnvio));
                    break;
            }
            
            JOptionPane.showMessageDialog(null, resultado.toString(), "📊 RESULTADO", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ Por favor ingrese valores válidos.", "⚠️ ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static void verHistorialVentas() {
        var ventas = facade.obtenerTodasLasVentas();
        if (ventas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "❌ No hay ventas registradas.", "💵 VENTAS", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder("===== 💵 HISTORIAL DE VENTAS =====\n\n");
        int contador = 1;
        for (Venta v : ventas) {
            sb.append(contador).append(". Venta ID: ").append(v.getIdVenta()).append("\n")
              .append("   Cliente: ").append(v.getCliente().getNombre()).append("\n")
              .append("   Producto: ").append(v.getProducto().getNombre()).append("\n")
              .append("   Cantidad: ").append(v.getCantidad()).append(" unidades\n")
              .append("   Precio Unitario: S/").append(String.format("%.2f", v.getPrecioUnitario())).append("\n")
              .append("   Total: S/").append(String.format("%.2f", v.getPrecioTotal())).append("\n")
              .append("   Envío ID: ").append(v.getIdEnvioAsociado()).append("\n")
              .append("   Fecha: ").append(v.getFechaVenta()).append("\n\n");
            contador++;
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "💵 VENTAS", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void generarReporte() {
        var ventas = facade.obtenerTodasLasVentas();
        double totalVentas = facade.obtenerTotalVentas();
        
        StringBuilder sb = new StringBuilder("===== 📊 REPORTE DE VENTAS =====\n\n");
        sb.append("Total de Ventas: ").append(ventas.size()).append("\n");
        sb.append("Monto Total: S/").append(String.format("%.2f", totalVentas)).append("\n");
        sb.append("Promedio por Venta: S/").append(String.format("%.2f", ventas.isEmpty() ? 0 : totalVentas / ventas.size())).append("\n\n");
        
        sb.append("Productos Más Vendidos:\n");
        Map<String, Integer> productosVendidos = new HashMap<>();
        for (Venta v : ventas) {
            String nombreProducto = v.getProducto().getNombre();
            productosVendidos.put(nombreProducto, productosVendidos.getOrDefault(nombreProducto, 0) + v.getCantidad());
        }
        int orden = 1;
        for (Map.Entry<String, Integer> entry : productosVendidos.entrySet()) {
            sb.append(orden).append(". ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" unidades\n");
            orden++;
        }
        
        sb.append("\n✅ Clientes Registrados: ").append(facade.obtenerTodosClientes().size()).append("\n");
        sb.append("✅ Productos en Inventario: ").append(facade.obtenerTodosProductos().size()).append("\n");
        sb.append("✅ Envíos Registrados: ").append(envios.size()).append("\n");
        
        JOptionPane.showMessageDialog(null, sb.toString(), "📊 REPORTE", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void menuProductos() {
        String[] opciones = {"📦 Crear Producto", "📋 Ver Inventario", "⬅️ Volver"};
        int seleccion = JOptionPane.showOptionDialog(
            null, "=== GESTIÓN DE PRODUCTOS ===", "📦 PRODUCTOS",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        
        if (seleccion == -1 || seleccion == 2) return;
        
        switch (seleccion) {
            case 0: crearProducto(); break;
            case 1: verInventario(); break;
        }
    }
    
    private static void menuClientes() {
        String[] opciones = {"👤 Registrar Cliente", "👥 Ver Clientes", "⬅️ Volver"};
        int seleccion = JOptionPane.showOptionDialog(
            null, "=== GESTIÓN DE CLIENTES ===", "👤 CLIENTES",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        
        if (seleccion == -1 || seleccion == 2) return;
        
        switch (seleccion) {
            case 0: registrarCliente(); break;
            case 1: verClientes(); break;
        }
    }
    
    private static void menuVentas() {
        String[] opciones = {"💰 Vender Producto", "⬅️ Volver"};
        int seleccion = JOptionPane.showOptionDialog(
            null, "=== GESTIÓN DE VENTAS ===", "💰 VENTAS",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        
        if (seleccion == -1 || seleccion == 1) return;
        
        if (seleccion == 0) venderProductoCompleto();
    }
    
    private static void menuEnvios() {
        String[] opciones = {"📮 Crear Envío", "📍 Ver Estado de Envíos", "⬅️ Volver"};
        int seleccion = JOptionPane.showOptionDialog(
            null, "=== GESTIÓN DE ENVÍOS ===", "📮 ENVÍOS",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        
        if (seleccion == -1 || seleccion == 2) return;
        
        switch (seleccion) {
            case 0: crearEnvio(); break;
            case 1: verEstadosEnvios(); break;
        }
    }
    
    private static void menuReportes() {
        String[] opciones = {"💵 Ver Historial de Ventas", "📊 Reporte de Ventas", "⬅️ Volver"};
        int seleccion = JOptionPane.showOptionDialog(
            null, "=== REPORTES ===", "💵 REPORTES",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        
        if (seleccion == -1 || seleccion == 2) return;
        
        switch (seleccion) {
            case 0: verHistorialVentas(); break;
            case 1: generarReporte(); break;
        }
    }
    
    private static String generarNumeroRastreo() {
        return "TRK" + System.currentTimeMillis() % 1000000;
    }
}
