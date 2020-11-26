package ch.heigvd.amt.projet2.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
public class EventEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private UUID IDUser;
    private String userName;
    private String action;

    @ManyToOne
    private ApplicationEntity application;

}
