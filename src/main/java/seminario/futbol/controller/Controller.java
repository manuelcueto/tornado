package seminario.futbol.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import seminario.futbol.model.Cancha;
import seminario.futbol.model.Jugador;
import seminario.futbol.service.TornadoService;

@RestController
public class Controller {

    @Autowired
    private TornadoService tornadoService;

    @RequestMapping(value = "/canchas", method = RequestMethod.POST)
    public void crearCancha(@RequestParam String nombre, @RequestParam String telefono, @RequestParam String dueno,
	    @RequestParam String direccion) {
	this.tornadoService.crearCancha(nombre, direccion, dueno, telefono);
    }

    @RequestMapping(value = "/canchas/{idCancha}", method = RequestMethod.PUT)
    public void modificarCancha(@PathVariable("idCancha") Integer idCancha, @RequestParam String nombre,
	    @RequestParam String telefono, @RequestParam String dueno, @RequestParam String direccion) {
	this.tornadoService.modificarCancha(idCancha, nombre, direccion, dueno, telefono);
    }

    @RequestMapping(value = "/canchas/{idCancha}", method = RequestMethod.DELETE)
    public void borrarCancha(@PathVariable("idCancha") Integer idCancha) {
	this.tornadoService.borrarCancha(idCancha);
    }

    @RequestMapping(value = "/canchas", method = RequestMethod.GET)
    public Iterable<Cancha> listarCanchas() {
	return this.tornadoService.listarCanchas();
    }

    @RequestMapping("/crear-torneo")
    public void crearTorneo(@RequestParam String nombre, @RequestParam Date fechaInicio,
	    @RequestParam String descripcion, @RequestParam Integer categoria) {
	this.tornadoService.crearTorneo(nombre, fechaInicio, descripcion, categoria);
    }

    @RequestMapping("/verificar-torneo")
    public boolean verificarTorneo(@RequestParam String nombre) {
	return this.tornadoService.existeTorneo(nombre);
    }

    @RequestMapping("/listar-jugadores")
    public Iterable<Jugador> listarJugadores() {
	return this.tornadoService.listarJugadores();
    }

}
