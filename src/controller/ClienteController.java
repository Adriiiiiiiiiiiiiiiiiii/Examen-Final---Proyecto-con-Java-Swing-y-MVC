package controller;


import model.Cliente;
import model.HotelDatabase;
import view.ClienteView;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ClienteController {
    private ClienteView view;
    private HotelDatabase model;

    public ClienteController(ClienteView view, HotelDatabase model) {
        this.view = view;
        this.model = model;
        
        // Configurar listeners
        configurarListeners();
        
        // Cargar datos iniciales
        cargarClientes();
    }
    
    private void configurarListeners() {
        view.getAgregarButton().addActionListener(e -> agregarCliente());
        view.getEditarButton().addActionListener(e -> editarCliente());
        view.getEliminarButton().addActionListener(e -> eliminarCliente());
        view.getBuscarButton().addActionListener(e -> buscarCliente());
    }
    
    private void cargarClientes() {
        List<Cliente> clientes = model.listarClientes();
        mostrarClientesEnTabla(clientes);
    }
    
    private void mostrarClientesEnTabla(List<Cliente> clientes) {
        Object[][] data = new Object[clientes.size()][5];
        
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            data[i][0] = c.getDni();
            data[i][1] = c.getNombre();
            data[i][2] = c.getApellido();
            data[i][3] = c.getTelefono();
            data[i][4] = c.getEmail();
        }
        
        String[] columnNames = {"DNI", "Nombre", "Apellido", "Teléfono", "Email"};
        view.actualizarTablaClientes(data, columnNames);
    }
    
    private void agregarCliente() {
        // Crear diálogo para ingresar datos del cliente
        JTextField dniField = new JTextField();
        JTextField nombreField = new JTextField();
        JTextField apellidoField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField emailField = new JTextField();
        
        Object[] message = {
            "DNI:", dniField,
            "Nombre:", nombreField,
            "Apellido:", apellidoField,
            "Teléfono:", telefonoField,
            "Email:", emailField
        };
        
        int option = JOptionPane.showConfirmDialog(
            view, 
            message, 
            "Agregar Nuevo Cliente", 
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Validar campos obligatorios
                if (dniField.getText().isEmpty() || nombreField.getText().isEmpty() || apellidoField.getText().isEmpty()) {
                    view.mostrarMensaje("DNI, Nombre y Apellido son campos obligatorios");
                    return;
                }
                
                // Crear y guardar cliente
                Cliente cliente = new Cliente(
                    dniField.getText(),
                    nombreField.getText(),
                    apellidoField.getText(),
                    telefonoField.getText(),
                    emailField.getText()
                );
                
                model.guardarCliente(cliente);
                view.mostrarMensaje("Cliente agregado exitosamente");
                cargarClientes();
                
            } catch (Exception e) {
                view.mostrarMensaje("Error al agregar cliente: " + e.getMessage());
            }
        }
    }
    
    private void editarCliente() {
        int row = view.getClientesTable().getSelectedRow();
        if (row < 0) {
            view.mostrarMensaje("Seleccione un cliente para editar");
            return;
        }
        
        String dni = (String) view.getClientesTable().getValueAt(row, 0);
        Cliente cliente = model.obtenerCliente(dni);
        
        if (cliente != null) {
            // Crear diálogo con datos actuales
            JTextField nombreField = new JTextField(cliente.getNombre());
            JTextField apellidoField = new JTextField(cliente.getApellido());
            JTextField telefonoField = new JTextField(cliente.getTelefono());
            JTextField emailField = new JTextField(cliente.getEmail());
            
            Object[] message = {
                "DNI: " + cliente.getDni(),
                "Nombre:", nombreField,
                "Apellido:", apellidoField,
                "Teléfono:", telefonoField,
                "Email:", emailField
            };
            
            int option = JOptionPane.showConfirmDialog(
                view, 
                message, 
                "Editar Cliente", 
                JOptionPane.OK_CANCEL_OPTION
            );
            
            if (option == JOptionPane.OK_OPTION) {
                try {
                    // Validar campos obligatorios
                    if (nombreField.getText().isEmpty() || apellidoField.getText().isEmpty()) {
                        view.mostrarMensaje("Nombre y Apellido son campos obligatorios");
                        return;
                    }
                    
                    // Actualizar cliente
                    cliente.setNombre(nombreField.getText());
                    cliente.setApellido(apellidoField.getText());
                    cliente.setTelefono(telefonoField.getText());
                    cliente.setEmail(emailField.getText());
                    
                    model.guardarCliente(cliente);
                    view.mostrarMensaje("Cliente actualizado exitosamente");
                    cargarClientes();
                    
                } catch (Exception e) {
                    view.mostrarMensaje("Error al actualizar cliente: " + e.getMessage());
                }
            }
        }
    }
    
    private void eliminarCliente() {
        int row = view.getClientesTable().getSelectedRow();
        if (row < 0) {
            view.mostrarMensaje("Seleccione un cliente para eliminar");
            return;
        }
        
        String dni = (String) view.getClientesTable().getValueAt(row, 0);
        
        // Verificar si el cliente tiene reservas activas
        boolean tieneReservas = model.listarReservas().stream()
            .anyMatch(r -> r.getCliente().getDni().equals(dni) && !r.isCheckOut());
        
        if (tieneReservas) {
            view.mostrarMensaje("No se puede eliminar el cliente porque tiene reservas activas");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            view,
            "¿Está seguro de eliminar este cliente?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            model.eliminarCliente(dni);
            view.mostrarMensaje("Cliente eliminado exitosamente");
            cargarClientes();
        }
    }
    
    private void buscarCliente() {
        String textoBusqueda = view.getBuscarField().getText().trim().toLowerCase();
        
        if (textoBusqueda.isEmpty()) {
            cargarClientes();
            return;
        }
        
        List<Cliente> clientes = model.listarClientes().stream()
            .filter(c -> c.getDni().toLowerCase().contains(textoBusqueda) ||
                         c.getNombre().toLowerCase().contains(textoBusqueda) ||
                         c.getApellido().toLowerCase().contains(textoBusqueda) ||
                         (c.getTelefono() != null && c.getTelefono().toLowerCase().contains(textoBusqueda)) ||
                         (c.getEmail() != null && c.getEmail().toLowerCase().contains(textoBusqueda)))
            .toList();
        
        mostrarClientesEnTabla(clientes);
    }
}