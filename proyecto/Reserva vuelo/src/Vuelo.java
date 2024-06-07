    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.Scanner;

    public class Vuelo implements IReservable {
        private int numeroVuelo;
        private String aerolinea;
        private String horaSalida;
        private String destino;
        private int capacidadMaxima;
        private Aeropuerto aeropuertoPartida;
        private Aeropuerto aeropuertoLlegada;
        private ArrayList<Pasajero> pasajeros;
        private int asientosDisponibles;

        public Vuelo(int numeroVuelo, String aerolinea, String horaSalida, String destino, int capacidadMaxima, Aeropuerto aeropuertoPartida, Aeropuerto aeropuertoLlegada, double d) {
            this.numeroVuelo = numeroVuelo;
            this.aerolinea = aerolinea;
            this.horaSalida = horaSalida;
            this.destino = destino;
            this.capacidadMaxima = capacidadMaxima;
            this.aeropuertoPartida = aeropuertoPartida;
            this.aeropuertoLlegada = aeropuertoLlegada;
            this.pasajeros = new ArrayList<>();
        }

        public Vuelo(int numeroVuelo, String aerolinea, String horaSalida, String destino,
                int capacidadMaxima, Aeropuerto aeropuertoPartida, Aeropuerto aeropuertoLlegada,
                double precioAsiento, int asientosDisponibles) {
                    this.numeroVuelo = numeroVuelo;
                    this.aerolinea = aerolinea;
                    this.horaSalida = horaSalida;
                    this.destino = destino;
                    this.capacidadMaxima = capacidadMaxima;
                    this.aeropuertoPartida = aeropuertoPartida;
                    this.aeropuertoLlegada = aeropuertoLlegada;
                    this.asientosDisponibles = asientosDisponibles;
    }


        public Vuelo(int numeroVuelo2, String aerolinea2, String horaSalida2, String destino2, int int1,
                boolean aeropuertoPartida2, boolean aeropuertoLlegada2, double double1) {
            
        }

        public boolean reservarAsiento(int numeroAsiento) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite su nombre: ");
            String nombre = scanner.nextLine();
            if (nombre.equalsIgnoreCase("x")) {
                return false;
            }
            System.out.print("Digite su numero de pasaporte: ");
            String numeroPasaporte = scanner.nextLine();
            if (numeroPasaporte.equalsIgnoreCase("x")) {
                return false;
            }
            System.out.print("Digite la cantidad de equipaje: ");
            String cantidadEquipaje = scanner.nextLine();
            if (cantidadEquipaje.equalsIgnoreCase("x")) {
                return false;
            }
            if (numeroAsiento <= (capacidadMaxima - pasajeros.size())) {
                double precio = 0;
                for (int i = 1; i <= numeroAsiento; i++) {
                    if (i == 1) {
                        Pasajero pasajero = new Pasajero(nombre, numeroPasaporte, Double.parseDouble(cantidadEquipaje)); // Corrección aquí
                        agregarPasajero(pasajero);
                        precio = (100 * (numeroAsiento - 1)) + pasajero.calcularPrecioReserva();
                    } else {
                        Pasajero pasajero = new Pasajero(nombre, numeroPasaporte, 0);
                        agregarPasajero(pasajero);
                    }
                    if (i == numeroAsiento) {
                        System.out.println("Precio de la reserva: " + precio);
                    }
                }
                System.out.println("Se han reservado " + numeroAsiento + " asientos");
                return true;
            }
            return false;
            }
        
        public void agregarPasajero(Pasajero pasajero) {
            pasajeros.add(pasajero);
        }
        

        public int getNumeroVuelo() {
            return numeroVuelo;
        }

        public String getAerolinea() {
            return aerolinea;
        }

        public String getHoraSalida() {
            return horaSalida;
        }

        public String getDestino() {
            return destino;
        }

        public int getCapacidadMaxima() {
            return capacidadMaxima;
        }

        public Aeropuerto getAeropuertoPartida() {
            return aeropuertoPartida;
        }

        public Aeropuerto getAeropuertoLlegada() {
            return aeropuertoLlegada;
        }

        public ArrayList<Pasajero> getPasajeros() {
            return pasajeros;
        }

        public int getPrecioAsiento() {
            return 0;
        }

        public void insertarVuelo(Connection connection) {
            try {
                String query = "INSERT INTO vuelos (numeroVuelo, aerolinea, horaSalida, destino, capacidadMaxima, aeropuertoPartida_id, aeropuertoLlegada_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, this.numeroVuelo);
                    preparedStatement.setString(2, this.aerolinea);
                    preparedStatement.setString(3, this.horaSalida);
                    preparedStatement.setString(4, this.destino);
                    preparedStatement.setInt(5, this.capacidadMaxima);
                    preparedStatement.setString(6, this.aeropuertoPartida.getNombre());
                    preparedStatement.setString(7, this.aeropuertoLlegada.getNombre());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public int getAsientosDisponibles() {
            return capacidadMaxima - pasajeros.size();
        }
        

        public void mostrarInformacionPasajeros() {
        }
    }
