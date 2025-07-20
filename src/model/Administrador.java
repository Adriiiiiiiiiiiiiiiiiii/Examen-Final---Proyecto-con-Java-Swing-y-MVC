package model;

public class Administrador extends Usuario {
    public Administrador(String username, String password, String nombre) {
        super(username, password, nombre);
    }

    @Override
    public String getRol() {
        return "Administrador";
    }
}