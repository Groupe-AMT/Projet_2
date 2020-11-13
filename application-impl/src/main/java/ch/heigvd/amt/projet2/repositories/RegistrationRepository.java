package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.entities.RegistrationEntity;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<RegistrationEntity, Long> {
}
