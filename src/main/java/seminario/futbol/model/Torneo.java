package seminario.futbol.model;

import static seminario.futbol.model.EstadoTorneo.NO_INICIADO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import seminario.futbol.model.factories.PartidoFactory;
import seminario.futbol.repositories.PartidoRepository;

@Entity
@Table(name = "torneos")
public class Torneo {

    private static final int MAX_EQUIPOS = 10;

    @Autowired
    @Transient
    private PartidoRepository partidoRepo;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idTorneo;
    private String nombre;
    private EstadoTorneo estado;
    private String descripcion;
    private Integer categoria;
    private Date inicio;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "canchasTorneo", joinColumns = {
	    @JoinColumn(name = "idTorneo", nullable = false, updatable = false) }, inverseJoinColumns = {
		    @JoinColumn(name = "idCancha", nullable = false, updatable = false) })
    private List<Cancha> canchas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "arbitrosTorneo", joinColumns = {
	    @JoinColumn(name = "idTorneo", nullable = false, updatable = false) }, inverseJoinColumns = {
		    @JoinColumn(name = "idArbitro", nullable = false, updatable = false) })
    private List<Arbitro> arbitro;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "equiposTorneo", joinColumns = {
	    @JoinColumn(name = "idTorneo", nullable = false, updatable = false) }, inverseJoinColumns = {
		    @JoinColumn(name = "idEquipo", nullable = false, updatable = false) })
    private List<Equipo> equipos;

    @Transient
    private List<Suspension> suspensiones;

    public String getNombre() {
	return this.nombre;
    }

    public Integer getIdTorneo() {
	return this.idTorneo;
    }

    public String getDescripcion() {
	return descripcion;
    }

    public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
    }

    public EstadoTorneo getEstado() {
	return estado;
    }

    public void setEstado(EstadoTorneo estado) {
	this.estado = estado;
    }

    public Integer getCategoria() {
	return categoria;
    }

    public void setCategoria(Integer categoria) {
	this.categoria = categoria;
    }

    public Date getInicio() {
	return inicio;
    }

    public void setInicio(Date inicio) {
	this.inicio = inicio;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
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

    public List<Partido> publicar(List<Cancha> canchas, List<Arbitro> arbitros) {
	List<Partido> partidos = new ArrayList<Partido>();
	int cantEquipos = this.equipos.size();
	Date fechaRonda = this.inicio;
	int maxCancha = canchas.size();
	int maxArbitro = arbitros.size();
	Random random = new Random();

	// cada iteracion externa es una ronda de partidos
	for (int i = 0; i < cantEquipos - 1; i++) {
	    for (int j = 0; j < cantEquipos / 2; j++) {

		Partido partido = new PartidoFactory().withTorneo(this).withNroFecha(i + 1).withFecha(fechaRonda)
			.withCancha(canchas.get(random.nextInt(maxCancha)))
			.withArbitro(arbitros.get(random.nextInt(maxArbitro))).withEquipoA(this.equipos.get(j))
			.withEquipoB(this.equipos.get(cantEquipos - 1 - j)).withNroFecha(i * (cantEquipos) + j + 1)
			.build();
		partidos.add(partido);

	    }
	    this.reordenar(i);
	    fechaRonda = DateUtils.addDays(fechaRonda, 7);
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

    public List<Equipo> getEquipos() {
	return this.equipos;
    }
}
