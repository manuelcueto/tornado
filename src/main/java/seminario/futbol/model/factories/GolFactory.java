package seminario.futbol.model.factories;

import seminario.futbol.model.Gol;
import seminario.futbol.model.Jugador;
import seminario.futbol.model.Partido;

public class GolFactory extends Gol {

    public GolFactory withJugador(Jugador jugador) {
	this.setJugador(jugador);
	return this;
    }

    public GolFactory withPartido(Partido partido) {
	this.setPartido(partido);
	return this;
    }

    public Gol build() {
	Gol gol = new Gol();
	gol.setJugador(this.getJugador());
	gol.setPartido(this.getPartido());
	return gol;
    }
}
