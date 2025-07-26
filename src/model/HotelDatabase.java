package model;
import java.util.Date;
import java.util.List;

public interface HotelDatabase {
    // Métodos para Clientes
    void guardarCliente(Cliente cliente);
    Cliente obtenerCliente(String dni);
    List<Cliente> listarClientes();
    
    // Métodos para Habitaciones
    void guardarHabitacion(Habitacion habitacion);
    Habitacion obtenerHabitacion(int numero);
    List<Habitacion> listarHabitaciones();
    List<Habitacion> listarHabitacionesDisponibles();
    
    // Métodos para Reservas
    void guardarReserva(Reserva reserva);
    Reserva obtenerReserva(String id);
    List<Reserva> listarReservas();
    List<Reserva> buscarReservasPorApellido(String apellido);
    List<Reserva> buscarReservasPorFecha(Date fecha);
    
    // Métodos para Usuarios
    Usuario autenticar(String username, String password);

    void eliminarCliente(String dni);

    void eliminarReserva(String idReserva);
    
}
