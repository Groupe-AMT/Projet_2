package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.RulesApi;
import ch.heigvd.amt.projet2.api.model.Rule;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeEntity;
import ch.heigvd.amt.projet2.entities.PointScaleEntity;
import ch.heigvd.amt.projet2.entities.RuleEntity;
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
public class RulesApiController implements RulesApi{
    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private HttpServletRequest context;

    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private PointScaleRepository pointScaleRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createRule(@ApiParam(value = "", required = true) @Valid @RequestBody Rule rule){
       // si la règle n'a pas de nom, pas de if, si le then est vide ou pas de then on envoit un 404
        if (rule.getName() == null ||
                rule.getIf() == null ||
                rule.getThen() == null ||
                (rule.getThen().getBadge() == null && rule.getThen().getPoints() == null))  return ResponseEntity.status(404).build();

        // si le badge ou le pointscale ne sont pas dans la Db on envoit un 404
        RuleEntity newRuleEntity;
        try{
            newRuleEntity = toRuleEntity(rule);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(404).build();
        }

        ruleRepository.save(newRuleEntity);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    private RuleEntity toRuleEntity(Rule rule) {

        RuleEntity entity = new RuleEntity();
        entity.setName(rule.getName());
        entity.setApplication((ApplicationEntity) context.getAttribute("application"));
        entity.setAction(rule.getIf().getAction());
        entity.setAttribute(rule.getIf().getAttribute());

        String badgeName = rule.getThen().getBadge();
        String pointScaleName = rule.getThen().getPoints().getPointscale();

        if (badgeName != null){
            BadgeEntity badgeEntity = badgeRepository.findByNameAndApplication(badgeName,(ApplicationEntity) context.getAttribute("application"));
            if (badgeEntity == null) throw new IllegalArgumentException();

            entity.setNameBadge(badgeEntity.getName());
        } else {
            entity.setNameBadge(null);
        }

        if (pointScaleName != null){
            PointScaleEntity pointScaleEntity = pointScaleRepository.findByNameAndApplication(pointScaleName, (ApplicationEntity) context.getAttribute("application"));
            if (pointScaleEntity == null) throw new IllegalArgumentException();

            entity.setNamePointScale(pointScaleEntity.getName());
            entity.setAmount(rule.getThen().getPoints().getAmount());
        } else {
            entity.setNamePointScale(null);
            entity.setAmount(0);
        }
        return entity;
    }
}
