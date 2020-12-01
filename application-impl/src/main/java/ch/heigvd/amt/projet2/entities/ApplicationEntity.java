package ch.heigvd.amt.projet2.entities;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
public class ApplicationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private String contact;

    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID XApiKey;
}
