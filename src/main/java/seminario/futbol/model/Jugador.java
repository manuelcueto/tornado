package seminario.futbol.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    private String nroDocumento;

    private Integer categoria;
    private String mail;
    @Enumerated(EnumType.STRING)
    private EstadoJugador estado;
    private Date fechaNacimiento;
    private String nombre;
    private String telefono;
    @ManyToOne
    @JoinColumn(name = "idEquipo")
    private Equipo equipo;

    public String getNroDocumento() {
	return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
	this.nroDocumento = nroDocumento;
    }

    public void setCategoria(Integer categoria) {
	this.categoria = categoria;
    }

    public void setMail(String mail) {
	this.mail = mail;
    }

    public void setEstado(EstadoJugador estado) {
	this.estado = estado;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
	this.fechaNacimiento = fechaNacimiento;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
	this.telefono = telefono;
    }

    public Integer getCategoria() {
	return categoria;
    }

    public String getMail() {
	return mail;
    }

    public EstadoJugador getEstado() {
	return estado;
    }

    public Date getFechaNacimiento() {
	return fechaNacimiento;
    }

    public String getNombre() {
	return nombre;
    }

    public String getTelefono() {
	return telefono;
    }

    public boolean sosElJugador(String nroDocumento) {
	return this.nroDocumento.equals(nroDocumento);
    }

    public void setEquipo(Equipo equipo) {
	this.equipo = equipo;
    }

    public boolean tieneEquipo() {
	return this.equipo != null;
    }

    public void desasociarEquipo() {
	this.equipo = null;

    }

}
