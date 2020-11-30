package ch.heigvd.amt.projet2.entities;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Table
public class BadgeRewardEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ApplicationEntity application;

    @ManyToOne
    private BadgeEntity badge;

    private UUID IDUser;
}
