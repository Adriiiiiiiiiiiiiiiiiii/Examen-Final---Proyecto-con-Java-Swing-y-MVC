package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginView() {
        setTitle("Login - Sistema de Hotel");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Usuario:"));
        usernameField = new JTextField();
        panel.add(usernameField);
        
        panel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        
        loginButton = new JButton("Iniciar Sesión");
        panel.add(new JLabel());
        panel.add(loginButton);
        
        add(panel);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Login", JOptionPane.ERROR_MESSAGE);
    }
}