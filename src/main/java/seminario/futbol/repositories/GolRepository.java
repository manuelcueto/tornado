package seminario.futbol.repositories;

import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Gol;
import seminario.futbol.model.Jugador;
import seminario.futbol.model.Partido;

public interface GolRepository extends CrudRepository<Gol, Integer> {

    Integer countByJugador(Jugador jugador);

    Integer countByJugadorAndPartido(Jugador jugador, Partido partido);

}
