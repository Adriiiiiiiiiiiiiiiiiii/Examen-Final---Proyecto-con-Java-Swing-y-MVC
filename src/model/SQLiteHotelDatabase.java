package model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHotelDatabase {
    private Connection connection;

    public SQLiteHotelDatabase(Connection connection) {
        this.connection = connection;
    }

    public Cliente obtenerCliente(String dni) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM clientes WHERE dni = ?"
            );
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
                rs.close();
                stmt.close();
                return cliente;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void guardarReserva(Reserva reserva) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT OR REPLACE INTO reservas (id, cliente_dni, habitacion_numero, fecha_inicio, fecha_fin, check_in, check_out) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            stmt.setString(1, reserva.getId());
            stmt.setString(2, reserva.getCliente().getDni());
            stmt.setInt(3, reserva.getHabitacion().getNumero());
            stmt.setString(4, sdf.format(reserva.getFechaInicio()));
            stmt.setString(5, sdf.format(reserva.getFechaFin()));
            stmt.setBoolean(6, reserva.isCheckIn());
            stmt.setBoolean(7, reserva.isCheckOut());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reserva> listarReservas() {
        List<Reserva> reservas = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reservas");

            while (rs.next()) {
                Cliente cliente = obtenerCliente(rs.getString("cliente_dni"));
                Habitacion habitacion = obtenerHabitacion(rs.getInt("habitacion_numero")); // Debes implementar esto

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Reserva reserva = new Reserva(
                    rs.getString("id"),
                    cliente,
                    habitacion,
                    sdf.parse(rs.getString("fecha_inicio")),
                    sdf.parse(rs.getString("fecha_fin"))
                );
                reserva.setCheckIn(rs.getBoolean("check_in"));
                reserva.setCheckOut(rs.getBoolean("check_out"));

                reservas.add(reserva);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservas;
    }

   
    public Habitacion obtenerHabitacion(int numero) {
        
        return new Habitacion(numero, "Simple", 50.0);
    }
}
