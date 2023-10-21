public class Dulce extends Producto {
    private String sabor;
    private String tamanio;

    public Dulce(int id, String nombre, int cantidad_disponible, int cantidad_vendidos, String estado, float precio,
            String sabor, String tamanio) {
        super(id, nombre, cantidad_disponible, cantidad_vendidos, estado, precio);
        this.sabor = sabor;
        this.tamanio = tamanio;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }
}
