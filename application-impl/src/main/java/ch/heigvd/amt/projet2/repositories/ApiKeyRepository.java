package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ApiKeyEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApiKeyRepository extends CrudRepository<ApiKeyEntity, Long> {
}
