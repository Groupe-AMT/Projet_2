package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.BadgeEntity;
import ch.heigvd.amt.projet2.entities.BadgeRewardEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface BadgeRewardRepository extends CrudRepository<BadgeRewardEntity, Long> {
    BadgeRewardEntity findByBadgeAndIDUser(BadgeEntity badge, UUID idUser);
}
