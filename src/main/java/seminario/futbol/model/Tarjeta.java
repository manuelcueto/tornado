package seminario.futbol.model;

public class Tarjeta {

    private Jugador jugador;
    private Partido partido;
    private String tipoTarjeta;

    public Tarjeta(Jugador jugador, Partido partido, String tipoTarjeta) {
	super();
	this.jugador = jugador;
	this.partido = partido;
	this.tipoTarjeta = tipoTarjeta;
    }

}
