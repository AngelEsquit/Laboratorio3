public class Snack extends Producto {
    private int gramos;
    private String sabor;
    private String tamanio;
    
    public Snack(int id, String nombre, int cantidad_disponible, int cantidad_vendidos, String estado, int precio,
            int gramos, String sabor, String tamanio) {
        super(id, nombre, cantidad_disponible, cantidad_vendidos, estado, precio);
        this.gramos = gramos;
        this.sabor = sabor;
        this.tamanio = tamanio;
    }

    public int getGramos() {
        return gramos;
    }

    public void setGramos(int gramos) {
        this.gramos = gramos;
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
