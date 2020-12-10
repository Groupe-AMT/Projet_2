package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ResultByUserDAO;
import ch.heigvd.amt.projet2.entities.ResultOverAll;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.PointScaleEntity;
import ch.heigvd.amt.projet2.entities.PointScaleRewardEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.UUID;

public interface PointScaleRewardRepository extends CrudRepository<PointScaleRewardEntity, Long> {
    List<PointScaleRewardEntity> findByApplication(ApplicationEntity applicationEntity);
    List<PointScaleRewardEntity> findByIDUserAndPointScaleEntityAndApplication(UUID id, PointScaleEntity pointScaleEntity, ApplicationEntity application);

    @Query("select new ch.heigvd.amt.projet2.entities.ResultByUserDAO (IDUser, SUM(amount)) from PointScaleRewardEntity where application=?1 group by IDUser order by count (pointScaleEntity) desc") // JPQL
    List<ResultByUserDAO> countAllByPointScale (ApplicationEntity applicationEntity);

    @Query(value = "SELECT  ps.iduser, COUNT(DISTINCT b.badge_id) AS totalBadge, SUM(ps.amount) AS totalPointScale FROM point_scale_reward_entity AS ps " +
            "INNER JOIN badge_reward_entity AS b ON ps.iduser = b.iduser WHERE ps.application_id =?1 GROUP BY ps.iduser ORDER BY totalBadge DESC, totalPointScale DESC ", nativeQuery = true)
    List<ResultOverAll> overAll (ApplicationEntity applicationEntity);
}
