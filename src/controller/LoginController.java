package controller;

import model.Usuario;
import model.HotelDatabase;
import view.LoginView;
import view.MainMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView view;
    private HotelDatabase model;

    public LoginController(LoginView view, HotelDatabase model) {
        this.view = view;
        this.model = model;
        
        this.view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }
    
    private void login() {
        String username = view.getUsername();
        String password = view.getPassword();
        
        if (username.isEmpty() || password.isEmpty()) {
            view.mostrarError("Usuario y contraseña son requeridos");
            return;
        }
        
        Usuario usuario = model.autenticar(username, password);
        if (usuario != null) {
            view.dispose();
            // Abrir el menú principal
            MainMenuView mainView = new MainMenuView(usuario.getNombre(), usuario.getRol());
            MainController mainController = new MainController(mainView, model, usuario);
            mainView.setVisible(true);
        } else {
            view.mostrarError("Credenciales incorrectas");
        }
    }
}