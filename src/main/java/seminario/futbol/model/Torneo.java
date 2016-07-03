package seminario.futbol.model;

import static seminario.futbol.model.EstadoTorneo.NO_INICIADO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class Torneo {

    private static final int MAX_EQUIPOS = 10;
    private String nombre;
    private EstadoTorneo estado;
    private String descripcion;
    private Integer categoria;
    private Date inicio;
    private List<Cancha> canchas;
    private List<Arbitro> arbitro;
    private List<Equipo> equipos;
    private List<Posicion> posiciones;
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

}
