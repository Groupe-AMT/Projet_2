package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.RuleApi;
import ch.heigvd.amt.projet2.api.model.Event;
import ch.heigvd.amt.projet2.api.model.Rule;
import ch.heigvd.amt.projet2.api.services.EventProcessor;
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
import java.sql.Date;

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
        RuleEntity newRuleEntity = new RuleEntity();
        newRuleEntity = toRuleEntity(rule);

        ApplicationEntity app = (ApplicationEntity) context.getAttribute("application");

        String response = "Error in rule creation...";
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand("-1").toUri();

        if (newRuleEntity == null) {
            return ResponseEntity.created(location).body(response);
        }

        if (app != null) {
            ruleRepository.save(newRuleEntity);

            location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newRuleEntity.getId()).toUri();
            response = "Rule successfully created !";
        }

        return ResponseEntity.created(location).body(response);
    }

    private RuleEntity toRuleEntity(Rule rule) {
        RuleEntity entity = new RuleEntity();

        entity.setName(rule.getName());
        entity.setApplication((ApplicationEntity) context.getAttribute("application"));

        entity.setAction(rule.getIf().getAction());
        entity.setAttribute(rule.getIf().getAttribute());

        try {
            String badgeName = rule.getThen().getBadge();
            String pointScaleName = rule.getThen().getPoints().getPointscale();

            if (badgeName != null){
                entity.setNameBadge(badgeRepository.findByName(rule.getThen().getBadge()).getName());
            } else {
                entity.setNameBadge(null);
            }

            if (pointScaleName != null){
                entity.setNamePointScale(pointScaleRepository.findByNameAndApp(rule.getThen().getPoints().getPointscale(), (ApplicationEntity) context.getAttribute("application")).getName());
                entity.setAmount(rule.getThen().getPoints().getAmount());
            } else {
                entity.setNamePointScale(null);
                entity.setAmount(0);
            }

        } catch (Exception e) {
            return null;
        }
        return entity;
    }
}