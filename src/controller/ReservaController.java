package controller;



import model.*;
import view.ReservaView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReservaController {
    private ReservaView view;
    private HotelDatabase model;
    private Usuario usuario;

    public ReservaController(ReservaView view, HotelDatabase model, Usuario usuario) {
        this.view = view;
        this.model = model;
        this.usuario = usuario;
        
        // Configurar listeners
        configurarListeners();
        
        // Cargar datos iniciales
        cargarReservas();
    }
    
    private void configurarListeners() {
        view.agregarNuevaReservaListener(e -> nuevaReserva());
        view.agregarCancelarReservaListener(e -> cancelarReserva());
        view.agregarCheckInListener(e -> hacerCheckIn());
        view.agregarCheckOutListener(e -> hacerCheckOut());
        view.agregarBuscarPorFechaListener(e -> buscarPorFecha());
        view.agregarBuscarPorApellidoListener(e -> buscarPorApellido());
        view.agregarFiltroEstadoListener(e -> filtrarPorEstado());
    }
    
    private void cargarReservas() {
        List<Reserva> reservas = model.listarReservas();
        mostrarReservasEnTabla(reservas);
    }
    
    private void mostrarReservasEnTabla(List<Reserva> reservas) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Object[][] data = new Object[reservas.size()][7];
        
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            data[i][0] = r.getId();
            data[i][1] = r.getCliente().getNombre() + " " + r.getCliente().getApellido();
            data[i][2] = r.getHabitacion().getNumero() + " - " + r.getHabitacion().getTipo().getDescripcion();
            data[i][3] = sdf.format(r.getFechaInicio());
            data[i][4] = sdf.format(r.getFechaFin());
            data[i][5] = r.isCheckIn() ? "Sí" : "No";
            data[i][6] = r.isCheckOut() ? "Sí" : "No";
        }
        
        String[] columnNames = {"ID", "Cliente", "Habitación", "Fecha Inicio", "Fecha Fin", "Check-In", "Check-Out"};
        view.actualizarTablaReservas(data, columnNames);
    }
    
    private void nuevaReserva() {
        Object[] datos = view.mostrarDialogoNuevaReserva();
        if (datos != null) {
            try {
                // Validar y procesar datos
                String dniCliente = (String) datos[0];
                int numeroHabitacion = Integer.parseInt((String) datos[1]);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaInicio = sdf.parse((String) datos[2]);
                Date fechaFin = sdf.parse((String) datos[3]);
                
                // Verificar disponibilidad
                if (!model.obtenerHabitacion(numeroHabitacion).isDisponible()) {
                    view.mostrarMensaje("La habitación no está disponible");
                    return;
                }
                
                // Crear reserva
                Cliente cliente = model.obtenerCliente(dniCliente);
                Habitacion habitacion = model.obtenerHabitacion(numeroHabitacion);
                
                if (cliente == null || habitacion == null) {
                    view.mostrarMensaje("Cliente o habitación no encontrados");
                    return;
                }
                
                String idReserva = "RES-" + System.currentTimeMillis();
                Reserva reserva = new Reserva(idReserva, cliente, habitacion, fechaInicio, fechaFin);
                
                // Guardar reserva y actualizar estado de habitación
                model.guardarReserva(reserva);
                habitacion.setDisponible(false);
                model.guardarHabitacion(habitacion);
                
                view.mostrarMensaje("Reserva creada exitosamente");
                cargarReservas();
                
            } catch (Exception e) {
                view.mostrarMensaje("Error en los datos: " + e.getMessage());
            }
        }
    }
    
    private void cancelarReserva() {
        String id = view.getReservaSeleccionada();
        if (id == null) {
            view.mostrarMensaje("Seleccione una reserva para cancelar");
            return;
        }
        
        // Implementar lógica para cancelar reserva
        // Liberar habitación
        // Actualizar tabla
    }
    
    private void hacerCheckIn() {
        String id = view.getReservaSeleccionada();
        if (id == null) {
            view.mostrarMensaje("Seleccione una reserva para hacer check-in");
            return;
        }
        
        Reserva reserva = model.obtenerReserva(id);
        if (reserva != null) {
            reserva.setCheckIn(true);
            model.guardarReserva(reserva);
            cargarReservas();
        }
    }
    
    private void hacerCheckOut() {
        String id = view.getReservaSeleccionada();
        if (id == null) {
            view.mostrarMensaje("Seleccione una reserva para hacer check-out");
            return;
        }
        
        Reserva reserva = model.obtenerReserva(id);
        if (reserva != null && reserva.isCheckIn()) {
            reserva.setCheckOut(true);
            reserva.getHabitacion().setDisponible(true);
            model.guardarReserva(reserva);
            model.guardarHabitacion(reserva.getHabitacion());
            cargarReservas();
        } else {
            view.mostrarMensaje("No se puede hacer check-out sin check-in previo");
        }
    }
    
    private void buscarPorFecha() {
        String fechaStr = view.getTextoBusqueda();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = sdf.parse(fechaStr);
            List<Reserva> reservas = model.buscarReservasPorFecha(fecha);
            mostrarReservasEnTabla(reservas);
        } catch (Exception e) {
            view.mostrarMensaje("Formato de fecha inválido (dd/mm/aaaa)");
        }
    }
    
    private void buscarPorApellido() {
        String apellido = view.getTextoBusqueda();
        if (apellido.isEmpty()) {
            view.mostrarMensaje("Ingrese un apellido para buscar");
            return;
        }
        
        List<Reserva> reservas = model.buscarReservasPorApellido(apellido);
        mostrarReservasEnTabla(reservas);
    }
    
    private void filtrarPorEstado() {
    String estado = view.getEstadoFiltroSeleccionado();
    List<Reserva> reservas = model.listarReservas();

    List<Reserva> filtradas = reservas.stream()
        .filter(r -> 
            estado.equals("Todas") ||
            (estado.equals("Pendientes") && !r.isCheckIn() && !r.isCheckOut()) ||
            (estado.equals("Check-In") && r.isCheckIn() && !r.isCheckOut()) ||
            (estado.equals("Check-Out") && r.isCheckOut()) ||
            (estado.equals("Canceladas") && false) // Cambia esto si tienes un campo real para cancelación
        )
        .toList();

    mostrarReservasEnTabla(filtradas);
}
}