package seminario.futbol.model;

import java.util.Date;
import java.util.List;

public class Torneo {

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
	this.estado = EstadoTorneo.NO_INICIADO;
    }

    public String getNombre() {
	return this.nombre;
    }

}
