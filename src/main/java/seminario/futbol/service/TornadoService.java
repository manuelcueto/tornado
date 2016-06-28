package seminario.futbol.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seminario.futbol.model.Torneo;
import seminario.futbol.repositories.TorneoRepository;

@Service
public class TornadoService {

    private List<Torneo> torneos;

    @Autowired
    private TorneoRepository torneoRepo;

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

}