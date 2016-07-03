package seminario.futbol.model;

public class Tarjeta {

    private Jugador jugador;
    private Partido partido;
    private TipoTarjeta tipoTarjeta;

    public Tarjeta(Jugador jugador, Partido partido, TipoTarjeta tipoTarjeta) {
	super();
	this.jugador = jugador;
	this.partido = partido;
	this.tipoTarjeta = tipoTarjeta;
    }

}
