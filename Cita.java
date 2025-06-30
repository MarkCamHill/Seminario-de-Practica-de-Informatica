package veterinaria;


import java.time.LocalDateTime;

public class Cita {
    private int idCita;
    private LocalDateTime fechaHora;
    private String motivo;
    private Cliente cliente;
    private Mascota mascota;

    public Cita(int idCita, LocalDateTime fechaHora, String motivo, Cliente cliente, Mascota mascota) {
        this.idCita = idCita;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.cliente = cliente;
        this.mascota = mascota;
    }

    public int getIdCita() { return idCita; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }
    public Cliente getCliente() { return cliente; }
    public Mascota getMascota() { return mascota; }

    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }
}
