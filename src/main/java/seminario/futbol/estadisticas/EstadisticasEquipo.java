package seminario.futbol.estadisticas;

import java.util.List;

public class EstadisticasEquipo {

    private List<EstadisticasJugador> estadisticasJugadores;
    private String nombre;
    private Integer partidosGanados;
    private Integer partidosEmpatados;
    private Integer partidosPerdidos;
    private Integer totalGoles;
    private Integer totalTarjetasRojas;
    private Integer totalTarjetasAmarillas;

    public List<EstadisticasJugador> getEstadisticasJugadores() {
	return estadisticasJugadores;
    }

    public Integer getPartidosGanados() {
	return partidosGanados;
    }

    public Integer getPartidosEmpatados() {
	return partidosEmpatados;
    }

    public Integer getPartidosPerdidos() {
	return partidosPerdidos;
    }

    public Integer getTotalGoles() {
	return totalGoles;
    }

    public Integer getTotalTarjetasRojas() {
	return totalTarjetasRojas;
    }

    public Integer getTotalTarjetasAmarillas() {
	return totalTarjetasAmarillas;
    }

    public String getNombre() {
	return this.nombre;
    }

    public EstadisticasEquipo(String nombre, List<EstadisticasJugador> estadisticasJugadores, Integer partidosGanados,
	    Integer partidosEmpatados, Integer partidosPerdidos) {
	this.nombre = nombre;
	this.estadisticasJugadores = estadisticasJugadores;
	this.partidosGanados = partidosGanados;
	this.partidosEmpatados = partidosEmpatados;
	this.partidosPerdidos = partidosPerdidos;
	this.totalGoles = this.contarGoles();
	this.totalTarjetasAmarillas = this.contarTarjetasAmarillas();
	this.totalTarjetasRojas = this.contarTarjetasRojas();
    }

    private Integer contarTarjetasRojas() {
	return estadisticasJugadores.stream().mapToInt(jugador -> jugador.getTarjetasRojas()).sum();
    }

    private Integer contarTarjetasAmarillas() {
	return estadisticasJugadores.stream().mapToInt(jugador -> jugador.getTarjetasAmarillas()).sum();
    }

    private Integer contarGoles() {
	return estadisticasJugadores.stream().mapToInt(jugador -> jugador.getGoles()).sum();
    }

}
