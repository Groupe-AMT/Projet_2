package ch.heigvd.amt.projet2.entities;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class PointScaleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ApplicationEntity app;

    private String name;
    private int scale;
}
