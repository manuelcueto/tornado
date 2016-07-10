package seminario.futbol.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Equipo;
import seminario.futbol.model.Partido;
import seminario.futbol.model.Resultado;
import seminario.futbol.model.Torneo;

public interface PartidoRepository extends CrudRepository<Partido, Integer> {

    Iterable<Partido> findByTorneo(Torneo torneo);

    Iterable<Partido> findByTorneoAndResultadoIsFalse(Torneo torneo, Resultado resultado);

    Integer countByEquipoAAndResultadoAndTorneo(Equipo equipoA, Resultado resultado, Torneo torneo);

    Integer countByEquipoBAndResultadoAndTorneo(Equipo equipoB, Resultado resultado, Torneo torneo);

    Integer countByEquipoAAndResultado(Equipo equipoA, Resultado resultado);

    Integer countByEquipoBAndResultado(Equipo equipoB, Resultado resultado);

    @Modifying
    @Query("update Partido set resultado = ?2 where idPartido = ?1 ")
    void updatePartido(Integer idPartido, Resultado resultado);

}
