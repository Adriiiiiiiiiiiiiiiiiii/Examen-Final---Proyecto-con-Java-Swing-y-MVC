import model.HotelDatabase;
import model.SQLiteHotelDatabase; // Implementación concreta
import view.LoginView;
import controller.LoginController;

public class App {
    public static void main(String[] args) throws Exception {
        // Inicializar la base de datos
        HotelDatabase database = new SQLiteHotelDatabase();
        
        // Inicializar la vista de login
        LoginView loginView = new LoginView();
        
        // Inicializar el controlador
        new LoginController(loginView, database);
        
        // Mostrar la vista
        loginView.setVisible(true);
    }
}
