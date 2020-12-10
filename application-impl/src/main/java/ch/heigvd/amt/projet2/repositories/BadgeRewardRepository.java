package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ResultByUserDAO;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeEntity;
import ch.heigvd.amt.projet2.entities.BadgeRewardEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.UUID;

public interface BadgeRewardRepository extends CrudRepository<BadgeRewardEntity, Long> {
    BadgeRewardEntity findByBadgeAndIDUser(BadgeEntity badge, UUID idUser);
    List<BadgeRewardEntity> findByApplicationAndIDUser(ApplicationEntity application,UUID IDUser);

    @Query("select new ch.heigvd.amt.projet2.entities.ResultByUserDAO (IDUser, count (badge)) from BadgeRewardEntity where application=?1 group by IDUser ORDER BY count (badge) desc ") // JPQL
    List<ResultByUserDAO> countAllByBadge (ApplicationEntity applicationEntity);

}
