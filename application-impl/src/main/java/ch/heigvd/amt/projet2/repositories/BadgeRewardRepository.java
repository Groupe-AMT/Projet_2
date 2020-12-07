package ch.heigvd.amt.projet2.repositories;

import ch.heigvd.amt.projet2.dao.ResultNumberBadgesByUserDAO;
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

    @Query("select new ch.heigvd.amt.projet2.dao.ResultNumberBadgesByUserDAO (IDUser, count (badge)) from BadgeRewardEntity group by IDUser") // JPQL
    //@Query(value = "select id from badge_reward_entity", nativeQuery = true)// mysql
    List<ResultNumberBadgesByUserDAO> countAllByBadge ();

    //@Query(value="select badgerewar0_.id as id1_3_, badgerewar0_.iduser as iduser2_3_, badgerewar0_.application_id as applicat4_3_, badgerewar0_.badge_id as badge_id5_3_, badgerewar0_.timestamp as timestam3_3_ from badge_reward_entity badgerewar0_ where badgerewar0_.application_id=? order by badgerewar0_.badge_id asc")
    List<BadgeRewardEntity> findByApplicationOrderByBadge(ApplicationEntity application);
}
