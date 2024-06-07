import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Aeropuerto {
    private String nombre;
    private String ubicacion;
    private ArrayList<Vuelo> vuelosPartida;
    private ArrayList<Vuelo> vuelosLlegada;

    public Aeropuerto(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.vuelosPartida = new ArrayList<>();
        this.vuelosLlegada = new ArrayList<>();
    }

    public void registrarVueloPartida(Vuelo vuelo) {
        vuelosPartida.add(vuelo);
    }

    public void registrarVueloLlegada(Vuelo vuelo) {
        vuelosLlegada.add(vuelo);
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public ArrayList<Vuelo> getVuelosPartida() {
        return vuelosPartida;
    }

    public ArrayList<Vuelo> getVuelosLlegada() {
        return vuelosLlegada;
    }

    public void insertarAeropuertoEnBD(Connection connection) {
        try {
            String query = "INSERT INTO aeropuerto (nombre, ubicacion) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, this.nombre);
                preparedStatement.setString(2, this.ubicacion);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
