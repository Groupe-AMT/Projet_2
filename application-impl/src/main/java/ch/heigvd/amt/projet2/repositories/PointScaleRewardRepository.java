package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.dao.ResultByUserDAO;
import ch.heigvd.amt.projet2.dao.ResultsByUserDAO;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.PointScaleEntity;
import ch.heigvd.amt.projet2.entities.PointScaleRewardEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.UUID;

public interface PointScaleRewardRepository extends CrudRepository<PointScaleRewardEntity, Long> {
    List<PointScaleRewardEntity> findByApplication(ApplicationEntity applicationEntity);
    List<PointScaleRewardEntity> findByPointScaleEntity(PointScaleEntity pointScaleEntity);
    PointScaleRewardEntity findByPointScaleEntityAndIDUser(PointScaleEntity pointScale, UUID IDUser);
    List<PointScaleRewardEntity> findByIDUserAndPointScaleEntityAndApplication(UUID id, PointScaleEntity pointScaleEntity, ApplicationEntity application);

    @Query("select new ch.heigvd.amt.projet2.dao.ResultByUserDAO (IDUser, count (pointScaleEntity)) from PointScaleRewardEntity where application=?1 group by IDUser order by count (pointScaleEntity) desc") // JPQL
    List<ResultByUserDAO> countAllByPointScale (ApplicationEntity applicationEntity);

    @Query(value = "SELECT\n" +
            "    ps.iduser,\n" +
            "    COUNT(DISTINCT b.badge_id) AS totalBadge,\n" +
            "    COUNT(DISTINCT ps.point_scale_entity_id) AS totalPointScale\n" +
            "FROM\n" +
            "    point_scale_reward_entity AS ps\n" +
            "INNER JOIN badge_reward_entity AS b\n" +
            "ON\n" +
            "    ps.iduser = b.iduser\n" +
            "WHERE\n" +
            "    ps.application_id =?1\n" +
            "GROUP BY\n" +
            "    ps.iduser\n" +
            "ORDER BY\n" +
            "    totalBadge DESC,\n" +
            "    totalPointScale DESC\n" +
            "    ", nativeQuery = true)
    List<Object> overAll (ApplicationEntity applicationEntity);
}
