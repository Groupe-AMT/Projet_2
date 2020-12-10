package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.PointScaleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointScaleRepository extends CrudRepository<PointScaleEntity, Long> {
    PointScaleEntity findByNameAndApplication(String pointScale, ApplicationEntity application);
    List<PointScaleEntity> findByApplication(ApplicationEntity application);
}
