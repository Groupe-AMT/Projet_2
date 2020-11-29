package ch.heigvd.amt.projet2.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@Data
@Entity
public class EventEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private UUID IDUser;
    private Date timestamp;

    private String userName;
    private String action;
    private String attribute;

    @ManyToOne
    private ApplicationEntity application;
}
