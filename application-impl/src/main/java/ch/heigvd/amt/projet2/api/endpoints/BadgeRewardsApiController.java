package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.BadgeRewardsApi;
import ch.heigvd.amt.projet2.api.model.BadgeRewards;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeRewardEntity;
import ch.heigvd.amt.projet2.repositories.BadgeRewardRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class BadgeRewardsApiController implements BadgeRewardsApi {

    @Autowired
    BadgeRewardRepository badgeRewardRepository;

    @Autowired
    private HttpServletRequest context;

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BadgeRewards>> getBadgeRewards(@ApiParam(value = ""  )  @PathVariable("id") UUID userID) {
        try {
            List<BadgeRewards> badgeRewardsList = new ArrayList<>();
            for (BadgeRewardEntity badgeRewardEntity : badgeRewardRepository.findByApplicationAndIDUser((ApplicationEntity) context.getAttribute("application"), userID)) {
                badgeRewardsList.add(toBadgeReward(badgeRewardEntity));
            }
            return ResponseEntity.ok(badgeRewardsList);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    private BadgeRewards toBadgeReward(BadgeRewardEntity entity) {
        BadgeRewards badgeRewards = new BadgeRewards();
        badgeRewards.setName(entity.getBadge().getName());
        badgeRewards.setImage(entity.getBadge().getImage());
        badgeRewards.setTimestamp(OffsetDateTime.ofInstant(Instant.ofEpochMilli(entity.getTimestamp().getTime()), ZoneId.of("UTC")));
        return badgeRewards;
    }
}
