package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.OverallrankingsApi;
import ch.heigvd.amt.projet2.api.model.RankingOverAll;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.ResultOverAll;
import ch.heigvd.amt.projet2.repositories.PointScaleRewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OverAllRankings implements OverallrankingsApi {
    @Autowired
    PointScaleRewardRepository pointScaleRewardRepository;

    @Autowired
    private HttpServletRequest context;

    public ResponseEntity<List<RankingOverAll>> getOverAllRanking(){
        List<ResultOverAll> test = pointScaleRewardRepository.overAll((ApplicationEntity) context.getAttribute("application"));
        return ResponseEntity.ok(toRankingOverAll(test));
    }

    private List<RankingOverAll> toRankingOverAll(List<ResultOverAll> res) {
        List<RankingOverAll> r = new ArrayList<>();
        for (ResultOverAll result : res){
            RankingOverAll tmp = new RankingOverAll();
            tmp.setIdUser(result.getIdUser());
            tmp.setTotalBadge(result.getTotalBadge());
            tmp.setTotalPointScale(result.getTotalPointScale());
            r.add(tmp);
        }
        return r;
    }
}
