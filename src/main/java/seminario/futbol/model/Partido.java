package seminario.futbol.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "partidos")
public class Partido {

    @Id
    private Integer idPartido;

    private Equipo equipoA;
    private Equipo equipoB;
    private Arbitro arbitro;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partido")
    private List<Gol> goles;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partido")
    private List<Tarjeta> tarjetas;
    private Torneo torneo;
    private Cancha cancha;
    private Date fecha;
    private Integer nroFecha;
    @Enumerated(EnumType.STRING)
    private Resultado resultado;

    public Resultado getResultado() {
	return resultado;
    }

    public void setResultado(Resultado resultado) {
	this.resultado = resultado;
    }

    public void setIdPartido(Integer idPartido) {
	this.idPartido = idPartido;
    }

    public void setEquipoA(Equipo equipoA) {
	this.equipoA = equipoA;
    }

    public void setEquipoB(Equipo equipoB) {
	this.equipoB = equipoB;
    }

    public void setArbitro(Arbitro arbitro) {
	this.arbitro = arbitro;
    }

    public void setTorneo(Torneo torneo) {
	this.torneo = torneo;
    }

    public void setCancha(Cancha cancha) {
	this.cancha = cancha;
    }

    public void setFecha(Date fecha) {
	this.fecha = fecha;
    }

    public void setNroFecha(Integer nroFecha) {
	this.nroFecha = nroFecha;
    }

    public Integer getIdPartido() {
	return idPartido;
    }

    public Equipo getEquipoA() {
	return equipoA;
    }

    public Equipo getEquipoB() {
	return equipoB;
    }

    public Arbitro getArbitro() {
	return arbitro;
    }

    public Torneo getTorneo() {
	return torneo;
    }

    public Cancha getCancha() {
	return cancha;
    }

    public Date getFecha() {
	return fecha;
    }

    public Integer getNroFecha() {
	return nroFecha;
    }

    public boolean sosElPartido(Integer idPartido2) {
	return this.idPartido.equals(idPartido2);
    }
}
