package veterinaria;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.sql.Date;

public class VistaVeterinaria {
    // Scanner para leer datos del usuario por consola
    private static Scanner scanner = new Scanner(System.in);

    // Instancia del controlador que conecta la vista con la lógica
    private static ControladorVeterinaria controlador = new ControladorVeterinaria();

    public static void main(String[] args) {
        // Menú principal interactivo
        while (true) {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Registrar mascota");
            System.out.println("3. Programar cita");
            System.out.println("4. Listar clientes");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            // Lógica de opciones del menú
            switch (opcion) {
                case 1 -> registrarCliente();      
                case 2 -> registrarMascota();      
                case 3 -> programarCita();         
                case 4 -> listarClientes();        
                case 0 -> {
                    System.out.println("Saliendo del sistema...");
                    return;                         
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // Permite ingresar un nuevo cliente
    private static void registrarCliente() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        controlador.registrarCliente(nombre, telefono);
    }

    // Permite registrar una nueva mascota asociada a un cliente
    private static void registrarMascota() {
        System.out.print("Nombre del dueño: ");
        String nombreDueño = scanner.nextLine();
        System.out.print("Nombre de la mascota: ");
        String nombre = scanner.nextLine();
        System.out.print("Especie: ");
        String especie = scanner.nextLine();
        System.out.print("Raza: ");
        String raza = scanner.nextLine();
       
        controlador.registrarMascota(nombreDueño, nombre, especie, raza);
    }

    // Permite programar una cita 
    public static void programarCita() {
        System.out.print("Nombre del cliente: ");
        String nombreCliente = scanner.nextLine();
        Cliente cliente = controlador.buscarClientePorNombre(nombreCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.print("Nombre de la mascota: ");
        String nombreMascota = scanner.nextLine();
        Mascota mascota = controlador.buscarMascotaPorNombreYCliente(nombreMascota, cliente.getIdCliente());
        if (mascota == null) {
            System.out.println("Mascota no encontrada.");
            return;
        }

        System.out.print("Fecha deseada (yyyy-MM-dd): ");
        String fechaStr = scanner.nextLine();
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha incorrecto.");
            return;
        }

        // Se obtienen los turnos disponibles para la fecha elegida
        List<LocalDateTime> turnosDisponibles = controlador.obtenerTurnosDisponibles(fecha);
        if (turnosDisponibles.isEmpty()) {
            System.out.println("No hay turnos disponibles en esa fecha.");
            return;
        }

        // Mostrar turnos disponibles
        System.out.println("Turnos disponibles:");
        for (int i = 0; i < turnosDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + turnosDisponibles.get(i).toLocalTime());
        }

        // Selección del turno
        System.out.print("Seleccione un turno: ");
        int opcion = Integer.parseInt(scanner.nextLine());
        if (opcion < 1 || opcion > turnosDisponibles.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        LocalDateTime turnoSeleccionado = turnosDisponibles.get(opcion - 1);

        System.out.print("Motivo de la cita: ");
        String motivo = scanner.nextLine();

        // Se registra la cita con los datos ingresados
        controlador.registrarCita(turnoSeleccionado, motivo, cliente, mascota);
        System.out.println("Cita registrada correctamente.");
    }

    // Lista todos los clientes con sus respectivas mascotas
    private static void listarClientes() {
        List<Cliente> clientes = controlador.obtenerClientes();
        for (Cliente cliente : clientes) {
            System.out.printf("Cliente: %s - Teléfono: %s\n", cliente.getNombre(), cliente.getTelefono());

            // Se obtienen y muestran las mascotas de cada cliente
            List<Mascota> mascotas = controlador.obtenerMascotasPorNombreCliente(cliente.getNombre());
            if (mascotas.isEmpty()) {
                System.out.println("  Mascotas: Ninguna registrada.");
            } else {
                for (Mascota mascota : mascotas) {
                    System.out.printf("  Mascota: %s - Especie: %s - Raza: %s\n",
                            mascota.getNombre(), mascota.getEspecie(), mascota.getRaza());
                }
            }
            System.out.println();
        }
    }
}
