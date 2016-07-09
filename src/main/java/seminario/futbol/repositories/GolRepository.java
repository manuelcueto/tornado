package seminario.futbol.repositories;

import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Gol;
import seminario.futbol.model.Jugador;

public interface GolRepository extends CrudRepository<Gol, Integer> {

    Integer countByJugador(Jugador jugador);

}
