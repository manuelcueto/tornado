package seminario.futbol.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seminario.futbol.model.Cancha;
import seminario.futbol.model.Equipo;
import seminario.futbol.model.Jugador;
import seminario.futbol.model.Torneo;
import seminario.futbol.repositories.CanchaRepository;
import seminario.futbol.repositories.EquipoRepository;
import seminario.futbol.repositories.JugadorRepository;
import seminario.futbol.repositories.TorneoRepository;

@Service
public class TornadoService {
    
	private List<Torneo> torneos;
    private List<Equipo> equipos;
    private List<Jugador> jugadores;

    @Autowired
    private List<Cancha> canchas;

    @Autowired
    private TorneoRepository torneoRepo;
    @Autowired
    private EquipoRepository equipoRepo;
    @Autowired
    private JugadorRepository jugadorRepo;
    @Autowired
    private CanchaRepository canchaRepo;

    public TornadoService() {
    	this.torneos = new ArrayList<Torneo>();
    	this.equipos = new ArrayList<Equipo>();
    	this.jugadores = new ArrayList<Jugador>();
    }
    public boolean verificarNombreTorneo(String nombreTorneo) {
	boolean existe = false;
	int i = 0;
	while (i < this.torneos.size() && !existe) {
	    existe = this.torneos.get(i).getNombre().equals(nombreTorneo);
	    i++;
	}
	if (!existe) {
	    existe = this.torneoRepo.findByNombre(nombreTorneo) != null;
	}
	return existe;
    }

    public void crearTorneo(String nombre, Date fechaInicio, String descripcion, Integer categoria) {
	if (!this.verificarNombreTorneo(nombre)) {
	    Torneo torneo = new Torneo(nombre, fechaInicio, descripcion, categoria);
	    this.torneos.add(this.torneoRepo.save(torneo));
	}
    }

    public void crearJugador(String nroDocumento, Integer categoria, String mail, Date fechaNacimiento, String nombre,
	    String telefono) {
	if (!this.verificarDnijugador(nroDocumento)) {
	    Jugador jug = new Jugador(nroDocumento, categoria, mail, fechaNacimiento, nombre, telefono);
	    this.jugadores.add(this.jugadorRepo.save(jug));
	}
    }

    public void crearEquipo(Integer categoria, String nombre) {
	if (!this.verificarNombreEquipo(nombre)) {
	    Equipo equipo = new Equipo(categoria, nombre);
	    this.equipos.add(this.equipoRepo.save(equipo));
	}
    }

    private boolean verificarDnijugador(String nroDocumento) {
	boolean existe = false;
	int i = 0;
	while (i < this.jugadores.size() && !existe) {
	    existe = this.jugadores.get(i).sosElJugador(nroDocumento);
	    i++;
	}
	if (!existe) {
	    existe = this.jugadorRepo.findOne(nroDocumento) != null;
	}
	return existe;
    }

    public void asignarEquipoATorneo(String nombreEquipo, String nombreTorneo) {
	Torneo torneo = this.buscarTorneo(nombreTorneo);
	Equipo equipo = this.buscarEquipo(nombreEquipo);

	if (equipo != null && torneo != null && torneo.equipoAgregable(equipo)) {
	    torneo.agregarEquipoATorneo(equipo);
	}
    }

    public void asignarCanchaATorneo(String nombreCancha, String nombreTorneo) {
	Torneo torneo = this.buscarTorneo(nombreTorneo);
	Cancha cancha = this.buscarCancha(nombreCancha);

	if (cancha != null && torneo != null) {
	    torneo.agregarCanchaATorneo(cancha);
	}
    }

    private Cancha buscarCancha(String nombreCancha) {
	int i = 0;
	while (i < this.canchas.size()) {
	    if (this.canchas.get(i).sosLaCancha(nombreCancha)) {
		return this.canchas.get(i);
	    }
	    i++;
	}
	return this.canchaRepo.findOne(nombreCancha);
    }

    public void asignarJugadorAEquipo(String nombreEquipo, String nroDocumento) {
	Jugador jugador = this.buscarJugador(nroDocumento);
	Equipo equipo = this.buscarEquipo(nombreEquipo);
	if (jugador != null && equipo != null && equipo.noEstaCompleto()
		&& this.equipoRepo.findByNroDocumento(nroDocumento) == 0) {
	    equipo.asignarJugadorAEquipo(jugador);
	    this.equipoRepo.saveJugadorEquipo(jugador.getNroDocumento(), equipo.getIdEquipo());
	}
    }

    private Jugador buscarJugador(String nroDocumento) {
	int i = 0;
	while (i < this.jugadores.size()) {
	    if (this.jugadores.get(i).sosElJugador(nroDocumento)) {
		return this.jugadores.get(i);
	    }
	    i++;
	}
	return this.jugadorRepo.findOne(nroDocumento);
    }

    public boolean verificarNombreEquipo(String nombreEquipo) {
	boolean existe = false;
	int i = 0;
	while (i < this.equipos.size() && !existe) {
	    existe = this.equipos.get(i).sosElEquipo(nombreEquipo);
	    i++;
	}
	if (!existe) {
	    existe = this.equipoRepo.findByNombre(nombreEquipo) != null;
	}
	return existe;
    }

    private Equipo buscarEquipo(String nombreEquipo) {
	int i = 0;
	while (i < this.equipos.size()) {
	    if (this.equipos.get(i).sosElEquipo(nombreEquipo)) {
		return this.equipos.get(i);
	    }
	    i++;
	}
	return this.equipoRepo.findByNombre(nombreEquipo);
    }

    private Torneo buscarTorneo(String nombreTorneo) {
	int i = 0;
	while (i < this.torneos.size()) {
	    if (this.torneos.get(i).getNombre().equals(nombreTorneo)) {
		return this.torneos.get(i);
	    }
	    i++;
	}
	return this.torneoRepo.findByNombre(nombreTorneo);
    }

    public void asignarCapitanEquipo(String nombreEquipo, String nroDocumento) {
	Jugador jugador = this.buscarJugador(nroDocumento);
	Equipo equipo = this.buscarEquipo(nombreEquipo);
	if (jugador != null && equipo != null && equipo.getCapitan() == null && equipo.tenesJugador(jugador)) {
	    equipo.asignarCapitanDeEquipo(jugador);
	    // falta persistir
	}
    }

    public void desasociarJugadorDeEquipo(String nombreEquipo, String nroDocumento) {
	Jugador jugador = this.buscarJugador(nroDocumento);
	Equipo equipo = this.buscarEquipo(nombreEquipo);
	if (jugador != null && equipo != null && equipo.tenesJugador(jugador)) {
	    equipo.desasociarJugadorDeEquipo(jugador);
	    // falta persistir
	}
    }

}
