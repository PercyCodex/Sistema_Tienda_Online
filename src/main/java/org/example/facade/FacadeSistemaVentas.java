package org.example.facade;

import java.util.List;

import org.example.adapter.DhlAdapter;
import org.example.adapter.EnvioAdaptado;
import org.example.adapter.OlvaAdapter;
import org.example.adapter.ShalomAdapter;
import org.example.decorator.ComponenteProducto;
import org.example.decorator.DecoradorDescuento;
import org.example.decorator.DecoradorEnvioExpress;
import org.example.decorator.DecoradorImpuesto;
import org.example.decorator.ProductoBase;
import org.example.factory.ProductoFactory;
import org.example.factory.UsuarioFactory;
import org.example.model.Cliente;
import org.example.model.Productos;
import org.example.model.Usuario;
import org.example.model.Venta;
import org.example.service.RepositorioClientes;
import org.example.service.RepositorioProductos;
import org.example.service.RepositorioVentas;
import org.example.service.ServicioAutenticacion;

public class FacadeSistemaVentas {
    
    private ServicioAutenticacion auth;
    private RepositorioProductos repoProductos;
    private RepositorioClientes repoClientes;
    private RepositorioVentas repoVentas;

    public FacadeSistemaVentas() {
        this.auth = ServicioAutenticacion.getInstancia();
        this.repoProductos = RepositorioProductos.getInstancia();
        this.repoClientes = RepositorioClientes.getInstancia();
        this.repoVentas = RepositorioVentas.getInstancia();
        
        // Crear admin
        Usuario admin = UsuarioFactory.crearUsuario("ADMIN", "admin", "admin123");
        this.auth.registrarUsuario(admin);
    }

    // ===== AUTENTICACIÓN =====
    public Usuario autenticar(String username, String password) {
        return auth.autenticar(username, password);
    }

    // ===== GESTIÓN DE PRODUCTOS =====
    public void crearProductoRopa(int id, String nombre, double precio, int stock, String color, String talla) {
        Productos producto = ProductoFactory.crearRopa(id, nombre, precio, stock, color, talla);
        repoProductos.agregarProducto(producto);
    }

    public void crearProductoCosmetico(int id, String nombre, double precio, int stock, String marca) {
        Productos producto = ProductoFactory.crearCosmetico(id, nombre, precio, stock, marca);
        repoProductos.agregarProducto(producto);
    }

    public List<Productos> obtenerTodosProductos() {
        return repoProductos.getListaProductos();
    }

    public Productos obtenerProductoPorId(int id) {
        for (Productos p : repoProductos.getListaProductos()) {
            if (p.getIdProductos() == id) {
                return p;
            }
        }
        return null;
    }

    // ===== GESTIÓN DE CLIENTES =====
    public void registrarCliente(String dni, String nombre, String telefono) {
        Cliente cliente = new Cliente(dni, nombre, telefono);
        repoClientes.agregarCliente(cliente);
    }

    public List<Cliente> obtenerTodosClientes() {
        return repoClientes.getListaClientes();
    }

    public Cliente obtenerClientePorDni(String dni) {
        return repoClientes.buscarPorDni(dni);
    }

    // ===== GESTIÓN DE VENTAS =====
    public void registrarVenta(Cliente cliente, Productos producto, int cantidad, 
                               double precioUnitario, double precioTotal, int idEnvioAsociado) {
        Venta venta = new Venta(0, cliente, producto, cantidad, precioUnitario, precioTotal, idEnvioAsociado);
        repoVentas.agregarVenta(venta);
    }

    public List<Venta> obtenerTodasLasVentas() {
        return repoVentas.getListaVentas();
    }

    public List<Venta> obtenerVentasDelCliente(String dni) {
        return repoVentas.obtenerVentasPorCliente(dni);
    }

    public double obtenerTotalVentas() {
        return repoVentas.obtenerTotalVentas();
    }

    public double calcularVenta(Productos producto, int cantidad) {
        return producto.getPrecio() * cantidad;
    }

    public boolean verificarStock(Productos producto, int cantidad) {
        return producto.getStock() >= cantidad;
    }

    public void actualizarStock(Productos producto, int cantidad) {
        producto.setStock(producto.getStock() - cantidad);
    }

    // ===== ADAPTER: GESTIÓN DE ENVÍOS =====
    public EnvioAdaptado crearEnvioOlva(String codigoOlva, String provincia,
                                        String estado, double costo) {
        return new OlvaAdapter(codigoOlva, provincia, estado, costo);
    }

    public EnvioAdaptado crearEnvioShalom(String idShalom, String location,
                                            String status, double precio) {
        return new ShalomAdapter(idShalom, location, status, precio);
    }

    public EnvioAdaptado crearEnvioDhl(String tracking, String destination,
                                        String status, double cost) {
        return new DhlAdapter(tracking, destination, status, cost);
    }

    // ===== DECORATOR: APLICAR DESCUENTOS =====
    public double aplicarDescuento(Productos producto, double porcentajeDescuento) {
        ComponenteProducto base = new ProductoBase(producto);
        ComponenteProducto conDescuento = new DecoradorDescuento(base, porcentajeDescuento);
        return conDescuento.getPrecioFinal();
    }

    public double aplicarDescuentoYImpuesto(Productos producto, double porcentajeDescuento) {
        ComponenteProducto base = new ProductoBase(producto);
        ComponenteProducto conDescuento = new DecoradorDescuento(base, porcentajeDescuento);
        ComponenteProducto conImpuesto = new DecoradorImpuesto(conDescuento);
        return conImpuesto.getPrecioFinal();
    }

    public double aplicarEnvioExpress(Productos producto, double costoEnvio) {
        ComponenteProducto base = new ProductoBase(producto);
        ComponenteProducto conEnvio = new DecoradorEnvioExpress(base, costoEnvio);
        return conEnvio.getPrecioFinal();
    }

    // Aplicar todo: descuento + impuesto + envío express
    public double aplicarTodoDecorador(Productos producto, double porcentajeDescuento, 
                                        double costoEnvio) {
        ComponenteProducto base = new ProductoBase(producto);
        ComponenteProducto conDescuento = new DecoradorDescuento(base, porcentajeDescuento);
        ComponenteProducto conImpuesto = new DecoradorImpuesto(conDescuento);
        ComponenteProducto conEnvio = new DecoradorEnvioExpress(conImpuesto, costoEnvio);
        return conEnvio.getPrecioFinal();
    }

    // Obtener detalles con decoradores
    public String obtenerDetallesConDecoradores(Productos producto, double porcentajeDescuento, 
                                                double costoEnvio) {
        ComponenteProducto base = new ProductoBase(producto);
        ComponenteProducto conDescuento = new DecoradorDescuento(base, porcentajeDescuento);
        ComponenteProducto conImpuesto = new DecoradorImpuesto(conDescuento);
        ComponenteProducto conEnvio = new DecoradorEnvioExpress(conImpuesto, costoEnvio);
        return conEnvio.getDetalles();
    }
}