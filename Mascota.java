package veterinaria;

public class Mascota {
    private int idMascota;
    private String nombre;
    private String especie;
    private String raza;
    private int idCliente;

    public Mascota(int idMascota, String nombre, String especie, String raza, int idCliente) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.idCliente = idCliente;
    }

    public Mascota(String nombre, String especie, String raza, int idCliente) {
        this(0, nombre, especie, raza, idCliente);
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaza() {
        return raza;
    }

    public int getIdCliente() {
        return idCliente;
    }
}
