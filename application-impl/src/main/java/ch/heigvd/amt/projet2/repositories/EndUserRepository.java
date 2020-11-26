package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.EndUserEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface EndUserRepository extends CrudRepository<EndUserEntity, Long> {
    List<EndUserEntity> findByIDUser (String IDUser);
    List<EndUserEntity> findByIDUserAndAppName (String IDUser, String appName);
    List<EndUserEntity> findByUserName (String userName);
}
