package seminario.futbol.model.factories;

import seminario.futbol.model.Jugador;
import seminario.futbol.model.Partido;
import seminario.futbol.model.Tarjeta;
import seminario.futbol.model.TipoTarjeta;

public class TarjetaFactory extends Tarjeta {

    public TarjetaFactory withJugador(Jugador jugador) {
	this.setJugador(jugador);
	return this;
    }

    public TarjetaFactory withPartido(Partido partido) {
	this.setPartido(partido);
	return this;
    }

    public TarjetaFactory withTarjeta(TipoTarjeta tarjeta) {
	this.setTipo(tarjeta);
	return this;
    }

    public Tarjeta build() {
	Tarjeta tarjeta = new Tarjeta();
	tarjeta.setJugador(this.getJugador());
	tarjeta.setPartido(this.getPartido());
	tarjeta.setTipo(this.getTipo());
	return tarjeta;
    }
}
