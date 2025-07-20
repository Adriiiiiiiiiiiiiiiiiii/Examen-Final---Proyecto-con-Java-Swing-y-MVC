package model;

public enum TipoHabitacion {
    INDIVIDUAL("Individual", 100),
    DOBLE("Doble", 180),
    SUITE("Suite", 300);

    private final String descripcion;
    private final double precioPorNoche;

    TipoHabitacion(String descripcion, double precioPorNoche) {
        this.descripcion = descripcion;
        this.precioPorNoche = precioPorNoche;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }
}