package seminario.futbol.estadisticas;

public class Posicion {

    private String equipo;
    private Integer partidosGanados;
    private Integer partidosEmpatados;
    private Integer partidosJugados;
    private Integer posicion;
    private Integer golesFavor;

    public Posicion(String equipo, Integer partidosGanados, Integer partidosEmpatados, Integer partidosJugados,
	    Integer posicion, Integer golesFavor) {
	this.equipo = equipo;
	this.partidosGanados = partidosGanados;
	this.partidosEmpatados = partidosEmpatados;
	this.partidosJugados = partidosJugados;
	this.posicion = posicion;
	this.golesFavor = golesFavor;
    }

}
