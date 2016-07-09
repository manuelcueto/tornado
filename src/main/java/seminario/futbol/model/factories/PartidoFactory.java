package seminario.futbol.model.factories;

import java.util.Date;

import seminario.futbol.model.Arbitro;
import seminario.futbol.model.Cancha;
import seminario.futbol.model.Equipo;
import seminario.futbol.model.Partido;
import seminario.futbol.model.Resultado;
import seminario.futbol.model.Torneo;

public class PartidoFactory extends Partido {

    public PartidoFactory withEquipoA(Equipo equipo) {
	this.setEquipoA(equipo);
	return this;
    }

    public PartidoFactory withEquipoB(Equipo equipo) {
	this.setEquipoB(equipo);
	return this;
    }

    public PartidoFactory withArbitro(Arbitro arbitro) {
	this.setArbitro(arbitro);
	return this;
    }

    public PartidoFactory withTorneo(Torneo torneo) {
	this.setTorneo(torneo);
	return this;
    }

    public PartidoFactory withCancha(Cancha cancha) {
	this.setCancha(cancha);
	return this;
    }

    public PartidoFactory withFecha(Date fecha) {
	this.setFecha(fecha);
	return this;
    }

    public PartidoFactory withNroFecha(Integer nroFecha) {
	this.setNroFecha(nroFecha);
	return this;
    }

    public Partido build() {
	this.setResultado(Resultado.NO_JUGADO);
	return this;
    }
}
