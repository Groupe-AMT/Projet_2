package ch.heigvd.amt.projet2.api.services;

import ch.heigvd.amt.projet2.entities.*;
import ch.heigvd.amt.projet2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventProcessor{

    @Autowired
    EndUserRepository endUserRepository;
    @Autowired
    RuleRepository ruleRepository;

    @Autowired
    BadgeRepository badgeRepository;
    @Autowired
    PointScaleRepository pointScaleRepository;

    @Autowired
    BadgeRewardRepository badgeRewardRepository;
    @Autowired
    PointScaleRewardRepository pointScaleRewardRepository;

    public void processEvent(ApplicationEntity app, EventEntity event) {
        EndUserEntity user = endUserRepository.findByIDUserAndAppName(event.getIDUser(), app.getName());
        List<RuleEntity> linkedRules = ruleRepository.findAllByAction(event.getAction());

        if (user == null) {
            user = new EndUserEntity();
            user.setIDUser(event.getIDUser());
            user.setUserName(event.getUserName());
            user.setAppName(app.getName());
            endUserRepository.save(user);
        }

        // Check if rules are triggered
        for (RuleEntity rule: linkedRules){
            if (rule.isTriggered(event)){
                Award(app, rule, user, event);
            }
        }

    }

    public void Award(ApplicationEntity app, RuleEntity rule, EndUserEntity user, EventEntity event){
        // On récupére le badge dans la règle
        BadgeEntity badge = getBadgeFromName(rule.getNameBadge(), app);
        // On récupère la pointscale dans la règle et le montant
        PointScaleEntity pointScale = getPointScaleFromName(rule.getNamePointScale(), app);
        int amount = rule.getAmount();

        if (badge != null) {
            // On livre le badge en récompense si elle ne l'a pas déjà été
            if (badgeRewardRepository.findByBadgeAndIDUser(badge, user.getIDUser()) == null) {
                BadgeRewardEntity badgeReward = new BadgeRewardEntity();
                badgeReward.setApplication(app);
                badgeReward.setBadge(badge);
                badgeReward.setIDUser(user.getIDUser());
                badgeReward.setTimestamp(event.getTimestamp());
                badgeRewardRepository.save(badgeReward);
            }
        }

        if (pointScale != null) {
            // On ajoute du score en ajoutant une reward point scale à l'utilisateur
            PointScaleRewardEntity pointScaleReward = new PointScaleRewardEntity();
            pointScaleReward.setApplication(app);
            pointScaleReward.setIDUser(user.getIDUser());
            pointScaleReward.setPointScaleEntity(pointScale);
            pointScaleReward.setAmount(amount);
            pointScaleReward.setTimestamp(event.getTimestamp());
            pointScaleRewardRepository.save(pointScaleReward);
        }
    }

    public BadgeEntity getBadgeFromName(String name, ApplicationEntity app){
        if (name != null)
            return badgeRepository.findByNameAndApplication(name, app);
        return null;
    }

    public PointScaleEntity getPointScaleFromName(String name, ApplicationEntity app){
        if (name != null)
            return pointScaleRepository.findByNameAndApplication(name, app);
        return null;
    }
}
