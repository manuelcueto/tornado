package seminario.futbol.repositories;

import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Jugador;
import seminario.futbol.model.Tarjeta;
import seminario.futbol.model.TipoTarjeta;

public interface TarjetaRepository extends CrudRepository<Tarjeta, Integer> {

    Integer countByJugadorAndTipo(Jugador jugador, TipoTarjeta tipo);

}
