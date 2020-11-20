package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.model.*;
import ch.heigvd.amt.projet2.entities.EndUserEntity;
import ch.heigvd.amt.projet2.repositories.EndUserRepository;
import ch.heigvd.amt.projet2.repositories.RegistrationRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ch.heigvd.amt.projet2.api.RegisterUserApi;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Controller
public class RegisterUserController implements RegisterUserApi{
    @Autowired
    EndUserRepository endUserRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EndUser> registerEndUser(@ApiParam(value = ""  )  @Valid @RequestBody(required = false) InlineObject inlineObject) {
        UUID uuid = UUID.randomUUID();
        EndUser endUser = new EndUser();
        endUser.setIdUser(uuid);
        endUser.setUserName(inlineObject.getUserName());
        EndUserEntity endUserEntity = toEndUserEntity(endUser);
        endUserRepository.save(endUserEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(endUserEntity.getId()).toUri();

        return ResponseEntity.created(location).body(endUser);
    }

    private EndUserEntity toEndUserEntity(EndUser endUser) {
        EndUserEntity entity = new EndUserEntity();
        entity.setIDUser(endUser.getIdUser());
        entity.setUserName(endUser.getUserName());
        return entity;
    }

    private EndUser toEndUser(EndUserEntity entity) {
        EndUser endUser = new EndUser();
        endUser.setIdUser(entity.getIDUser());
        endUser.setUserName(entity.getUserName());
        return endUser;
    }
}
