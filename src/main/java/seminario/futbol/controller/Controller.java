package seminario.futbol.controller;

import java.sql.SQLException;
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

    @RequestMapping(value = "/equipos", method = RequestMethod.PUT)
    public void crearEquipo(@RequestParam String nombre, @RequestParam Integer categoria) {
	this.tornadoService.crearEquipo(nombre, categoria);
    }

    @RequestMapping(value = "/equipos/{idEquipo}", method = RequestMethod.DELETE)
    public void borrarEquipo(@PathVariable("idEquipo") Integer idEquipo) {
	this.tornadoService.borrarEquipo(idEquipo);
    }

    @RequestMapping(value = "/equipos/{nombreEquipo}/capitan", method = RequestMethod.PUT)
    public void asignarCapitanAEquipo(@PathVariable("nombreEquipo") String nombreEquipo,
	    @RequestParam String nroDocumento) {
	try {
	    this.tornadoService.asociarCapitanAEquipo(nombreEquipo, nroDocumento);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/equipos/{idEquipo}/jugadores", method = RequestMethod.DELETE)
    public void desasociarJugadorDeEquipo(@PathVariable("idEquipo") Integer idEquipo,
	    @RequestParam("nroDocumento") String nroDocumento) {
	try {
	    this.tornadoService.desasociarJugadorDeEquipo(idEquipo, nroDocumento);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/equipos/{idEquipo}/jugadores", method = RequestMethod.PUT)
    public void asociarJugadorAEquipo(@PathVariable("idEquipo") Integer idEquipo,
	    @RequestParam("nroDocumento") String nroDocumento) {
	try {
	    this.tornadoService.asociarJugadorAEquipo(idEquipo, nroDocumento);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
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
