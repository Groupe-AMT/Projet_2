package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeEntity;
import ch.heigvd.amt.projet2.entities.BadgeRewardEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.UUID;

public interface BadgeRewardRepository extends CrudRepository<BadgeRewardEntity, Long> {
    List<BadgeRewardEntity> findByApplication(ApplicationEntity applicationEntity);
    List<BadgeRewardEntity> findByBadge(BadgeEntity badgeEntity);

    BadgeRewardEntity findByBadgeAndIDUser(BadgeEntity badge, UUID idUser);
}
