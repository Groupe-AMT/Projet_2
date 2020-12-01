package ch.heigvd.amt.projet2.repositories;

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

    //@Query(value = "SELECT * FROM point_scale_reward_entity WHERE iduser = :id AND point_scale_entity_id = :pointScaleEntity.getId() AND application_id = :application.getId()", nativeQuery = true)
    List<PointScaleRewardEntity> findByIDUserAndPointScaleEntityAndApplication(UUID id, PointScaleEntity pointScaleEntity, ApplicationEntity application);
}
