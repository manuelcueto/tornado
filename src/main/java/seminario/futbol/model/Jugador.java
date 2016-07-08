package seminario.futbol.model;

import java.util.Date;

import javax.persistence.Entity;
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
    private EstadoJugador estado;
    private Date fechaNacimiento;
    private String nombre;
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "idEquipo")
    private Equipo equipo;

    public Jugador(String nroDocumento, Integer categoria, String mail, Date fechaNacimiento, String nombre,
	    String telefono) {
	super();
	this.nroDocumento = nroDocumento;
	this.categoria = categoria;
	this.mail = mail;
	this.estado = EstadoJugador.HABILITADO;
	this.fechaNacimiento = fechaNacimiento;
	this.nombre = nombre;
	this.telefono = telefono;
    }

    public String getNroDocumento() {
	return nroDocumento;
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

}
