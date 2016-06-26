package seminario.futbol.model;

public class Suspension {

    private Jugador jugador;
    private Integer nroFecha;
    private Integer duracion;

    public Suspension(Jugador jugador, Integer nroFecha, Integer duracion) {
	super();
	this.jugador = jugador;
	this.nroFecha = nroFecha;
	this.duracion = duracion;
    }

}
