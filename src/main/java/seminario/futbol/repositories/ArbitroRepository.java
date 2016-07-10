package seminario.futbol.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Arbitro;
import seminario.futbol.model.Estado;

public interface ArbitroRepository extends CrudRepository<Arbitro, Integer> {

    public Arbitro findByNombre(String nombre);

    @Modifying
    @Query("update Arbitro a set estado = ?2 where a.idArbitro= ?1")
    public void bajaLogica(Integer idArbitro, Estado estado);

    public Iterable<Arbitro> findAllByEstado(Estado estado);
}
