package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.PointScaleEntity;
import ch.heigvd.amt.projet2.entities.PointScaleRewardEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointScaleRewardRepository extends CrudRepository<PointScaleRewardEntity, Long> {
    List<PointScaleRewardRepository> findByApplication(ApplicationEntity applicationEntity);
    List<PointScaleRewardRepository> findByPointScaleEntity(PointScaleEntity pointScaleEntity);
}
