package seminario.futbol.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "equipos")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idEquipo;

    @OneToOne
    private Jugador capitan;
    private Integer categoria;
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipo")
    private List<Jugador> jugadores;

    public Integer getIdEquipo() {
	return idEquipo;
    }

    public Jugador getCapitan() {
	return this.capitan;
    }

    public void setCapitan(Jugador capitan) {
	this.capitan = capitan;
    }

    public void setCategoria(Integer categoria) {
	this.categoria = categoria;
    }

    public Integer getCategoria() {
	return this.categoria;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public boolean hasCapitan() {
	return this.capitan != null;
    }

    public void asignarJugador(Jugador jugador) {
	if (this.categoria.equals(jugador.getCategoria())) {
	    jugadores.add(jugador);
	}
    }

    public void desasociarJugador(Jugador jugador) {
	this.jugadores.remove(jugador);
    }

    public boolean sosElEquipo(String nombreEquipo) {
	return this.nombre == nombreEquipo;
    }

    public boolean noEstaCompleto() {
	return (this.jugadores.size() < 10);
    }

    public boolean hasJugador(Jugador jugador) {
	return this.jugadores.contains(jugador);
    }

    public List<Jugador> getJugadores() {
	return this.jugadores;
    }

    public String getNombre() {
	return this.nombre;
    }
}
