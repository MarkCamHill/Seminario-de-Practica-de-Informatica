package veterinaria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ControladorVeterinaria {
    // Instancias DAO para acceder a la base de datos
    private ClienteDAO clienteDAO;
    private MascotaDAO mascotaDAO;
    private CitaDAO citaDAO;

    // Constructor que inicializa los DAOs
    public ControladorVeterinaria() {
        clienteDAO = new ClienteDAO();
        mascotaDAO = new MascotaDAO();
        citaDAO = new CitaDAO();
    }

    // Lógica para registrar un nuevo cliente
    public void registrarCliente(String nombre, String telefono) {
        clienteDAO.insertar(new Cliente(0, nombre, telefono));
    }

    // Lógica para registrar una mascota asociada a un cliente existente
    public void registrarMascota(String nombreDueño, String nombreMascota, String especie, String raza) {
        Cliente cliente = clienteDAO.buscarPorNombre(nombreDueño);
        if (cliente != null) {
            // Asociar mascota al cliente por su ID
            mascotaDAO.insertar(new Mascota(0, nombreMascota, especie, raza, cliente.getIdCliente()));
        }
    }

    // Programar una cita buscando cliente, mascota y verificando disponibilidad
    public void programarCita(String nombreCliente, String nombreMascota, LocalDateTime fechaHora, String motivo) {
        Cliente cliente = clienteDAO.buscarPorNombre(nombreCliente);
        if (cliente != null) {
            Mascota mascota = mascotaDAO.buscarPorNombreYCliente(nombreMascota, cliente.getIdCliente());
            if (mascota != null && citaDAO.estaDisponible(fechaHora)) {
                citaDAO.insertar(new Cita(0, fechaHora, motivo, cliente, mascota));
            }
        }
    }

    // Devuelve todos los clientes de la base de datos
    public List<Cliente> obtenerClientes() {
        return clienteDAO.listarTodos();
    }

    // Devuelve los turnos disponibles de una fecha entre 9:00 y 17:00 (cada 30 minutos)
    public List<LocalDateTime> obtenerTurnosDisponibles(LocalDate fecha) {
        List<LocalDateTime> turnos = new ArrayList<>();

        LocalTime inicio = LocalTime.of(9, 0);
        LocalTime fin = LocalTime.of(17, 0);

        // Verificar disponibilidad de turnos en intervalos de 30 minutos
        for (LocalTime hora = inicio; hora.isBefore(fin); hora = hora.plusMinutes(30)) {
            LocalDateTime turno = LocalDateTime.of(fecha, hora);
            if (citaDAO.estaDisponible(turno)) {
                turnos.add(turno);
            }
        }

        return turnos;
    }

    // Buscar cliente por su nombre
    public Cliente buscarClientePorNombre(String nombre) {
        return clienteDAO.buscarPorNombre(nombre);
    }

    // Buscar mascota por nombre e ID del cliente (asegura que sea del cliente correcto)
    public Mascota buscarMascotaPorNombreYCliente(String nombre, int idCliente) {
        return mascotaDAO.buscarPorNombreYCliente(nombre, idCliente);
    }

    // Registrar una cita con los objetos ya encontrados
    public void registrarCita(LocalDateTime fechaHora, String motivo, Cliente cliente, Mascota mascota) {
        Cita cita = new Cita(0, fechaHora, motivo, cliente, mascota);
        citaDAO.insertar(cita);
    }

    // Obtener lista de mascotas de un cliente por su ID
    public List<Mascota> obtenerMascotasPorCliente(int idCliente) {
        return mascotaDAO.obtenerPorCliente(idCliente);
    }

    // Obtener lista de mascotas de un cliente por su nombre
    public List<Mascota> obtenerMascotasPorNombreCliente(String nombreCliente) {
        return mascotaDAO.obtenerPorNombreCliente(nombreCliente);
    }
}
