package veterinaria;

import java.sql.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;



  //Implementa inserción y verificación de disponibilidad de turnos.
 
public class CitaDAO {

    /*
     * Inserta una nueva cita en la base de datos.
     */
    public void insertar(Cita cita) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "INSERT INTO Cita (fecha_hora, motivo, cliente_id, mascota_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            // Asignación de parámetros de la cita al statement
            stmt.setTimestamp(1, Timestamp.valueOf(cita.getFechaHora())); // Fecha y hora
            stmt.setString(2, cita.getMotivo());                          // Motivo de la cita
            stmt.setInt(3, cita.getCliente().getIdCliente());             // ID del cliente
            stmt.setInt(4, cita.getMascota().getIdMascota());             // ID de la mascota
            
            stmt.executeUpdate(); // Ejecuta la inserción
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Verifica si una fecha y hora determinada está disponible (sin citas agendadas).
     * Retorna true si no hay ninguna cita ya registrada en ese horario.
     */
    public boolean estaDisponible(LocalDateTime fechaHora) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT COUNT(*) FROM Cita WHERE fecha_hora = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(fechaHora)); // Establece la fecha y hora a consultar
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Si el resultado es 0, el turno está libre
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Si hay error o ya hay cita en ese turno
    }
}
