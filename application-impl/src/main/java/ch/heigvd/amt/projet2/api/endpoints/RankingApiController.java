package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.RankingsApi;
import ch.heigvd.amt.projet2.api.model.BadgeRewards;
import ch.heigvd.amt.projet2.api.model.Ranking;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeRewardEntity;
import ch.heigvd.amt.projet2.repositories.BadgeRewardRepository;
import ch.heigvd.amt.projet2.repositories.EndUserRepository;
import ch.heigvd.amt.projet2.repositories.PointScaleRewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RankingApiController implements RankingsApi {

    @Autowired
    BadgeRewardRepository badgeRewardRepository;

    @Autowired
    PointScaleRewardRepository pointScaleRewardRepository;

    @Autowired
    EndUserRepository endUserRepository;

    @Autowired
    private HttpServletRequest context;

    public ResponseEntity<List<Ranking>> getUserBadges(){
        List<BadgeRewardEntity> rank = new ArrayList<>();
        Ranking r = new Ranking();
        rank = badgeRewardRepository.findByApplicationOrderByBadge((ApplicationEntity) context.getAttribute("application"));
        return null;
    }

}
