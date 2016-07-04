package seminario.futbol.model;

import static seminario.futbol.model.EstadoTorneo.NO_INICIADO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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

}
