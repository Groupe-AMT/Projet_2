package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BadgeRepository extends CrudRepository<BadgeEntity, Long> {
    BadgeEntity findByName(String name);
    BadgeEntity findByNameAndApplication(String name, ApplicationEntity application);

    List<BadgeEntity> findByApplication(ApplicationEntity application);
}
