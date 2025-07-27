package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reserva implements Pago {
    private String id;
    private Cliente cliente;
    private Habitacion habitacion;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean checkIn;
    private boolean checkOut;
    private List<ServicioAdicional> servicios = new ArrayList<>();

    // Constructor completo
    public Reserva(String id, Cliente cliente, Habitacion habitacion,
                   Date fechaInicio, Date fechaFin, boolean checkIn, boolean checkOut) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // Constructor sin checkIn/checkOut (por defecto en false)
    public Reserva(String id, Cliente cliente, Habitacion habitacion,
                   Date fechaInicio, Date fechaFin) {
        this(id, cliente, habitacion, fechaInicio, fechaFin, false, false);
    }

    // Getters
    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public boolean isCheckOut() {
        return checkOut;
    }

    public List<ServicioAdicional> getServicios() {
        return servicios;
    }

    // Setters
    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(boolean checkOut) {
        this.checkOut = checkOut;
    }

    public void setServicios(List<ServicioAdicional> servicios) {
        this.servicios = servicios;
    }

    public void agregarServicio(ServicioAdicional servicio) {
        this.servicios.add(servicio);
    }

    // Implementación del cálculo total
    @Override
    public double calcularTotal() {
        long diff = fechaFin.getTime() - fechaInicio.getTime();
        int dias = (int) (diff / (1000 * 60 * 60 * 24));
        if (dias <= 0) dias = 1; // al menos una noche

        double total = dias * habitacion.getTipo().getPrecioPorNoche();

        for (ServicioAdicional servicio : servicios) {
            total += servicio.getPrecio();
        }

        return total;
    }
}
