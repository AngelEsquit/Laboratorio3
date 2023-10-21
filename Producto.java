public class Producto {
    protected int id;
    protected String nombre;
    protected int cantidad_disponible;
    protected int cantidad_vendidos;
    protected String estado;
    protected float precio;
    
    public Producto(int id, String nombre, int cantidad_disponible, int cantidad_vendidos, String estado, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad_disponible = cantidad_disponible;
        this.cantidad_vendidos = cantidad_vendidos;
        this.estado = estado;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad_disponible() {
        return cantidad_disponible;
    }

    public void setCantidad_disponible(int cantidad_disponible) {
        this.cantidad_disponible = cantidad_disponible;
    }

    public int getCantidad_vendidos() {
        return cantidad_vendidos;
    }

    public void setCantidad_vendidos(int cantidad_vendidos) {
        this.cantidad_vendidos = cantidad_vendidos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
