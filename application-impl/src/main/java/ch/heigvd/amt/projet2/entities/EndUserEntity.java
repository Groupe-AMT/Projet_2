package ch.heigvd.amt.projet2.entities;

import lombok.Data;
import javax.persistence.*;
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
}
