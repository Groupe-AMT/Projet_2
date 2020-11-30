package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.PointScaleEntity;
import org.springframework.data.repository.CrudRepository;

public interface PointScaleRepository extends CrudRepository<PointScaleEntity, Long> {
    PointScaleEntity findByNameAndApp(String pointScale, ApplicationEntity app);
}
