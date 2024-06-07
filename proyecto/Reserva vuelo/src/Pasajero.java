import java.util.ArrayList;

public class Pasajero implements IPagable {
    private String nombre;
    private String numeroPasaporte;
    private double cantidadEquipaje;
    private ArrayList<Vuelo> vuelosReservados;

    public Pasajero(String nombre, String numeroPasaporte, double cantidadEquipaje) {
        this.nombre = nombre;
        this.numeroPasaporte = numeroPasaporte;
        this.cantidadEquipaje = cantidadEquipaje;
        this.vuelosReservados = new ArrayList<>();
    }

    public double calcularPrecioReserva() {
        return 100 + (cantidadEquipaje * 10);
    }

    public void reservarVuelo(Vuelo vuelo) {
        vuelosReservados.add(vuelo);
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroPasaporte() {
        return numeroPasaporte;
    }

    public double getCantidadEquipaje() {
        return cantidadEquipaje;
    }

    public ArrayList<Vuelo> getVuelosReservados() {
        return vuelosReservados;
    }

}
