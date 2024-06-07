import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    private static Connection connection;
    private static ArrayList<Aeropuerto> aeropuertos;

    public static void main(String[] args) {
        connection = establecerConexion();

        Scanner scanner = new Scanner(System.in);
        ArrayList<Aeropuerto> aeropuertos = new ArrayList<>();
        Vuelo vuelo = new Vuelo(0, null, null, null, 0, null, null, 0);
        int opcion = 0;

        while (opcion != 5) {
            menu();
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registerAeropuerto(scanner, aeropuertos);
                    break;
                case 2:
                    comprarAsientos(scanner);
                    break;
                case 3:
                    registrarVuelo(scanner, aeropuertos, vuelo);
                    break;
                case 4:
                    verInformacionVuelo(scanner, connection);
                    break;
                case 5:
                    System.out.println("Cerrando programa...");
                    System.out.println("\nHasta la proxima");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }

        cerrarConexion();
        scanner.close();
    }

    private static void menu() {
        System.out.println("   RESERVAS DE VUELOS  ");
        System.out.println("1.><>< Registrar Aeropuerto ><><");
        System.out.println("2.><>< Reservar asientos para un vuelo registrado ><><");
        System.out.println("3.><>< Registrar vuelo ><><");
        System.out.println("4.><>< Ver informacion pasajeros ><><");
        System.out.println("5.><>< Salir ><><");
        System.out.print("Seleccione una opcion: ");
    }

    private static Connection establecerConexion() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/aeropuerto";
            String usuario = "root";
            String contraseña = "S3n42023*";
            
            connection = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void cerrarConexion() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registerAeropuerto(Scanner scanner, ArrayList<Aeropuerto> aeropuertos) {
        System.out.print("Escriba el nombre del aeropuerto: ");
        String nombre = scanner.nextLine();
        if (nombre.equalsIgnoreCase("x")) {
            return;
        }
        System.out.print("Escriba la ubicacion del aeropuerto: ");
        String ubicacion = scanner.nextLine();
        if (ubicacion.equalsIgnoreCase("x")) {
            return;
        }

        insertarAeropuerto(nombre, ubicacion);

        Aeropuerto nuevoAeropuerto = new Aeropuerto(nombre, ubicacion);
        aeropuertos.add(nuevoAeropuerto);

        System.out.println("Aeropuerto registrado con exito.");
    }

    private static void insertarAeropuerto(String nombre, String ubicacion) {
        try {
            String query = "INSERT INTO aeropuertos (nombre, ubicacion) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, ubicacion);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registrarVuelo(Scanner scanner, ArrayList<Aeropuerto> aeropuertos, Vuelo vuelo) {
        System.out.print("Digite el numero de vuelo: ");
        int numeroVuelo = scanner.nextInt();

        boolean existeVuelo = existeNumeroVueloEnBD(numeroVuelo);
        if (!existeVuelo) {
            System.out.print("Escriba el nombre de la aerolinea: ");
            String aerolinea = scanner.nextLine();
            if (aerolinea.equalsIgnoreCase("x")) {
                return;
            }
            System.out.print("Digite la hora de salida: ");
            String horaSalida = scanner.nextLine();
            if (horaSalida.equalsIgnoreCase("x")) {
                return;
            }
            System.out.print("Digite el destino: ");
            String destino = scanner.nextLine();
            if (destino.equalsIgnoreCase("x")) {
                return;
            }
            System.out.print("Digite la capacidad maxima: ");
            String capacidadMaxima = scanner.nextLine();
            if (capacidadMaxima.equalsIgnoreCase("x")) {
                return;
            }
            System.out.print("Digite el nombre del aeropuerto de partida: ");
            String nombreAeropuertoPartida = scanner.nextLine();
            if (nombreAeropuertoPartida.equalsIgnoreCase("x")) {
                return;
            }
            boolean aeropuertoPartida = buscarAeropuertoPorNombre(nombreAeropuertoPartida);
            if (!buscarAeropuertoPorNombre(nombreAeropuertoPartida)) {
                System.out.println("No se encontro el aeropuerto especificado.");
                return;
            }
            System.out.print("Digite el nombre del aeropuerto de llegada: ");
            String nombreAeropuertoLlegada = scanner.nextLine();
            if (nombreAeropuertoLlegada.equalsIgnoreCase("x")) {
                return;
            }
            boolean aeropuertoLlegada = buscarAeropuertoPorNombre(nombreAeropuertoLlegada);
            if (!buscarAeropuertoPorNombre(nombreAeropuertoLlegada)) {
                System.out.println("No se encontro el aeropuerto especificado.");
                return;
            }
            System.out.print("Digite el precio por asiento: ");
            String precioAsiento = scanner.nextLine();
            if (precioAsiento.equalsIgnoreCase("x")) {
                return;
            }

            Vuelo nuevoVuelo = new Vuelo(numeroVuelo, aerolinea, horaSalida, destino,
                    Integer.parseInt(capacidadMaxima), aeropuertoPartida, aeropuertoLlegada,
                    Double.parseDouble(precioAsiento));
            insertarVuelo(nuevoVuelo);
            aeropuertos.add(nuevoVuelo.getAeropuertoPartida());
            aeropuertos.add(nuevoVuelo.getAeropuertoLlegada());
            System.out.println("Vuelo registrado con exito.");
        } else {
            System.out.println("Ya existe un vuelo con el numero especificado.");
        }
    }

    private static void insertarVuelo(Vuelo vuelo) {
        try {
            String query = "INSERT INTO vuelos (numeroVuelo, aerolinea, horaSalida, destino, capacidadMaxima, "
                    + "aeropuertoPartida_id, aeropuertoLlegada_id, precioAsiento, asientosDisponibles) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, vuelo.getNumeroVuelo());
                preparedStatement.setString(2, vuelo.getAerolinea());
                preparedStatement.setString(3, vuelo.getHoraSalida());
                preparedStatement.setString(4, vuelo.getDestino());
                preparedStatement.setInt(5, vuelo.getCapacidadMaxima());
                preparedStatement.setString(6, vuelo.getAeropuertoPartida().getNombre());
                preparedStatement.setString(7, vuelo.getAeropuertoLlegada().getNombre());
                preparedStatement.setDouble(8, vuelo.getPrecioAsiento());
                preparedStatement.setInt(9, vuelo.getAsientosDisponibles());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    private static void verInformacionVuelo(Scanner scanner, Connection connection) {
        System.out.print("Digite el número de vuelo: ");
        int numeroVuelo = scanner.nextInt();
    
        Vuelo vuelo = obtenerInformacionVueloDesdeBD(numeroVuelo, connection);
    
        if (vuelo != null) {
            System.out.println("Información del vuelo " + numeroVuelo + ":");
            System.out.println("Aerolínea: " + vuelo.getAerolinea());
            System.out.println("Hora de salida: " + vuelo.getHoraSalida());
            System.out.println("Destino: " + vuelo.getDestino());
            System.out.println("Capacidad máxima: " + vuelo.getCapacidadMaxima());
            System.out.println("Aeropuerto de partida: " + vuelo.getAeropuertoPartida().getNombre());
            System.out.println("Aeropuerto de llegada: " + vuelo.getAeropuertoLlegada().getNombre());
            System.out.println("Asientos disponibles: " + vuelo.getAsientosDisponibles());
    
            // Puedes mostrar más información aquí según tus necesidades
    
            vuelo.mostrarInformacionPasajeros();
        } else {
            System.out.println("No se encontró el vuelo con el número especificado.");
        }
    }
    
    private static Vuelo obtenerInformacionVueloDesdeBD(int numeroVuelo, Connection connection) {
        try {
            String query = "SELECT * FROM vuelos WHERE numeroVuelo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, numeroVuelo);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Vuelo(
                                resultSet.getInt("numeroVuelo"),
                                resultSet.getString("aerolinea"),
                                resultSet.getString("horaSalida"),
                                resultSet.getString("destino"),
                                resultSet.getInt("capacidadMaxima"),
                                // Obtener los aeropuertos desde la base de datos según su ID o nombre, dependiendo de tu modelo
                                obtenerAeropuertoDesdeBD(resultSet.getString("aeropuertoPartida_id"), connection),
                                obtenerAeropuertoDesdeBD(resultSet.getString("aeropuertoLlegada_id"), connection),
                                resultSet.getDouble("precioAsiento"), // Ajusta según tu modelo
                                resultSet.getInt("asientosDisponibles")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }    
    
    private static Aeropuerto obtenerAeropuertoDesdeBD(String nombreAeropuerto, Connection connection) {
        try {
            String query = "SELECT * FROM aeropuertos WHERE nombre = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nombreAeropuerto);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Aeropuerto(
                                resultSet.getString("nombre"),
                                resultSet.getString("ubicacion")
                                // Puedes agregar más atributos según tu modelo de datos
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static void comprarAsientos(Scanner scanner) {
        System.out.print("Digite el número de vuelo: ");
        int numeroVuelo = scanner.nextInt();
    
        Vuelo vuelo = obtenerInformacionVueloDesdeBD(numeroVuelo, connection);
    
        if (vuelo != null) {
            System.out.print("Digite la cantidad de asientos que desea comprar: ");
            int cantidadAsientos = scanner.nextInt();
    
            if (cantidadAsientos <= vuelo.getAsientosDisponibles()) {
                double totalGastado = cantidadAsientos * vuelo.getPrecioAsiento();
                vuelo.reservarAsiento(cantidadAsientos);
    
                System.out.println("Asientos comprados con éxito.");
                System.out.println("Total gastado: " + totalGastado);
            } else {
                System.out.println("La cantidad solicitada supera la cantidad de asientos disponibles.");
            }
        } else {
            System.out.println("No se encontró el vuelo con el número especificado.");
        }
    }
        

    private static boolean existeNumeroVueloEnBD(int numeroVuelo) {
        try {
            String consulta = "SELECT COUNT(*) FROM vuelos WHERE numeroVuelo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setInt(1, numeroVuelo);
    
                // Ejecutar la consulta
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    private static boolean buscarAeropuertoPorNombre(String nombreAeropuerto) {
        try {
            String query = "SELECT * FROM aeropuertos WHERE nombre = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nombreAeropuerto);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // Devuelve true si hay un resultado, false si no hay resultados
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En caso de error, asumimos que el aeropuerto no existe
        }
    }
    
    
}
