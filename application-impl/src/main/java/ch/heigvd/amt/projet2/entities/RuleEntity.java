package ch.heigvd.amt.projet2.entities;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class RuleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String action;
    private String attribute;
    private String nameBadge;
    private String namePointScale;
    private int amount;

    @ManyToOne
    private ApplicationEntity application;

    public boolean isTriggered(EventEntity event){
        return action.equals(event.getAction()) &&
                (attribute == null || attribute.equals(event.getAttribute()));
    }
}
