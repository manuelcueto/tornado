package seminario.futbol.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seminario.futbol.model.Equipo;
import seminario.futbol.model.Torneo;
import seminario.futbol.repositories.EquipoRepository;
import seminario.futbol.repositories.TorneoRepository;

@Service
public class TornadoService {

    private List<Torneo> torneos;
    private List<Equipo> equipos;

    @Autowired
    private TorneoRepository torneoRepo;
    @Autowired
    private EquipoRepository equipoRepo;

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
	Torneo torneo = new Torneo(nombre, fechaInicio, descripcion, categoria);
	this.torneos.add(this.torneoRepo.save(torneo));
    }

    public void asignarEquipoATorneo(String nombreEquipo, String nombreTorneo) {
	Torneo torneo = this.buscarTorneo(nombreTorneo);
	Equipo equipo = this.buscarEquipo(nombreEquipo);

	if (equipo != null && torneo != null && torneo.equipoAgregable(equipo)) {
	    torneo.agregarEquipoATorneo(equipo);
	}
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