package seminario.futbol.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "goles")
public class Gol {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idGol;

    @ManyToOne
    @JoinColumn(name = "idPartido")
    private Partido partido;
    @OneToOne
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
