package seminario.futbol.model.factories;

import java.util.Date;

import seminario.futbol.model.Jugador;

public class JugadorFactory extends Jugador {

    public JugadorFactory withNroDocumento(String nroDocumento) {
	this.setNroDocumento(nroDocumento);
	return this;
    }

    public JugadorFactory withCategoria(Integer categoria) {
	this.setCategoria(categoria);
	return this;
    }

    public JugadorFactory withMail(String mail) {
	this.setMail(mail);
	return this;
    }

    public JugadorFactory withFechaNacimiento(Date fechaNacimiento) {
	this.setFechaNacimiento(fechaNacimiento);
	return this;
    }

    public JugadorFactory withNombre(String nombre) {
	this.setNombre(nombre);
	return this;
    }

    public JugadorFactory withTelefono(String telefono) {
	this.setTelefono(telefono);
	return this;
    }

    public Jugador build() {
	return this;
    }
}
