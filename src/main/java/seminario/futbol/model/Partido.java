package seminario.futbol.model;

import java.util.Date;

public class Partido {
	private Integer idPartido;
    private Equipo equipoA;
    private Equipo equipoB;
    private Arbitro arbitro;
    private Torneo torneo;
    private Cancha cancha;
    private Date fecha;
    private Integer nroFecha;
    
	public boolean sosElPartido(Integer idPartido2) {
		return this.idPartido.equals(idPartido2);
	}
}
