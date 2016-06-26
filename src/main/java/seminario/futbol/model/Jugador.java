package seminario.futbol.model;

import java.util.Date;

public class Jugador {

    private String nroDocumento;
    private Integer categoria;
    private String mail;
    private EstadoJugador estado;
    private Date fechaNacimiento;
    private String nombre;
    private String telefono;

    public Jugador(String nroDocumento, Integer categoria, String mail, EstadoJugador estado, Date fechaNacimiento,
	    String nombre, String telefono) {
	super();
	this.nroDocumento = nroDocumento;
	this.categoria = categoria;
	this.mail = mail;
	this.estado = estado;
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

}