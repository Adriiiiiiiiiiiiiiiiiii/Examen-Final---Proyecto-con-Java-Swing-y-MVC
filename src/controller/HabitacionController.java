package controller;

import java.util.List;
import java.util.stream.Collectors;
import model.Habitacion;
import model.HotelDatabase;
import model.TipoHabitacion;
import view.HabitacionView;

public class HabitacionController {
    private HabitacionView view;
    private HotelDatabase model;

    public HabitacionController(HabitacionView view, HotelDatabase model) {
        this.view = view;
        this.model = model;
        
        configurarListeners();
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
        mostrarHabitacionesEnTabla(habitaciones);
    }
    
    private void mostrarHabitacionesEnTabla(List<Habitacion> habitaciones) {
        Object[][] data = new Object[habitaciones.size()][3];
        
        for (int i = 0; i < habitaciones.size(); i++) {
            Habitacion h = habitaciones.get(i);
            data[i][0] = h.getNumero();
            data[i][1] = h.getTipo().getDescripcion();
            data[i][2] = h.isDisponible() ? "Disponible" : "Ocupada";
        }
        
        String[] columnNames = {"Número", "Tipo", "Estado"};
        view.actualizarTablaHabitaciones(data, columnNames);
    }
    
    private void agregarHabitacion() {
        Object[] resultado = view.mostrarDialogoHabitacion(0, "");
        
        if (resultado != null) {
            try {
                int numero = Integer.parseInt(resultado[0].toString());
                TipoHabitacion tipo = TipoHabitacion.valueOf(resultado[1].toString().toUpperCase());
                
                if (model.obtenerHabitacion(numero) != null) {
                    view.mostrarMensaje("Ya existe una habitación con ese número");
                    return;
                }
                
                Habitacion nuevaHabitacion = new Habitacion(numero, tipo);
                model.guardarHabitacion(nuevaHabitacion);
                view.mostrarMensaje("Habitación agregada exitosamente");
                cargarHabitaciones();
                
            } catch (NumberFormatException e) {
                view.mostrarMensaje("El número de habitación debe ser un valor numérico");
            } catch (Exception e) {
                view.mostrarMensaje("Error al agregar habitación: " + e.getMessage());
            }
        }
    }
    
    private void editarHabitacion() {
        int numero = view.getHabitacionSeleccionada();
        if (numero == -1) {
            view.mostrarMensaje("Seleccione una habitación para editar");
            return;
        }
        
        Habitacion habitacion = model.obtenerHabitacion(numero);
        if (habitacion != null) {
            Object[] resultado = view.mostrarDialogoHabitacion(
                habitacion.getNumero(), 
                habitacion.getTipo().name()
            );
            
            if (resultado != null) {
                try {
                    TipoHabitacion nuevoTipo = TipoHabitacion.valueOf(resultado[1].toString().toUpperCase());
                    habitacion.setTipo(nuevoTipo);
                    
                    model.guardarHabitacion(habitacion);
                    view.mostrarMensaje("Habitación actualizada exitosamente");
                    cargarHabitaciones();
                    
                } catch (Exception e) {
                    view.mostrarMensaje("Error al actualizar habitación: " + e.getMessage());
                }
            }
        }
    }
    
    private void cambiarEstadoHabitacion() {
        int numero = view.getHabitacionSeleccionada();
        if (numero == -1) {
            view.mostrarMensaje("Seleccione una habitación para cambiar estado");
            return;
        }
        
        Habitacion habitacion = model.obtenerHabitacion(numero);
        if (habitacion != null) {
            habitacion.setDisponible(!habitacion.isDisponible());
            model.guardarHabitacion(habitacion);
            cargarHabitaciones();
            view.mostrarMensaje("Estado de la habitación actualizado");
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
        .collect(Collectors.toList()); 

    mostrarHabitacionesEnTabla(habitacionesFiltradas);
}
}