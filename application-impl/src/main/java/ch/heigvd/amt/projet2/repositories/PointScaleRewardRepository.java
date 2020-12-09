package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.dao.ResultByUserDAO;
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
}
