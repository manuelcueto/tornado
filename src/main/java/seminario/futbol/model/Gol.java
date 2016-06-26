package seminario.futbol.model;

public class Gol {

    private Integer cantidadGoles;
    private Partido partido;
    private Jugador jugador;

    public Gol(Integer cantidadGoles, Partido partido, Jugador jugador) {
	super();
	this.cantidadGoles = cantidadGoles;
	this.partido = partido;
	this.jugador = jugador;
    }

}
