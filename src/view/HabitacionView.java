package view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class HabitacionView extends JPanel {
    private JTable habitacionesTable;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton cambiarEstadoButton;
    private JComboBox<String> filtroTipoComboBox;
    private JComboBox<String> filtroDisponibilidadComboBox;
    private JButton filtrarButton;
    private JButton limpiarFiltrosButton;

    public HabitacionView() {
        setLayout(new BorderLayout());
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        agregarButton = new JButton("Agregar");
        editarButton = new JButton("Editar");
        cambiarEstadoButton = new JButton("Cambiar Estado");
        buttonPanel.add(agregarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(cambiarEstadoButton);
        
        // Panel de filtros
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filtroTipoComboBox = new JComboBox<>(new String[]{"Todos", "Individual", "Doble", "Suite"});
        filtroDisponibilidadComboBox = new JComboBox<>(new String[]{"Todas", "Disponibles", "Ocupadas"});
        filtrarButton = new JButton("Filtrar");
        limpiarFiltrosButton = new JButton("Limpiar");
        
        filterPanel.add(new JLabel("Tipo:"));
        filterPanel.add(filtroTipoComboBox);
        filterPanel.add(new JLabel("Disponibilidad:"));
        filterPanel.add(filtroDisponibilidadComboBox);
        filterPanel.add(filtrarButton);
        filterPanel.add(limpiarFiltrosButton);
        
        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(filterPanel, BorderLayout.EAST);
        
        // Tabla de habitaciones
        habitacionesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(habitacionesTable);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getters para los componentes
    public JTable getHabitacionesTable() { return habitacionesTable; }
    public JButton getAgregarButton() { return agregarButton; }
    public JButton getEditarButton() { return editarButton; }
    public JButton getCambiarEstadoButton() { return cambiarEstadoButton; }
    public JComboBox<String> getFiltroTipoComboBox() { return filtroTipoComboBox; }
    public JComboBox<String> getFiltroDisponibilidadComboBox() { return filtroDisponibilidadComboBox; }
    public JButton getFilrarButton() { return filtrarButton; }
    public JButton getLimpiarFiltrosButton() { return limpiarFiltrosButton; }
    
    public void actualizarTablaHabitaciones(Object[][] data, String[] columnNames) {
        habitacionesTable.setModel(new DefaultTableModel(data, columnNames));
    }
    
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    // Métodos para agregar listeners
    public void agregarAgregarListener(ActionListener listener) {
        agregarButton.addActionListener(listener);
    }
    
    public void agregarEditarListener(ActionListener listener) {
        editarButton.addActionListener(listener);
    }
    
    public void agregarCambiarEstadoListener(ActionListener listener) {
        cambiarEstadoButton.addActionListener(listener);
    }
    
    public void agregarFiltrarListener(ActionListener listener) {
        filtrarButton.addActionListener(listener);
    }
    
    public void agregarLimpiarFiltrosListener(ActionListener listener) {
        limpiarFiltrosButton.addActionListener(listener);
    }
    
    // Métodos para obtener filtros seleccionados
    public String getTipoFiltroSeleccionado() {
        return (String) filtroTipoComboBox.getSelectedItem();
    }
    
    public String getDisponibilidadFiltroSeleccionado() {
        return (String) filtroDisponibilidadComboBox.getSelectedItem();
    }
    
    // Método para obtener habitación seleccionada
    public int getHabitacionSeleccionada() {
        int row = habitacionesTable.getSelectedRow();
        if (row >= 0) {
            return (int) habitacionesTable.getValueAt(row, 0); // Asumiendo que la columna 0 es el número
        }
        return -1;
    }
}