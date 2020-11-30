package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface RuleRepository extends CrudRepository<RuleEntity, Long> {
    List<RuleEntity> findAllByAction(String action);
}
