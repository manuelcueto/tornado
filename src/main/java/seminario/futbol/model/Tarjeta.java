package seminario.futbol.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tarjetas")
public class Tarjeta {

    @Id
    private Integer idTarjeta;

    @OneToOne
    private Jugador jugador;
    @ManyToOne
    @JoinColumn(name = "idPartido")
    private Partido partido;
    @Enumerated(EnumType.STRING)
    private TipoTarjeta tipo;

    public Jugador getJugador() {
	return jugador;
    }

    public void setJugador(Jugador jugador) {
	this.jugador = jugador;
    }

    public Partido getPartido() {
	return partido;
    }

    public void setPartido(Partido partido) {
	this.partido = partido;
    }

    public TipoTarjeta getTipo() {
	return tipo;
    }

    public void setTipo(TipoTarjeta tipoTarjeta) {
	this.tipo = tipoTarjeta;
    }

}
