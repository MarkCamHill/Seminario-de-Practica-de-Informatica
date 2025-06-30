package veterinaria;

import java.sql.*;
import java.util.*;


public class ClienteDAO {

    
     //Inserta un nuevo cliente en la base de datos.
     
    public void insertar(Cliente cliente) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "INSERT INTO Cliente (nombre, telefono) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNombre());   // Asigna nombre al parámetro 1
            stmt.setString(2, cliente.getTelefono()); // Asigna teléfono al parámetro 2
            stmt.executeUpdate(); // Ejecuta la inserción
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
      //Busca un cliente por su nombre.
      //Retorna el objeto Cliente correspondiente si se encuentra.
     
    public Cliente buscarPorNombre(String nombre) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT * FROM Cliente WHERE nombre = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre); // Se establece el nombre en la consulta
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Crea y retorna un objeto Cliente con los datos obtenidos
                return new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("telefono")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el cliente
    }

    
     //Retorna una lista con todos los clientes registrados en la base de datos.
     
    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.obtenerConexion()) {
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery("SELECT * FROM Cliente");
            while (rs.next()) {
                // Por cada fila, se agrega un nuevo Cliente a la lista
                lista.add(new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("telefono")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
