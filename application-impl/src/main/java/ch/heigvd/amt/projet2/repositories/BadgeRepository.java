package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeEntity;
import ch.heigvd.amt.projet2.entities.BadgeRewardEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeRepository extends CrudRepository<BadgeEntity, Long> {
    BadgeEntity findByName(String name);

    List<BadgeEntity> findByApp(ApplicationEntity app);

    BadgeEntity findByNameAndApp(String name, ApplicationEntity app);
}
