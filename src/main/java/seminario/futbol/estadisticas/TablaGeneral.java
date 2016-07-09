package seminario.futbol.estadisticas;

import java.util.List;

public class TablaGeneral {

    private String nombreTorneo;
    private List<EstadisticasEquipo> posiciones;

    public TablaGeneral(String torneo, List<EstadisticasEquipo> metricasEquipos) {
	this.nombreTorneo = torneo;
	this.calcularPosiciones(metricasEquipos);
    }

    private void calcularPosiciones(List<EstadisticasEquipo> equipos) {

	equipos.sort((elem1, elem2) -> {
	    if (elem1.getPartidosGanados().equals(elem2.getPartidosGanados())) {
		if (elem1.getPartidosEmpatados().equals(elem2.getPartidosEmpatados())) {
		    if (elem1.getTotalGoles().equals(elem2.getTotalGoles())) {
			return 1;
		    }
		    return elem1.getTotalGoles() > elem2.getTotalGoles() ? 1 : -1;
		}
		return elem1.getPartidosEmpatados() > elem2.getPartidosEmpatados() ? 1 : -1;
	    }
	    return elem1.getPartidosGanados() > elem2.getPartidosGanados() ? 1 : -1;
	});
	this.posiciones = equipos;
    }

}
