package model;

public class ServicioAdicional implements Pago {
    private String nombre;
    private double precio;

    public ServicioAdicional(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public double calcularTotal() {
        return precio;
    }

    // Getters
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
}
