package seminario.futbol.repositories;

import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Partido;
import seminario.futbol.model.Resultado;

public interface PartidoRepository extends CrudRepository<Partido, Integer> {

    Iterable<Partido> findByTorneo(Integer torneo);

    Integer countByEquipoAAndResultado(Integer equipoA, Resultado resultado);

    Integer countByEquipoBAndResultado(Integer equipoA, Resultado resultado);

}
