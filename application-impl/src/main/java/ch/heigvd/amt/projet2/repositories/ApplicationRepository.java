package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.api.model.Application;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends CrudRepository<ApplicationEntity, Long> {
        List<Application> findByXApiKey(UUID XApiKey);
}
