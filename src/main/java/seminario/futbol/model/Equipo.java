package seminario.futbol.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "equipos")
public class Equipo {
	@Id
	private Integer idEquipo;
	
	@OneToOne
    private Jugador capitan;
    private Integer categoria;
    private String nombre;
    @Transient
    private List<Jugador> jugadores;

    public Equipo(Integer categoria, String nombre) {
	super();
	this.categoria = categoria;
	this.nombre = nombre;
	this.jugadores = new ArrayList<Jugador>();
    }

    public Integer getIdEquipo() {
	return idEquipo;
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

    public void asignarCapitanDeEquipo(Jugador capitan) {
	this.capitan = capitan;
    }

    public boolean sosElEquipo(String nombreEquipo) {
	return this.nombre == nombreEquipo;
    }

    public boolean noEstaCompleto() {
	return (this.jugadores.size() < 10);
    }

    public boolean tenesJugador(Jugador jugador) {
	return (this.jugadores.contains(jugador));
    }

}
