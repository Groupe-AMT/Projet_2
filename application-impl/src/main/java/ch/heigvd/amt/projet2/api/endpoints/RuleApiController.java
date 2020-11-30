package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.RuleApi;
import ch.heigvd.amt.projet2.api.model.Rule;
import ch.heigvd.amt.projet2.entities.*;
import ch.heigvd.amt.projet2.repositories.BadgeRepository;
import ch.heigvd.amt.projet2.repositories.PointScaleRepository;
import ch.heigvd.amt.projet2.repositories.RuleRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Controller
public class RuleApiController implements RuleApi {
    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private HttpServletRequest context;

    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private PointScaleRepository pointScaleRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createRule(@ApiParam(value = "", required = true) @Valid @RequestBody Rule rule){
        RuleEntity newRuleEntity = toRuleEntity(rule);

        ruleRepository.save(newRuleEntity);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getId()).toUri();

        String response = "Rule successfully created !";
        return ResponseEntity.created(location).body(response);
    }

    private RuleEntity toRuleEntity(Rule rule) {
        if (rule == null) return null;
        if (rule.getName() == null) return null;
        if (rule.getIf() == null) return null;
        if (rule.getThen() == null) return null;

        RuleEntity entity = new RuleEntity();
        entity.setName(rule.getName());
        entity.setApplication((ApplicationEntity) context.getAttribute("application"));
        entity.setAction(rule.getIf().getAction());
        entity.setAttribute(rule.getIf().getAttribute());

        try {
            String badgeName = rule.getThen().getBadge();
            String pointScaleName = rule.getThen().getPoints().getPointscale();

            if (badgeName != null){
                BadgeEntity badgeEntity = badgeRepository.findByNameAndApp(badgeName,(ApplicationEntity) context.getAttribute("application"));
                if (badgeEntity == null) return null;

                entity.setNameBadge(badgeEntity.getName());
            } else {
                entity.setNameBadge(null);
            }

            if (pointScaleName != null){
                PointScaleEntity pointScaleEntity = pointScaleRepository.findByNameAndApp(pointScaleName, (ApplicationEntity) context.getAttribute("application"));
                if (pointScaleEntity == null) return null;

                entity.setNamePointScale(pointScaleEntity.getName());
                entity.setAmount(rule.getThen().getPoints().getAmount());
            } else {
                entity.setNamePointScale(null);
                entity.setAmount(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
