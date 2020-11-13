package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.ApplicationsApi;
import ch.heigvd.amt.projet2.api.model.ApiKey;
import ch.heigvd.amt.projet2.api.model.Application;
import ch.heigvd.amt.projet2.api.model.Registration;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.repositories.ApplicationRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller
public class ApplicationsApiController implements ApplicationsApi {

    @Autowired
    ApplicationRepository applicationRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiKey> registerApp(@ApiParam(value = ""  )  @Valid @RequestBody(required = false) Registration registration) {
        UUID uuid = UUID.randomUUID();
        Application application = new Application();
        application.setName(registration.getName());
        application.setDescription(registration.getDescription());
        application.setContact(registration.getContact());
        application.setXapiKey(uuid);

        ApplicationEntity newApplicationEntity = toApplicationEntity(application);
        applicationRepository.save(newApplicationEntity);

        //Long id = newApplicationEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newApplicationEntity.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Application>> getApplications(){
        return  null;
    }

    private ApplicationEntity toApplicationEntity(Application application) {
        ApplicationEntity entity = new ApplicationEntity();
        entity.setContact(application.getContact());
        entity.setDescription(application.getDescription());
        entity.setName(application.getName());
        entity.setXApiKey(application.getXapiKey());
        return entity;
    }

    private Application toApplication(ApplicationEntity entity) {
        Application application = new Application();
        application.setContact(application.getContact());
        application.setDescription(application.getDescription());
        application.setName(application.getName());
        application.setXapiKey(application.getXapiKey());
        return application;
    }
}
