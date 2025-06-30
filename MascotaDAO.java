package veterinaria;

import java.sql.*;
import java.util.*;

/*
 * Clase DAO para manejar operaciones de base de datos relacionadas con mascotas.
 */
public class MascotaDAO {

    /*
     * Inserta una nueva mascota en la base de datos.
     */
    public void insertar(Mascota mascota) {
        String sql = "INSERT INTO mascota (nombre, especie, raza, id_cliente) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asigna los valores de la mascota al PreparedStatement
            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getEspecie());
            stmt.setString(3, mascota.getRaza());
            stmt.setInt(4, mascota.getIdCliente());

            stmt.executeUpdate(); // Ejecuta la inserción
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime error si algo falla
        }
    }

    /*
     * Busca una mascota por nombre y por ID del cliente.
     * Retorna la mascota si existe, null si no se encuentra.
     */
    public Mascota buscarPorNombreYCliente(String nombre, int idCliente) {
        String sql = "SELECT * FROM mascota WHERE nombre = ? AND id_cliente = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setInt(2, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Mascota(
                    rs.getInt("id_mascota"),
                    rs.getString("nombre"),
                    rs.getString("especie"),
                    rs.getString("raza"),
                    rs.getInt("id_cliente")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Obtiene todas las mascotas asociadas a un cliente cuyo nombre sea el proporcionado.
     */
    public List<Mascota> obtenerPorNombreCliente(String nombreCliente) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT m.id_mascota, m.nombre, m.especie, m.raza " +
                     "FROM mascota m " +
                     "JOIN cliente c ON m.id_cliente = c.id_cliente " +
                     "WHERE c.nombre = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Mascota mascota = new Mascota(
                    rs.getInt("id_mascota"),
                    rs.getString("nombre"),
                    rs.getString("especie"),
                    rs.getString("raza"),
                    0 // ID del cliente omitido porque ya lo conocemos por nombre
                );
                mascotas.add(mascota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascotas;
    }

    /*
     * Devuelve todas las mascotas asociadas al ID de un cliente específico.
     */
    public List<Mascota> obtenerPorCliente(int idCliente) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascota WHERE id_cliente = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Mascota mascota = new Mascota(
                    rs.getInt("id_mascota"),
                    rs.getString("nombre"),
                    rs.getString("especie"),
                    rs.getString("raza"),
                    idCliente // Se usa el ID directamente
                );
                mascotas.add(mascota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mascotas;
    }
}
