package seminario.futbol.model.factories;

import java.util.Date;

import seminario.futbol.model.Torneo;

public class TorneoFactory extends Torneo {

    public TorneoFactory withCategoria(Integer categoria) {
	this.setCategoria(categoria);
	return this;
    }

    public TorneoFactory withNombre(String nombre) {
	this.setNombre(nombre);
	return this;
    }

    public TorneoFactory withDescripcion(String descripcion) {
	this.setDescripcion(descripcion);
	return this;
    }

    public TorneoFactory withInicio(Date inicio) {
	this.setInicio(inicio);
	return this;
    }

    public Torneo build() {
	Torneo torneo = new Torneo();
	torneo.setNombre(this.getNombre());
	torneo.setCategoria(this.getCategoria());
	torneo.setInicio(this.getInicio());
	torneo.setDescripcion("");
	if (this.getDescripcion() != null) {
	    torneo.setDescripcion(this.getDescripcion());
	}
	return torneo;
    }
}
