package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReservaView extends JPanel {
    private JTable reservasTable;
    private JButton nuevaReservaButton;
    private JButton cancelarReservaButton;
    private JButton checkInButton;
    private JButton checkOutButton;
    private JButton buscarPorFechaButton;
    private JButton buscarPorApellidoButton;
    private JTextField buscarField;
    private JComboBox<String> filtroEstadoComboBox;

    public ReservaView() {
        setLayout(new BorderLayout());
        
        // Crear modelo de tabla no editable
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no editables
            }
        };
        
        reservasTable = new JTable(model);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nuevaReservaButton = new JButton("Nueva Reserva");
        cancelarReservaButton = new JButton("Cancelar Reserva");
        checkInButton = new JButton("Check-In");
        checkOutButton = new JButton("Check-Out");
        buttonPanel.add(nuevaReservaButton);
        buttonPanel.add(cancelarReservaButton);
        buttonPanel.add(checkInButton);
        buttonPanel.add(checkOutButton);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filtroEstadoComboBox = new JComboBox<>(new String[]{"Todas", "Pendientes", "Check-In", "Check-Out", "Canceladas"});
        buscarField = new JTextField(20);
        buscarPorFechaButton = new JButton("Buscar por Fecha");
        buscarPorApellidoButton = new JButton("Buscar por Apellido");
        
        searchPanel.add(new JLabel("Estado:"));
        searchPanel.add(filtroEstadoComboBox);
        searchPanel.add(new JLabel("Buscar:"));
        searchPanel.add(buscarField);
        searchPanel.add(buscarPorFechaButton);
        searchPanel.add(buscarPorApellidoButton);
        
        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
        
        // Tabla de reservas
        JScrollPane scrollPane = new JScrollPane(reservasTable);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getters para los componentes
    public JTable getReservasTable() { return reservasTable; }
    public JButton getNuevaReservaButton() { return nuevaReservaButton; }
    public JButton getCancelarReservaButton() { return cancelarReservaButton; }
    public JButton getCheckInButton() { return checkInButton; }
    public JButton getCheckOutButton() { return checkOutButton; }
    public JButton getBuscarPorFechaButton() { return buscarPorFechaButton; }
    public JButton getBuscarPorApellidoButton() { return buscarPorApellidoButton; }
    public JTextField getBuscarField() { return buscarField; }
    public JComboBox<String> getFiltroEstadoComboBox() { return filtroEstadoComboBox; }
    
    public void actualizarTablaReservas(Object[][] data, String[] columnNames) {
        DefaultTableModel model = (DefaultTableModel) reservasTable.getModel();
        model.setDataVector(data, columnNames);
        model.fireTableDataChanged();
    }
    
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    // Métodos para agregar listeners
    public void agregarNuevaReservaListener(ActionListener listener) {
        nuevaReservaButton.addActionListener(listener);
    }
    
    public void agregarCancelarReservaListener(ActionListener listener) {
        cancelarReservaButton.addActionListener(listener);
    }
    
    public void agregarCheckInListener(ActionListener listener) {
        checkInButton.addActionListener(listener);
    }
    
    public void agregarCheckOutListener(ActionListener listener) {
        checkOutButton.addActionListener(listener);
    }
    
    public void agregarBuscarPorFechaListener(ActionListener listener) {
        buscarPorFechaButton.addActionListener(listener);
    }
    
    public void agregarBuscarPorApellidoListener(ActionListener listener) {
        buscarPorApellidoButton.addActionListener(listener);
    }
    
    public void agregarFiltroEstadoListener(ActionListener listener) {
        filtroEstadoComboBox.addActionListener(listener);
    }
    
    // Métodos para obtener datos de selección
    public String getReservaSeleccionada() {
        int row = reservasTable.getSelectedRow();
        if (row >= 0) {
            return (String) reservasTable.getValueAt(row, 0); // Asumiendo que la columna 0 es el ID
        }
        return null;
    }
    
    public String getEstadoFiltroSeleccionado() {
        return (String) filtroEstadoComboBox.getSelectedItem();
    }
    
    public String getTextoBusqueda() {
        return buscarField.getText();
    }
    
    // Método para mostrar diálogo de nueva reserva
    public Object[] mostrarDialogoNuevaReserva() {
        JTextField clienteField = new JTextField();
        JTextField habitacionField = new JTextField();
        JTextField fechaInicioField = new JTextField();
        JTextField fechaFinField = new JTextField();
        
        Object[] message = {
            "DNI Cliente:", clienteField,
            "Número Habitación:", habitacionField,
            "Fecha Inicio (dd/mm/aaaa):", fechaInicioField,
            "Fecha Fin (dd/mm/aaaa):", fechaFinField
        };
        
        int option = JOptionPane.showConfirmDialog(
            this, 
            message, 
            "Nueva Reserva", 
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (option == JOptionPane.OK_OPTION) {
            return new Object[]{
                clienteField.getText(),
                habitacionField.getText(),
                fechaInicioField.getText(),
                fechaFinField.getText()
            };
        }
        return null;
    }
}