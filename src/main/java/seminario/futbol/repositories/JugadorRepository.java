package seminario.futbol.repositories;

import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Jugador;

public interface JugadorRepository extends CrudRepository<Jugador, String> {

}
