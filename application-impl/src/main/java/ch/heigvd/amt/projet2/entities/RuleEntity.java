package ch.heigvd.amt.projet2.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

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
        boolean res = false;
        if (action.equals(event.getAction())) {
            if (attribute == null || attribute == event.getAttribute()) {
                res = true;
            }
        }
        return res;
    }
}
