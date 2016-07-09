package seminario.futbol.model.factories;

import seminario.futbol.model.Cancha;

public class CanchaFactory extends Cancha {

    public CanchaFactory withDireccion(String direccion) {
	this.setDireccion(direccion);
	return this;
    }

    public CanchaFactory withDueno(String dueno) {
	this.setDueno(dueno);
	return this;
    }

    public CanchaFactory withNombre(String nombre) {
	this.setNombre(nombre);
	return this;
    }

    public CanchaFactory withTelefono(String telefono) {
	this.setTelefono(telefono);
	return this;
    }

    public Cancha build() {
	return this;
    }
}
