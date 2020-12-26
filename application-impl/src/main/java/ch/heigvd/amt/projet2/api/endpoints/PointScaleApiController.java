package ch.heigvd.amt.projet2.api.endpoints;

import ch.heigvd.amt.projet2.api.PointscalesApi;
import ch.heigvd.amt.projet2.api.model.PointScale;
import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.BadgeEntity;
import ch.heigvd.amt.projet2.entities.PointScaleEntity;
import ch.heigvd.amt.projet2.entities.PointScaleRewardEntity;
import ch.heigvd.amt.projet2.repositories.PointScaleRepository;
import ch.heigvd.amt.projet2.repositories.PointScaleRewardRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PointScaleApiController implements PointscalesApi {
    @Autowired
    PointScaleRepository pointScaleRepository;

    @Autowired
    private HttpServletRequest context;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createPointScale(@ApiParam(value = ""  )  @Valid @RequestBody(required = false) PointScale pointScale){
        if(pointScale.getName() == null || pointScale.getScale() == null) return ResponseEntity.status(404).build();
        else if(pointScaleRepository.findByNameAndApplication(pointScale.getName(), (ApplicationEntity) context.getAttribute("application")) != null ) return ResponseEntity.status(409).build();

        PointScaleEntity newPointScaleEntity = toPointScaleEntity(pointScale);
        pointScaleRepository.save(newPointScaleEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newPointScaleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<PointScale> getPointScale(@ApiParam(value = "",required=true) @PathVariable("id") Integer id){
        PointScaleEntity tmp = pointScaleRepository.findByIdAndApplication(Long.valueOf(id), (ApplicationEntity) context.getAttribute("application"));
        if (tmp == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(toPointScale(tmp));
    }

    public ResponseEntity<List<PointScale>> getPointScales(){
        List<PointScale> pointScales = new ArrayList<>();
        for(PointScaleEntity pointScaleEntity : pointScaleRepository.findByApplication((ApplicationEntity) context.getAttribute("application")))
            pointScales.add(toPointScale(pointScaleEntity));

        return ResponseEntity.ok(pointScales);
    }

    private PointScaleEntity toPointScaleEntity(PointScale pointScale) {
        PointScaleEntity entity = new PointScaleEntity();
        entity.setName(pointScale.getName());
        entity.setScale(pointScale.getScale());
        entity.setApplication((ApplicationEntity) context.getAttribute("application"));
        return entity;
    }

    private PointScale toPointScale(PointScaleEntity entity) {
        PointScale pointScale = new PointScale();
        pointScale.setName(entity.getName());
        pointScale.setScale(entity.getScale());
        return pointScale;
    }
}
