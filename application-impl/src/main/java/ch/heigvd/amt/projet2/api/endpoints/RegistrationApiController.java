package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.model.ApiKey;
import ch.heigvd.amt.projet2.api.model.Application;
import ch.heigvd.amt.projet2.api.model.Registration;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

public class RegistrationApiController {

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiKey> registerApp(@ApiParam(value = ""  )  @Valid @RequestBody(required = false) Registration registration) {
        UUID uuid = UUID.randomUUID();
        application.setXapiKey(uuid);

        ApplicationEntity newApplicationEntity = toApplicationEntity(application);
        applicationRepository.save(newApplicationEntity);

        //Long id = newApplicationEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newApplicationEntity.getId()).toUri();
        //JSONObject js = new JSONObject();
        return ResponseEntity.created(location).build();
    }
}
