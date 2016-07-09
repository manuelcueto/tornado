package seminario.futbol.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Gol {

    @ManyToOne
    @JoinColumn(name = "idPartido")
    private Partido partido;
    private Jugador jugador;

    public Partido getPartido() {
	return partido;
    }

    public void setPartido(Partido partido) {
	this.partido = partido;
    }

    public Jugador getJugador() {
	return jugador;
    }

    public void setJugador(Jugador jugador) {
	this.jugador = jugador;
    }

}
