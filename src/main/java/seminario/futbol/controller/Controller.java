package seminario.futbol.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import seminario.futbol.model.EstadoJugador;
import seminario.futbol.model.Jugador;
import seminario.futbol.service.TornadoService;

@RestController
public class Controller {

    @Autowired
    private TornadoService tornadoService;

    @RequestMapping("/hello")
    public String hello(@RequestParam String name) {
	return "hello " + name;
    }

    @RequestMapping("/jugador")
    public Jugador getJugador(@RequestParam String nroDocumento) {
	Jugador jugador = new Jugador(nroDocumento, 1990, "trogolo@enfermo.com", EstadoJugador.EXPULSADO, new Date(),
		"luis trologo", "0303456");
	return jugador;
    }

    @RequestMapping("/crear-torneo")
    public void crearTorneo(@RequestParam String nombre, @RequestParam Date fechaInicio,
	    @RequestParam String descripcion, @RequestParam Integer categoria) {
	this.tornadoService.crearTorneo(nombre, fechaInicio, descripcion, categoria);
    }

    @RequestMapping("/verificar-torneo")
    public boolean verificarTorneo(@RequestParam String nombre) {
	return this.tornadoService.verificarNombreTorneo(nombre);
    }

}
