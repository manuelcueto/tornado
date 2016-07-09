package seminario.futbol.model;

import static seminario.futbol.model.EstadoTorneo.NO_INICIADO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import seminario.futbol.model.factories.PartidoFactory;

@Entity
@Table(name = "torneos")
public class Torneo {

    private static final int MAX_EQUIPOS = 10;

    @Id
    private Integer idTorneo;
    private String nombre;
    private EstadoTorneo estado;
    private String descripcion;
    private Integer categoria;
    private Date inicio;
    @Transient
    private List<Cancha> canchas;
    @Transient
    private List<Arbitro> arbitro;
    @Transient
    private List<Equipo> equipos;
    @Transient
    private List<Posicion> posiciones;
    @Transient
    private List<Suspension> suspensiones;

    public Torneo(String nombre, Date fechaInicio, String descripcion, Integer categoria) {
	this.nombre = nombre;
	this.inicio = fechaInicio;
	this.descripcion = descripcion;
	this.categoria = categoria;
	this.estado = NO_INICIADO;
	this.equipos = new ArrayList<Equipo>();
    }

    public String getNombre() {
	return this.nombre;
    }

    public boolean equipoAgregable(Equipo equipo) {
	return this.estado == NO_INICIADO && this.equipos.size() < MAX_EQUIPOS && !this.equipos.contains(equipo);
    }

    public void agregarEquipoATorneo(Equipo equipo) {
	this.equipos.add(equipo);
    }

    public void agregarCanchaATorneo(Cancha cancha) {
	this.canchas.add(cancha);
    }

    public boolean publicable() {
	return EstadoTorneo.NO_INICIADO == this.estado && this.equipos.size() > 1 && this.esPar(this.equipos.size());
    }

    public List<Partido> publicar() {
	List<Partido> partidos = new ArrayList<Partido>();
	int cantEquipos = this.equipos.size();
	Date fechaRonda = this.inicio;

	// cada iteracion externa es una ronda de partidos
	for (int i = 0; i < cantEquipos - 1; i++) {
	    for (int j = 0; j < cantEquipos / 2; j++) {
		partidos.add(new PartidoFactory().withTorneo(this).withNroFecha(i + 1).withFecha(fechaRonda)
			.withCancha(new Cancha()).withArbitro(new Arbitro()).withEquipoA(this.equipos.get(j))
			.withEquipoB(this.equipos.get(cantEquipos - 1 - j)).build());

	    }
	    this.reordenar(i);
	    // agregar 7 dias
	}
	return partidos;
    }

    public void iniciarTorneo() {
	this.estado = EstadoTorneo.INICIADO;
    }

    private boolean esPar(int numero) {
	return numero % 2 == 0;
    }

    private void reordenar(int ronda) {
	this.equipos.add(ronda + 1, this.equipos.get(this.equipos.size() - 1));
    }
}
