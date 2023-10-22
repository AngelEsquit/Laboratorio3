public class Bebida extends Producto {
    private int mililitros;
    private String tipo;
    
    public Bebida(int id, String nombre, int cantidad_disponible, int cantidad_vendidos, String estado, float precio,
            int mililitros, String tipo) {
        super(id, nombre, cantidad_disponible, cantidad_vendidos, estado, precio);
        this.mililitros = mililitros;
        this.tipo = tipo;
    }

    public int getMililitros() {
        return mililitros;
    }

    public void setMililitros(int mililitros) {
        this.mililitros = mililitros;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return id + ": " + nombre + " - Cantidad disponible: " + cantidad_disponible
                + " - Cantidad vendidos: " + cantidad_vendidos + " - Estado: " + estado + " - Precio: " + precio
                + " - Mililitros: " + mililitros + " - Tipo: " + tipo;
    }
}
