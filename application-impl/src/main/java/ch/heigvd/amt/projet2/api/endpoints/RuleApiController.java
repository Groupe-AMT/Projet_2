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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.sql.Date;

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

        String response = "Error in event creation...";
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getId()).toUri();

        if (newRuleEntity == null) {
            return ResponseEntity.created(location).body(response);
        }

        if (app != null) {
            ruleRepository.save(newRuleEntity);
            response = "Event successfully created !";
        }

        return ResponseEntity.created(location).body(response);
    }

    private RuleEntity toRuleEntity(Rule rule) {
        RuleEntity entity = new RuleEntity();

        System.out.println(entity);

        entity.setName(rule.getName());
        entity.setApplication((ApplicationEntity) context.getAttribute("application"));

        entity.setAction(rule.getIf().getAction());
        entity.setAttribute(rule.getIf().getAttribute());

        try {
            BadgeEntity badge = new BadgeEntity();
            badge = badgeRepository.findByName(rule.getThen().getBadge());
            entity.setBadge(badge);

            PointScaleEntity pointScale = new PointScaleEntity();
            pointScale = pointScaleRepository.findByName(rule.getThen().getPoints().getPointscale());
            entity.setPointScale(pointScale);
            entity.setAmount(rule.getThen().getPoints().getAmount());
        } catch (Exception e) {
            return null;
        }
        return entity;
    }
}
