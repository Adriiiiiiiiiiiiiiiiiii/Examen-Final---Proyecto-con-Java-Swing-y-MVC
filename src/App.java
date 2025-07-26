import model.FileHotelDatabase;
import model.HotelDatabase;
import view.LoginView;
import controller.LoginController;
public class App {
    public static void main(String[] args) throws Exception {
        HotelDatabase database = new FileHotelDatabase();
        
        // Inicializar la vista de login
        LoginView loginView = new LoginView();
        
        // Inicializar el controlador
        new LoginController(loginView, database);
        
        // Mostrar la vista
        loginView.setVisible(true);
    }
}
