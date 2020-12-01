package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.EventApi;
import ch.heigvd.amt.projet2.api.model.Event;
import ch.heigvd.amt.projet2.api.services.EventProcessor;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.EventEntity;
import ch.heigvd.amt.projet2.repositories.EventRepository;
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
import java.sql.Timestamp;

@Controller
public class EventsApiController implements EventApi {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    private HttpServletRequest context;

    @Autowired
    private EventProcessor eventProcessor;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createEvent(@ApiParam(value = "", required = true) @Valid @RequestBody Event event){
        if(event.getAction() == null ||
                event.getAttribute() == null ||
                event.getIdUser() == null ||
                event.getTimestamp() == null ||
                event.getUserName() == null){
            return ResponseEntity.status(404).build();
        }

        EventEntity newEventEntity = toEventEntity(event);

        eventRepository.save(newEventEntity);
        eventProcessor.processEvent((ApplicationEntity) context.getAttribute("application"), newEventEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newEventEntity.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    private EventEntity toEventEntity(Event event) {
        EventEntity entity = new EventEntity();
        entity.setIDUser(event.getIdUser());
        entity.setUserName(event.getUserName());
        entity.setAction(event.getAction());
        entity.setApplication((ApplicationEntity) context.getAttribute("application"));
        entity.setTimestamp(Timestamp.valueOf(event.getTimestamp().toLocalDateTime()));
        entity.setAttribute(event.getAttribute());
        return entity;
    }
}
