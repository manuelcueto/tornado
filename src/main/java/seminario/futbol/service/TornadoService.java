package seminario.futbol.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seminario.futbol.model.Equipo;
import seminario.futbol.model.EstadoJugador;
import seminario.futbol.model.Jugador;
import seminario.futbol.model.Torneo;
import seminario.futbol.repositories.EquipoRepository;
import seminario.futbol.repositories.JugadorRepository;
import seminario.futbol.repositories.TorneoRepository;

@Service
public class TornadoService {
	@Autowired
    private List<Torneo> torneos;
	@Autowired
    private List<Equipo> equipos;
	@Autowired
    private List<Jugador> jugadores;

    @Autowired
    private TorneoRepository torneoRepo;
    @Autowired
    private EquipoRepository equipoRepo;
    @Autowired
	private JugadorRepository jugadorRepo;

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
    	if(!this.verificarNombreTorneo(nombre)) {
    		Torneo torneo = new Torneo(nombre, fechaInicio, descripcion, categoria);
    		this.torneos.add(this.torneoRepo.save(torneo));
    	}
    }
    
    public void crearJugador(String nroDocumento, Integer categoria, String mail, Date fechaNacimiento,
    	    String nombre, String telefono) {
    	if(!this.verificarDnijugador(nroDocumento)) {
    		Jugador jug = new Jugador(nroDocumento, categoria, mail, fechaNacimiento, nombre, telefono);
    		this.jugadores.add(this.jugadorRepo.save(jug));
    	}
    }

    public void crearEquipo( Integer categoria, String nombre) {
    	if(!this.verificarNombreEquipo(nombre)) {
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

	public void asignarJugadorAEquipo(String nombreEquipo, String nroDocumento) {
		Jugador jugador= this.buscarJugador(nroDocumento);	
		Equipo equipo = this.buscarEquipo(nombreEquipo);
		if (jugador != null && equipo != null && equipo.noEstaCompleto() && this.equipoRepo.findByNroDocumento(nroDocumento)==0) {
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
}