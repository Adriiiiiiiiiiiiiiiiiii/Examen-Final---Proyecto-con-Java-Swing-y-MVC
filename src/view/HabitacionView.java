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
        
        // Crear modelo de tabla no editable
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        habitacionesTable = new JTable(model);
        
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
        DefaultTableModel model = (DefaultTableModel) habitacionesTable.getModel();
        model.setDataVector(data, columnNames);
        model.fireTableDataChanged();
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
            return (int) habitacionesTable.getValueAt(row, 0);
        }
        return -1;
    }
    
    // Diálogo para agregar/editar habitación
    public Object[] mostrarDialogoHabitacion(int numeroActual, String tipoActual) {
        JTextField numeroField = new JTextField(5);
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Individual", "Doble", "Suite"});
        
        if (numeroActual > 0) {
            numeroField.setText(String.valueOf(numeroActual));
            numeroField.setEditable(false);
            tipoCombo.setSelectedItem(tipoActual);
        }
        
        Object[] message = {
            "Número:", numeroField,
            "Tipo:", tipoCombo
        };
        
        int option = JOptionPane.showConfirmDialog(
            this, 
            message, 
            numeroActual > 0 ? "Editar Habitación" : "Agregar Habitación", 
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (option == JOptionPane.OK_OPTION) {
            return new Object[]{
                numeroField.getText(),
                tipoCombo.getSelectedItem()
            };
        }
        return null;
    }
}