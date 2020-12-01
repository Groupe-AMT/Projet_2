package ch.heigvd.amt.projet2.entities;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
public class EventEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID IDUser;

    private Timestamp timestamp;
    private String userName;
    private String action;
    private String attribute;

    @ManyToOne
    private ApplicationEntity application;
}
