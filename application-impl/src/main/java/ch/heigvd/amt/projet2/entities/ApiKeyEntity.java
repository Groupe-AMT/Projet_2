package ch.heigvd.amt.projet2.entities;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
public class ApiKeyEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private UUID XApiKey;
}
