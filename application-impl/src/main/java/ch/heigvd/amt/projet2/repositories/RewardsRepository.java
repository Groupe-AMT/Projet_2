package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeEntity;
import ch.heigvd.amt.projet2.entities.RewardsEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface RewardsRepository extends CrudRepository<RewardsEntity, Long> {
    List<RewardsEntity> findByApplication(ApplicationEntity applicationEntity);
    List<RewardsEntity> findByBadge(BadgeEntity badgeEntity);
}
