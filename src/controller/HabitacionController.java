package controller;


import model.Habitacion;
import model.HotelDatabase;
import view.HabitacionView;

import java.util.List;

public class HabitacionController {
    private HabitacionView view;
    private HotelDatabase model;

    public HabitacionController(HabitacionView view, HotelDatabase model) {
        this.view = view;
        this.model = model;
        
        // Configurar listeners
        configurarListeners();
        
        // Cargar datos iniciales
        cargarHabitaciones();
    }
    
    private void configurarListeners() {
        view.agregarAgregarListener(e -> agregarHabitacion());
        view.agregarEditarListener(e -> editarHabitacion());
        view.agregarCambiarEstadoListener(e -> cambiarEstadoHabitacion());
        view.agregarFiltrarListener(e -> filtrarHabitaciones());
        view.agregarLimpiarFiltrosListener(e -> cargarHabitaciones());
    }
    
    private void cargarHabitaciones() {
        List<Habitacion> habitaciones = model.listarHabitaciones();
        Object[][] data = new Object[habitaciones.size()][4];
        
        for (int i = 0; i < habitaciones.size(); i++) {
            Habitacion h = habitaciones.get(i);
            data[i][0] = h.getNumero();
            data[i][1] = h.getTipo().getDescripcion();
            data[i][2] = h.getTipo().getPrecioPorNoche();
            data[i][3] = h.isDisponible() ? "Disponible" : "Ocupada";
        }
        
        String[] columnNames = {"Número", "Tipo", "Precio/Noche", "Estado"};
        view.actualizarTablaHabitaciones(data, columnNames);
    }
    
    private void agregarHabitacion() {
        // Implementar lógica para agregar nueva habitación
        // Mostrar diálogo para ingresar datos
        // Validar datos
        // Guardar en la base de datos
        // Actualizar tabla
    }
    
    private void editarHabitacion() {
        int numero = view.getHabitacionSeleccionada();
        if (numero == -1) {
            view.mostrarMensaje("Seleccione una habitación para editar");
            return;
        }
        
        // Implementar lógica para editar habitación
    }
    
    private void cambiarEstadoHabitacion() {
        int numero = view.getHabitacionSeleccionada();
        if (numero == -1) {
            view.mostrarMensaje("Seleccione una habitación para cambiar estado");
            return;
        }
        
        // Implementar lógica para cambiar estado
        Habitacion habitacion = model.obtenerHabitacion(numero);
        if (habitacion != null) {
            habitacion.setDisponible(!habitacion.isDisponible());
            model.guardarHabitacion(habitacion);
            cargarHabitaciones();
        }
    }
    
    private void filtrarHabitaciones() {
        String tipo = view.getTipoFiltroSeleccionado();
        String disponibilidad = view.getDisponibilidadFiltroSeleccionado();
        
        List<Habitacion> habitacionesFiltradas = model.listarHabitaciones()
            .stream()
            .filter(h -> tipo.equals("Todos") || h.getTipo().getDescripcion().equals(tipo))
            .filter(h -> disponibilidad.equals("Todas") || 
                  (disponibilidad.equals("Disponibles") && h.isDisponible()) || 
                  (disponibilidad.equals("Ocupadas") && !h.isDisponible()))
            .toList();
        
        Object[][] data = new Object[habitacionesFiltradas.size()][4];
        for (int i = 0; i < habitacionesFiltradas.size(); i++) {
            Habitacion h = habitacionesFiltradas.get(i);
            data[i][0] = h.getNumero();
            data[i][1] = h.getTipo().getDescripcion();
            data[i][2] = h.getTipo().getPrecioPorNoche();
            data[i][3] = h.isDisponible() ? "Disponible" : "Ocupada";
        }
        
        String[] columnNames = {"Número", "Tipo", "Precio/Noche", "Estado"};
        view.actualizarTablaHabitaciones(data, columnNames);
    }
}