package controller;

import model.Usuario;
import model.HotelDatabase;
import view.MainMenuView;
import view.ClienteView;
import view.HabitacionView;
import view.ReservaView;

public class MainController {
    private MainMenuView view;
    private HotelDatabase model;
    private Usuario usuario;
    
    private ClienteController clienteController;
    private HabitacionController habitacionController;
    private ReservaController reservaController;

    public MainController(MainMenuView view, HotelDatabase model, Usuario usuario) {
        this.view = view;
        this.model = model;
        this.usuario = usuario;
        
        initControllers();
    }
    
    private void initControllers() {
        // Inicializar vistas para cada pestaña
        ClienteView clienteView = new ClienteView();
        HabitacionView habitacionView = new HabitacionView();
        ReservaView reservaView = new ReservaView();
        
        // Agregar vistas a los paneles
        view.getClientePanel().add(clienteView);
        view.getHabitacionPanel().add(habitacionView);
        view.getReservaPanel().add(reservaView);
        
        // Inicializar controladores
        clienteController = new ClienteController(clienteView, model);
        habitacionController = new HabitacionController(habitacionView, model);
        reservaController = new ReservaController(reservaView, model, usuario);
    }
}