package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.BadgesApi;
import ch.heigvd.amt.projet2.api.model.Application;
import ch.heigvd.amt.projet2.api.model.Badge;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeEntity;
import ch.heigvd.amt.projet2.entities.RewardsEntity;
import ch.heigvd.amt.projet2.repositories.BadgeRepository;
import ch.heigvd.amt.projet2.repositories.RewardsRepository;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BadgesApiController implements BadgesApi {

    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    RewardsRepository rewardsRepository;

    @Autowired
    private HttpServletRequest context;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createBadge(@ApiParam(value = "", required = true) @Valid @RequestBody Badge badge) {
        BadgeEntity newBadgeEntity = toBadgeEntity(badge);
        badgeRepository.save(newBadgeEntity);

        // création d'une entrée dans la table de correspondance
        RewardsEntity rewardsEntity = new RewardsEntity();
        rewardsEntity.setApplication((ApplicationEntity) context.getAttribute("application"));
        rewardsEntity.setBadge(newBadgeEntity);
        rewardsRepository.save(rewardsEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId()).toUri();

        long id = newBadgeEntity.getId();
        return ResponseEntity.created(location).body(Long.toString(id));
    }

    public ResponseEntity<List<Badge>> getBadges() {
        List<Badge> badges = new ArrayList<>();
        for (BadgeEntity badgeEntity : badgeRepository.findByApplication((ApplicationEntity) context.getAttribute("application"))) {
            badges.add(toBadge(badgeEntity));
        }
        return ResponseEntity.ok(badges);
    }

    public ResponseEntity<Badge> getBadge(@ApiParam(value = "",required=true) @PathVariable("id") Integer id) {
        BadgeEntity existingBadgeEntity = badgeRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toBadge(existingBadgeEntity));
    }

    private BadgeEntity toBadgeEntity(Badge badge) {
        BadgeEntity entity = new BadgeEntity();
        entity.setName(badge.getName());
        entity.setImage(badge.getImage());
        entity.setApplication((ApplicationEntity) context.getAttribute("application"));
        return entity;
    }

    private Badge toBadge(BadgeEntity entity) {
        Badge badge = new Badge();
        badge.setName(entity.getName());
        badge.setImage(entity.getImage());
        return badge;
    }
}
