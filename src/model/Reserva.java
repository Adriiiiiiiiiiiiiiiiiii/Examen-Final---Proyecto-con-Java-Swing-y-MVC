package model;

import java.util.Date;

public class Reserva implements Pago {
    private String id;
    private Cliente cliente;
    private Habitacion habitacion;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean checkIn;
    private boolean checkOut;

    public Reserva(String id, Cliente cliente, Habitacion habitacion, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.checkIn = false;
        this.checkOut = false;
    }

    // Getters y setters
    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Habitacion getHabitacion() { return habitacion; }
    public Date getFechaInicio() { return fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public boolean isCheckIn() { return checkIn; }
    public boolean isCheckOut() { return checkOut; }
    
    public void setCheckIn(boolean checkIn) { this.checkIn = checkIn; }
    public void setCheckOut(boolean checkOut) { this.checkOut = checkOut; }

    @Override
    public double calcularTotal() {
        long diff = fechaFin.getTime() - fechaInicio.getTime();
        int dias = (int) (diff / (1000 * 60 * 60 * 24));
        return dias * habitacion.getTipo().getPrecioPorNoche();
    }
}