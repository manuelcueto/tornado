package seminario.futbol.estadisticas;

public class EstadisticaJugador {

    private Integer goles;
    private Integer tarjetasRojas;
    private Integer tarjetasAmarillas;

    public EstadisticaJugador(Integer goles, Integer tarjetasRojas, Integer tarjetasAmarillas) {
	this.goles = goles;
	this.tarjetasRojas = tarjetasRojas;
	this.tarjetasAmarillas = tarjetasAmarillas;
    }

    public Integer getGoles() {
	return goles;
    }

    public Integer getTarjetasRojas() {
	return tarjetasRojas;
    }

    public Integer getTarjetasAmarillas() {
	return tarjetasAmarillas;
    }

}
