package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.RankingsApi;
import ch.heigvd.amt.projet2.api.model.ResultsByUser;
import ch.heigvd.amt.projet2.dao.ResultByUserDAO;
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

    public ResponseEntity<List<ResultsByUser>> getUserBadges(){
        List<ResultsByUser> rank = new ArrayList<>();
        List<ResultByUserDAO> obj = badgeRewardRepository.countAllByBadge((ApplicationEntity) context.getAttribute("application"));
        ResultsByUser r = new ResultsByUser();
        r.setResults(obj);
        r.setName("Badge Rewards Ranking");
        rank.add(r);
        r = new ResultsByUser();
        obj = pointScaleRewardRepository.countAllByPointScale((ApplicationEntity) context.getAttribute("application"));
        r.setResults(obj);
        r.setName("PointScale Rewards Ranking");
        rank.add(r);
        return ResponseEntity.ok(rank);
    }

}
