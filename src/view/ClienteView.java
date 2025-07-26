package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClienteView extends JPanel {
    private JTable clientesTable;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton buscarButton;
    private JTextField buscarField;

    public ClienteView() {
        setLayout(new BorderLayout());
        
        // Crear modelo de tabla no editable
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que todas las celdas sean no editables
            }
        };
        
        clientesTable = new JTable(model);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        agregarButton = new JButton("Agregar");
        editarButton = new JButton("Editar");
        eliminarButton = new JButton("Eliminar");
        buttonPanel.add(agregarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(eliminarButton);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buscarField = new JTextField(20);
        buscarButton = new JButton("Buscar");
        searchPanel.add(new JLabel("Buscar:"));
        searchPanel.add(buscarField);
        searchPanel.add(buscarButton);
        
        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
        
        // Tabla de clientes
        JScrollPane scrollPane = new JScrollPane(clientesTable);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getters para los componentes
    public JTable getClientesTable() { return clientesTable; }
    public JButton getAgregarButton() { return agregarButton; }
    public JButton getEditarButton() { return editarButton; }
    public JButton getEliminarButton() { return eliminarButton; }
    public JButton getBuscarButton() { return buscarButton; }
    public JTextField getBuscarField() { return buscarField; }
    
    public void actualizarTablaClientes(Object[][] data, String[] columnNames) {
        DefaultTableModel model = (DefaultTableModel) clientesTable.getModel();
        model.setDataVector(data, columnNames);
        model.fireTableDataChanged();
    }
    
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}