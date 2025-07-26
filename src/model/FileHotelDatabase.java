package model;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FileHotelDatabase implements HotelDatabase {
    private static final String CLIENTES_FILE = "data/clientes.txt";
    private static final String HABITACIONES_FILE = "data/habitaciones.txt";
    private static final String RESERVAS_FILE = "data/reservas.txt";
    private static final String USUARIOS_FILE = "data/usuarios.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public FileHotelDatabase() {
        // Crear directorio si no existe
        new File("data").mkdirs();
        
        // Crear archivos si no existen
        crearArchivoSiNoExiste(CLIENTES_FILE);
        crearArchivoSiNoExiste(HABITACIONES_FILE);
        crearArchivoSiNoExiste(RESERVAS_FILE);
        crearArchivoSiNoExiste(USUARIOS_FILE);
        
        // Insertar datos iniciales si los archivos están vacíos
        if (estaVacio(USUARIOS_FILE)) {
            insertarDatosIniciales();
        }
    }

    private void crearArchivoSiNoExiste(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creando archivo " + filename + ": " + e.getMessage());
        }
    }

    private boolean estaVacio(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            return br.readLine() == null;
        } catch (IOException e) {
            System.err.println("Error verificando archivo " + filename + ": " + e.getMessage());
            return true;
        }
    }

    private void insertarDatosIniciales() {
        try {
            // Usuarios iniciales
            guardarUsuario(new Administrador("admin", "admin123", "Administrador Principal"));
            guardarUsuario(new Recepcionista("recepcion", "recepcion123", "Recepcionista"));
            
            // Algunas habitaciones de ejemplo
            guardarHabitacion(new Habitacion(101, TipoHabitacion.INDIVIDUAL));
            guardarHabitacion(new Habitacion(102, TipoHabitacion.DOBLE));
            guardarHabitacion(new Habitacion(201, TipoHabitacion.SUITE));
        } catch (Exception e) {
            System.err.println("Error insertando datos iniciales: " + e.getMessage());
        }
    }

    // Métodos para clientes
    @Override
    public void guardarCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo");
        Objects.requireNonNull(cliente.getDni(), "El DNI del cliente no puede ser nulo");
        
        try {
            List<Cliente> clientes = listarClientes();
            clientes.removeIf(c -> c.getDni().equals(cliente.getDni()));
            clientes.add(cliente);
            guardarListaClientes(clientes);
        } catch (Exception e) {
            System.err.println("Error guardando cliente: " + e.getMessage());
            throw new RuntimeException("Error al guardar cliente", e);
        }
    }

    @Override
    public Cliente obtenerCliente(String dni) {
        try {
            return listarClientes().stream()
                    .filter(c -> c.getDni().equals(dni))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println("Error obteniendo cliente: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(CLIENTES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 3) { // Mínimo DNI, nombre y apellido
                        clientes.add(new Cliente(
                                parts[0], // dni
                                parts[1], // nombre
                                parts[2], // apellido
                                parts.length > 3 ? parts[3] : "", // telefono
                                parts.length > 4 ? parts[4] : ""  // email
                        ));
                    }
                } catch (Exception e) {
                    System.err.println("Error procesando línea de cliente: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de clientes: " + e.getMessage());
        }
        
        return clientes;
    }

    @Override
    public void eliminarCliente(String dni) {
        try {
            List<Cliente> clientes = listarClientes();
            clientes.removeIf(c -> c.getDni().equals(dni));
            guardarListaClientes(clientes);
        } catch (Exception e) {
            System.err.println("Error eliminando cliente: " + e.getMessage());
            throw new RuntimeException("Error al eliminar cliente", e);
        }
    }

    private void guardarListaClientes(List<Cliente> clientes) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CLIENTES_FILE))) {
            for (Cliente c : clientes) {
                pw.println(String.join("|",
                        c.getDni(),
                        c.getNombre(),
                        c.getApellido(),
                        c.getTelefono() != null ? c.getTelefono() : "",
                        c.getEmail() != null ? c.getEmail() : ""
                ));
            }
        } catch (IOException e) {
            System.err.println("Error guardando lista de clientes: " + e.getMessage());
            throw new RuntimeException("Error al guardar lista de clientes", e);
        }
    }

    // Métodos para habitaciones
    @Override
    public void guardarHabitacion(Habitacion habitacion) {
        Objects.requireNonNull(habitacion, "La habitación no puede ser nula");
        
        try {
            List<Habitacion> habitaciones = listarHabitaciones();
            habitaciones.removeIf(h -> h.getNumero() == habitacion.getNumero());
            habitaciones.add(habitacion);
            guardarListaHabitaciones(habitaciones);
        } catch (Exception e) {
            System.err.println("Error guardando habitación: " + e.getMessage());
            throw new RuntimeException("Error al guardar habitación", e);
        }
    }

    @Override
    public Habitacion obtenerHabitacion(int numero) {
        try {
            return listarHabitaciones().stream()
                    .filter(h -> h.getNumero() == numero)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println("Error obteniendo habitación: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Habitacion> listarHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(HABITACIONES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length == 3) {
                        habitaciones.add(new Habitacion(
                                Integer.parseInt(parts[0]), // numero
                                TipoHabitacion.valueOf(parts[1]), // tipo
                                parseBooleanSafe(parts[2]) // disponible
                        ));
                    }
                } catch (Exception e) {
                    System.err.println("Error procesando línea de habitación: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de habitaciones: " + e.getMessage());
        }
        
        return habitaciones;
    }

    @Override
    public List<Habitacion> listarHabitacionesDisponibles() {
        try {
            return listarHabitaciones().stream()
                    .filter(Habitacion::isDisponible)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error listando habitaciones disponibles: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private void guardarListaHabitaciones(List<Habitacion> habitaciones) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(HABITACIONES_FILE))) {
            for (Habitacion h : habitaciones) {
                pw.println(String.join("|",
                        String.valueOf(h.getNumero()),
                        h.getTipo().name(),
                        String.valueOf(h.isDisponible())
                ));
            }
        } catch (IOException e) {
            System.err.println("Error guardando lista de habitaciones: " + e.getMessage());
            throw new RuntimeException("Error al guardar lista de habitaciones", e);
        }
    }

    // Métodos para reservas
    @Override
    public void guardarReserva(Reserva reserva) {
        Objects.requireNonNull(reserva, "La reserva no puede ser nula");
        Objects.requireNonNull(reserva.getId(), "El ID de reserva no puede ser nulo");
        Objects.requireNonNull(reserva.getCliente(), "El cliente de la reserva no puede ser nulo");
        Objects.requireNonNull(reserva.getHabitacion(), "La habitación de la reserva no puede ser nula");
        Objects.requireNonNull(reserva.getFechaInicio(), "La fecha de inicio no puede ser nula");
        Objects.requireNonNull(reserva.getFechaFin(), "La fecha de fin no puede ser nula");
        
        try {
            List<Reserva> reservas = listarReservas();
            reservas.removeIf(r -> r.getId().equals(reserva.getId()));
            reservas.add(reserva);
            guardarListaReservas(reservas);
        } catch (Exception e) {
            System.err.println("Error guardando reserva: " + e.getMessage());
            throw new RuntimeException("Error al guardar reserva", e);
        }
    }

    @Override
    public Reserva obtenerReserva(String id) {
        try {
            return listarReservas().stream()
                    .filter(r -> r.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println("Error obteniendo reserva: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reserva> listarReservas() {
        List<Reserva> reservas = new ArrayList<>();
        List<Cliente> clientes = listarClientes();
        List<Habitacion> habitaciones = listarHabitaciones();
        
        try (BufferedReader br = new BufferedReader(new FileReader(RESERVAS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length == 7) {
                        String clienteDni = parts[1];
                        int habitacionNumero = Integer.parseInt(parts[2]);
                        
                        Cliente cliente = clientes.stream()
                                .filter(c -> c.getDni().equals(clienteDni))
                                .findFirst()
                                .orElse(null);
                        
                        Habitacion habitacion = habitaciones.stream()
                                .filter(h -> h.getNumero() == habitacionNumero)
                                .findFirst()
                                .orElse(null);
                        
                        if (cliente != null && habitacion != null) {
                            Date fechaInicio = parseDateSafe(parts[3]);
                            Date fechaFin = parseDateSafe(parts[4]);
                            
                            if (fechaInicio != null && fechaFin != null) {
                                reservas.add(new Reserva(
                                        parts[0], // id
                                        cliente,
                                        habitacion,
                                        fechaInicio,
                                        fechaFin,
                                        parseBooleanSafe(parts[5]), // checkIn
                                        parseBooleanSafe(parts[6])  // checkOut
                                ));
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error procesando línea de reserva: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de reservas: " + e.getMessage());
        }
        
        return reservas;
    }

    @Override
    public List<Reserva> buscarReservasPorApellido(String apellido) {
        try {
            return listarReservas().stream()
                    .filter(r -> r.getCliente().getApellido().equalsIgnoreCase(apellido))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error buscando reservas por apellido: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
public List<Reserva> buscarReservasPorFecha(Date fecha) {
    try {
        return listarReservas().stream()
                .filter(r -> fecha != null && 
                        (fecha.equals(r.getFechaInicio()) || 
                         fecha.equals(r.getFechaFin()) || 
                         (fecha.after(r.getFechaInicio()) && fecha.before(r.getFechaFin()))))
                .collect(Collectors.toList());
    } catch (Exception e) {
        System.err.println("Error buscando reservas por fecha: " + e.getMessage());
        return Collections.emptyList();
    }
}

    private void guardarListaReservas(List<Reserva> reservas) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RESERVAS_FILE))) {
            for (Reserva r : reservas) {
                pw.println(String.join("|",
                    r.getId(),
                    r.getCliente().getDni(),
                    String.valueOf(r.getHabitacion().getNumero()),
                    dateFormat.format(r.getFechaInicio()),
                    dateFormat.format(r.getFechaFin()),
                    String.valueOf(r.isCheckIn()),
                    String.valueOf(r.isCheckOut())
                ));
            }
        } catch (IOException e) {
            System.err.println("Error guardando lista de reservas: " + e.getMessage());
            throw new RuntimeException("Error al guardar lista de reservas", e);
        }
    }

    // Métodos para usuarios
    @Override
    public Usuario autenticar(String username, String password) {
        try {
            return listarUsuarios().stream()
                    .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println("Error autenticando usuario: " + e.getMessage());
            return null;
        }
    }
    //metodo para eliminar
    @Override
    public void eliminarReserva(String idReserva) {
        List<Reserva> reservas = listarReservas();
        reservas.removeIf(r -> r.getId().equals(idReserva));
        guardarListaReservas(reservas);
    }

    private void guardarUsuario(Usuario usuario) {
        Objects.requireNonNull(usuario, "El usuario no puede ser nulo");
        
        try {
            List<Usuario> usuarios = listarUsuarios();
            usuarios.removeIf(u -> u.getUsername().equals(usuario.getUsername()));
            usuarios.add(usuario);
            guardarListaUsuarios(usuarios);
        } catch (Exception e) {
            System.err.println("Error guardando usuario: " + e.getMessage());
            throw new RuntimeException("Error al guardar usuario", e);
        }
    }

    private List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length == 4) {
                        if ("Administrador".equals(parts[3])) {
                            usuarios.add(new Administrador(
                                    parts[0], // username
                                    parts[1], // password
                                    parts[2]  // nombre
                            ));
                        } else if ("Recepcionista".equals(parts[3])) {
                            usuarios.add(new Recepcionista(
                                    parts[0], // username
                                    parts[1], // password
                                    parts[2]  // nombre
                            ));
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error procesando línea de usuario: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de usuarios: " + e.getMessage());
        }
        
        return usuarios;
    }

    private void guardarListaUsuarios(List<Usuario> usuarios) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USUARIOS_FILE))) {
            for (Usuario u : usuarios) {
                pw.println(String.join("|",
                        u.getUsername(),
                        u.getPassword(),
                        u.getNombre(),
                        u.getRol()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error guardando lista de usuarios: " + e.getMessage());
            throw new RuntimeException("Error al guardar lista de usuarios", e);
        }
    }

    // Métodos de utilidad
    private boolean parseBooleanSafe(String value) {
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return false;
        }
    }

    private Date parseDateSafe(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.err.println("Error parseando fecha: " + dateStr);
            return null;
        }
    }

    private String formatDateSafe(Date date) {
        try {
            return date != null ? dateFormat.format(date) : dateFormat.format(new Date());
        } catch (Exception e) {
            System.err.println("Error formateando fecha: " + date);
            return dateFormat.format(new Date());
        }
    }
}