package model;

public class Habitacion {
    private int numero;
    private TipoHabitacion tipo;
    private boolean disponible;

    // Constructor con parámetro disponible
    public Habitacion(int numero, TipoHabitacion tipo, boolean disponible) {
        this.numero = numero;
        this.tipo = tipo;
        this.disponible = disponible;
    }

    // Constructor que asume disponible=true por defecto
    public Habitacion(int numero, TipoHabitacion tipo) {
        this(numero, tipo, true); // Llama al constructor principal con disponible=true
    }

    // Getters y setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}