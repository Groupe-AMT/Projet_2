package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BadgeRepository extends CrudRepository<BadgeEntity, Long> {
    BadgeEntity findByName(String name);
    BadgeEntity findByNameAndApp(String name, ApplicationEntity app);

    List<BadgeEntity> findByApp(ApplicationEntity app);
}
