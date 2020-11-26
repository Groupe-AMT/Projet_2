package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.EventApi;
import ch.heigvd.amt.projet2.api.exceptions.ApiException;
import ch.heigvd.amt.projet2.api.model.EndUser;
import ch.heigvd.amt.projet2.api.model.Event;
import ch.heigvd.amt.projet2.api.services.EventProcessor;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.EndUserEntity;
import ch.heigvd.amt.projet2.entities.EventEntity;
import ch.heigvd.amt.projet2.repositories.ApplicationRepository;
import ch.heigvd.amt.projet2.repositories.EndUserRepository;
import ch.heigvd.amt.projet2.repositories.EventRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller
public class EventsApiController implements EventApi {

    @Autowired
    EndUserRepository endUserRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    private HttpServletRequest context;

    private EventProcessor eventProcessor;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createEvent(@ApiParam(value = "", required = true) @Valid @RequestBody Event event){
        eventProcessor = new EventProcessor(endUserRepository);
        EventEntity newEventEntity = toEventEntity(event);

        ApplicationEntity app = null;
        List<ApplicationEntity> apps = applicationRepository.findByXApiKey(UUID.fromString(context.getHeader("X-API-KEY")));
        if (!apps.isEmpty()) {
            app = apps.get(0);
        }

        String response = "Error in event creation...";

        if (app != null) {
            eventRepository.save(newEventEntity);
            eventProcessor.processEvent(app, newEventEntity);
            response = "Event successfully created !";
        }

        System.out.println(endUserRepository.findByIDUser(newEventEntity.getIDUser()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newEventEntity.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    private EventEntity toEventEntity(Event event) {
        EventEntity entity = new EventEntity();
        entity.setIDUser(event.getIdUser().toString());
        entity.setUserName(event.getUserName());
        entity.setAction(event.getAction());
        entity.setApplication((ApplicationEntity) context.getAttribute("application"));
        return entity;
    }
}
