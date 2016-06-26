package seminario.futbol.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import seminario.futbol.model.EstadoJugador;
import seminario.futbol.model.Jugador;

@RestController
public class Controller {

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name") String name) {
	return "hello " + name;
    }

    @RequestMapping("/jugador")
    public Jugador getJugador(@RequestParam(value = "nroDocumento") String nroDocumento) {
	Jugador jugador = new Jugador(nroDocumento, 1990, "trogolo@enfermo.com", EstadoJugador.EXPULSADO, new Date(),
		"luis trologo", "0303456");
	return jugador;
    }

}
