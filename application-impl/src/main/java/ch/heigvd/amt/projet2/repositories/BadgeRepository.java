package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeRepository extends CrudRepository<BadgeEntity, Long> {
    List<BadgeEntity> findByApplication(ApplicationEntity applicationEntity);
}
