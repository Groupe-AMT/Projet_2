package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.EndUserEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface EndUserRepository extends CrudRepository<EndUserEntity, Long> {
    EndUserEntity findByIDUserAndAppName (UUID IDUser, String appName);
}
