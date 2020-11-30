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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Controller
public class ApplicationsApiController implements ApplicationsApi {

    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    private HttpServletRequest context;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiKey> registerApp(@ApiParam(value = ""  )  @Valid @RequestBody(required = false) Registration registration) {
        if (registration.getContact() == null || registration.getDescription() == null || registration.getName() == null){
            return ResponseEntity.status(404).build();
        }

        Application application = new Application();
        application.setName(registration.getName());
        application.setDescription(registration.getDescription());
        application.setContact(registration.getContact());
        application.setXapiKey(UUID.randomUUID());
        ApplicationEntity newApplicationEntity = toApplicationEntity(application);
        applicationRepository.save(newApplicationEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newApplicationEntity.getId()).toUri();

        ApiKey a = new ApiKey();
        a.setXapiKey(application.getXapiKey());
        return ResponseEntity.created(location).body(a);
    }

    @Override
    public ResponseEntity<List<Application>> getApplications(){
        ApplicationEntity appInfo = (ApplicationEntity) context.getAttribute("application");
        Application app = toApplication(appInfo);
        List<Application> apps = new LinkedList<>();
        apps.add(app);
        return ResponseEntity.ok().body(apps);
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
        application.setContact(entity.getContact());
        application.setDescription(entity.getDescription());
        application.setName(entity.getName());
        application.setXapiKey(entity.getXApiKey());
        return application;
    }
}
