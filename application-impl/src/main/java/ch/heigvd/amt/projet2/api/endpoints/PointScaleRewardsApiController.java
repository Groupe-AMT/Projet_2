package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.PointScaleRewardsApi;
import ch.heigvd.amt.projet2.api.model.PointScaleStats;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.PointScaleEntity;
import ch.heigvd.amt.projet2.entities.PointScaleRewardEntity;
import ch.heigvd.amt.projet2.repositories.PointScaleRepository;
import ch.heigvd.amt.projet2.repositories.PointScaleRewardRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class PointScaleRewardsApiController implements PointScaleRewardsApi {

    @Autowired
    PointScaleRewardRepository pointScaleRewardRepository;
    @Autowired
    PointScaleRepository pointScaleRepository;

    @Autowired
    private HttpServletRequest context;

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PointScaleStats>> getPointScaleRewards(@ApiParam(value = ""  )  @PathVariable("id") UUID userID) {
        try {
            List<PointScaleStats> pointScaleRewards = new ArrayList<>();
            for (PointScaleEntity pointScaleEntity : pointScaleRepository.findByApplication((ApplicationEntity) context.getAttribute("application"))) {
                pointScaleRewards.add(toPointScaleStats(pointScaleRewardRepository.findByIDUserAndPointScaleEntityAndApplication(userID, pointScaleEntity,(ApplicationEntity) context.getAttribute("application"))));
            }
            return ResponseEntity.ok(pointScaleRewards);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    private PointScaleStats toPointScaleStats(List<PointScaleRewardEntity> entities) {
        String name = entities.get(0).getPointScaleEntity().getName();
        int scale = entities.get(0).getPointScaleEntity().getScale();
        Timestamp timeStamp = entities.get(0).getTimestamp();

        int amount = 0;
        for (PointScaleRewardEntity entity: entities){
            amount += entity.getAmount();
        }
        PointScaleStats pointScaleStats = new PointScaleStats();
        pointScaleStats.setName(name);
        pointScaleStats.setScale(scale);
        pointScaleStats.setAmount(amount);
        pointScaleStats.setTimestamp(OffsetDateTime.ofInstant(Instant.ofEpochMilli(timeStamp.getTime()), ZoneId.of("UTC")));
        return pointScaleStats;
    }
}
