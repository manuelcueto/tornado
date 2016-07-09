package seminario.futbol.estadisticas;

public class EstadisticasJugador {

    private String nombre;
    private Integer goles;
    private Integer tarjetasRojas;
    private Integer tarjetasAmarillas;

    public EstadisticasJugador(Integer goles, Integer tarjetasRojas, Integer tarjetasAmarillas, String nombre) {
	this.goles = goles;
	this.tarjetasRojas = tarjetasRojas;
	this.tarjetasAmarillas = tarjetasAmarillas;
	this.nombre = nombre;
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
