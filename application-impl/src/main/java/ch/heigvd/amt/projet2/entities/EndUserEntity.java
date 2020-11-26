package ch.heigvd.amt.projet2.entities;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
public class EndUserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String appName;
    private UUID IDUser;
    private String userName;

    private int nbEvents;
    private int nbVotes;
    private int nbMessages;
}
