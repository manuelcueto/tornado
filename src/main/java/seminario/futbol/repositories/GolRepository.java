package seminario.futbol.repositories;

import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Gol;

public interface GolRepository extends CrudRepository<Gol, Integer> {

    Integer countByNroDocumento(String nroDocumento);

}
