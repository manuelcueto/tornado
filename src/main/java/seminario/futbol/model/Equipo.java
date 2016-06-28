package seminario.futbol.model;

import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private Jugador capitan;
    private Integer categoria;
    private String nombre;
    private List<Jugador> jugadores;

    public Equipo(Integer categoria, String nombre) {
	super();
	this.categoria = categoria;
	this.nombre = nombre;
	this.jugadores = new ArrayList<Jugador>();
    }

    public Jugador getCapitan() {
	return this.capitan;
    }

    public void asignarJugadorAEquipo(Jugador jugador) {
	if (this.categoria.equals(jugador.getCategoria()) && jugadores.size() < 10) {
	    jugadores.add(jugador);
	}
    }

    public void desasociarJugadorDeEquipo(Jugador jugador) {
	this.jugadores.remove(jugador);
    }

    public boolean tenesJugador(String nroDocumento) {
	boolean result = false;
	int i = 0;
	while (i < this.jugadores.size() && !result) {
	    result = this.jugadores.get(i).getNroDocumento().equals(nroDocumento);
	    i++;
	}
	return result;
    }

    public void asignarCapitanDeEquipo(Jugador capitan) {
	this.capitan = capitan;
    }

    public boolean sosElEquipo(String nombreEquipo) {
	return this.nombre == nombreEquipo;
    }

}
