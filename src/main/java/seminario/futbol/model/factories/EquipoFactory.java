package seminario.futbol.model.factories;

import seminario.futbol.model.Equipo;

public class EquipoFactory extends Equipo {

    public EquipoFactory withCategoria(Integer categoria) {
	this.setCategoria(categoria);
	return this;
    }

    public EquipoFactory withNombre(String nombre) {
	this.setNombre(nombre);
	return this;
    }

    public Equipo build() {
	Equipo equipo = new Equipo();
	equipo.setNombre(this.getNombre());
	equipo.setCategoria(this.getCategoria());
	equipo.setAsignado(false);
	return equipo;
    }
}
