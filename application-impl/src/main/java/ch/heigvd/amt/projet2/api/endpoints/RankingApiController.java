package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.RankingsApi;
import ch.heigvd.amt.projet2.api.model.ResultsByUser;
import ch.heigvd.amt.projet2.entities.ResultByUserDAO;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
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

    private final List<ResultsByUser> rank = new ArrayList<>();

    public ResponseEntity<List<ResultsByUser>> getUserBadges(){
        toResultsByUser(
                badgeRewardRepository.countAllByBadge((ApplicationEntity) context.getAttribute("application")),
                "Badge Rewards Ranking");
        toResultsByUser(
                pointScaleRewardRepository.countAllByPointScale((ApplicationEntity) context.getAttribute("application")),
                "PointScale Rewards Ranking");
        return ResponseEntity.ok(rank);
    }

    private void toResultsByUser(List<ResultByUserDAO> obj, String name){
        ResultsByUser r = new ResultsByUser();
        r.setResults(obj);
        r.setName(name);
        rank.add(r);
    }

}
