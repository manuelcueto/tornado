package seminario.futbol.repositories;

import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Tarjeta;
import seminario.futbol.model.TipoTarjeta;

public interface TarjetaRepository extends CrudRepository<Tarjeta, Integer> {

    Integer countByNroDocumentoAndTipo(String nroDocumento, TipoTarjeta tipo);

}
