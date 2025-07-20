package view;

import javax.swing.*;
//import java.awt.*;

public class MainMenuView extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel clientePanel;
    private JPanel habitacionPanel;
    private JPanel reservaPanel;

    public MainMenuView(String usuarioNombre, String rol) {
        setTitle("Sistema de Hotel - " + usuarioNombre + " (" + rol + ")");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        
        // Crear pestañas
        clientePanel = new JPanel();
        habitacionPanel = new JPanel();
        reservaPanel = new JPanel();
        
        tabbedPane.addTab("Clientes", clientePanel);
        tabbedPane.addTab("Habitaciones", habitacionPanel);
        tabbedPane.addTab("Reservas", reservaPanel);
        
        add(tabbedPane);
    }

    public JPanel getClientePanel() {
        return clientePanel;
    }

    public JPanel getHabitacionPanel() {
        return habitacionPanel;
    }

    public JPanel getReservaPanel() {
        return reservaPanel;
    }
}