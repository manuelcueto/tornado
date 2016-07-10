package seminario.futbol.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Partido;
import seminario.futbol.model.Resultado;
import seminario.futbol.model.Torneo;

public interface PartidoRepository extends CrudRepository<Partido, Integer> {

    Iterable<Partido> findByTorneo(Torneo torneo);

    Iterable<Partido> findByTorneoAndResultadoIsFalse(Integer torneo, Resultado resultado);

    Integer countByEquipoAAndResultadoAndTorneo(Integer equipoA, Resultado resultado, Integer torneo);

    Integer countByEquipoBAndResultadoAndTorneo(Integer equipoA, Resultado resultado, Integer torneo);

    Integer countByEquipoAAndResultado(Integer equipoA, Resultado resultado);

    Integer countByEquipoBAndResultado(Integer equipoA, Resultado resultado);

    @Modifying
    @Query("update Partido set resultado = ?2 where idPartido = ?1 ")
    void updatePartido(Integer idPartido, Resultado resultado);

}
