# Aeropuerto
**Sistema de Reservas de Vuelos**

Este proyecto consiste en un sistema de reservas de vuelos desarrollado en Java utilizando una base de datos MySQL. El sistema permite registrar aeropuertos, vuelos y realizar reservas de asientos. Aquí hay una descripción general de las características y funcionalidades del sistema:

**Características:**
- **Registro de Aeropuertos:** Permite agregar nuevos aeropuertos especificando su nombre y ubicación.
- **Registro de Vuelos:** Posibilidad de registrar nuevos vuelos con detalles como número de vuelo, aerolínea, hora de salida, destino, capacidad máxima, precio por asiento, aeropuerto de partida y aeropuerto de llegada.
- **Reserva de Asientos:** Los usuarios pueden comprar asientos para un vuelo específico, verificando la disponibilidad y gastando el monto total correspondiente.

**Estructura del Proyecto:**
- El proyecto está organizado en una clase principal `App`, que contiene el método `main` para ejecutar el sistema.
- Se utiliza una base de datos MySQL para almacenar la información de los aeropuertos, vuelos y reservas.
- Se utilizan las clases `Aeropuerto` y `Vuelo` para representar los objetos correspondientes en el sistema.
- La interacción con la base de datos se realiza a través de consultas SQL utilizando la API `java.sql`.
- Se implementa un menú interactivo en la consola para que el usuario pueda seleccionar las diferentes opciones del sistema.

**Instrucciones de Uso:**
1. Ejecutar la clase principal `App`.
2. Seleccionar una opción del menú para registrar aeropuertos, vuelos, realizar reservas o salir del sistema.
3. Seguir las indicaciones en la consola para completar las acciones deseadas, como ingresar datos de vuelos o realizar reservas de asientos.
4. Cerrar el programa cuando se haya completado la operación deseada.

Este sistema proporciona una solución simple y eficiente para gestionar reservas de vuelos, permitiendo a los usuarios administrar aeropuertos, registrar nuevos vuelos y realizar reservas de asientos de manera conveniente.
