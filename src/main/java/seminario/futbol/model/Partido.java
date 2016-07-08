package seminario.futbol.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "partidos")
public class Partido {

    @Id
    private Integer idPartido;

    // private Equipo equipoA;
    // private Equipo equipoB;
    // private Arbitro arbitro;
    // private Torneo torneo;
    // private Cancha cancha;
    // private Date fecha;
    // private Integer nroFecha;

    public boolean sosElPartido(Integer idPartido2) {
	return this.idPartido.equals(idPartido2);
    }
}
