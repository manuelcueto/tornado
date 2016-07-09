package seminario.futbol.estadisticas;

import java.util.List;

import seminario.futbol.model.Resultado;

public class EstadisticasPartido {

    private String equipoA;
    private String equipoB;
    private Integer golesEquipoA;
    private Integer golesEquipoB;
    private Integer tarjetasAmarillasA;
    private Integer tarjetasAmarillasB;
    private Integer tarjetasRojasA;
    private Integer tarjetasRojasB;
    private Resultado resultado;

    public String getEquipoA() {
	return equipoA;
    }

    public String getEquipoB() {
	return equipoB;
    }

    public Integer getGolesEquipoA() {
	return golesEquipoA;
    }

    public Integer getGolesEquipoB() {
	return golesEquipoB;
    }

    public Integer getTarjetasAmarillasA() {
	return tarjetasAmarillasA;
    }

    public Integer getTarjetasAmarillasB() {
	return tarjetasAmarillasB;
    }

    public Integer getTarjetasRojasA() {
	return tarjetasRojasA;
    }

    public Integer getTarjetasRojasB() {
	return tarjetasRojasB;
    }

    public Resultado getResultado() {
	return resultado;
    }

    public EstadisticasPartido(List<EstadisticasJugador> metricasEquipoA, List<EstadisticasJugador> metricasEquipoB,
	    String equipoA, String equipoB, Resultado resultado) {
	this.equipoA = equipoA;
	this.equipoB = equipoB;
	this.golesEquipoA = 0;
	this.golesEquipoB = 0;
	this.tarjetasAmarillasA = 0;
	this.tarjetasAmarillasB = 0;
	this.tarjetasRojasA = 0;
	this.tarjetasRojasB = 0;
	this.contarMetricasA(metricasEquipoA);
	this.contarMetricasB(metricasEquipoB);
	this.resultado = resultado;
    }

    private void contarMetricasA(List<EstadisticasJugador> metricasEquipo) {

	metricasEquipo.forEach(jugador -> {
	    this.golesEquipoA += jugador.getGoles();
	    this.tarjetasAmarillasA += jugador.getTarjetasAmarillas();
	    this.tarjetasRojasA += jugador.getTarjetasRojas();
	});
    }

    private void contarMetricasB(List<EstadisticasJugador> metricasEquipo) {

	metricasEquipo.forEach(jugador -> {
	    this.golesEquipoB += jugador.getGoles();
	    this.tarjetasAmarillasB += jugador.getTarjetasAmarillas();
	    this.tarjetasRojasB += jugador.getTarjetasRojas();
	});
    }

}
