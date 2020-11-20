package ch.heigvd.amt.projet2.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class RewardsEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long badgeID;
    @Column(length=16)
    private UUID userID;
    @Column(columnDefinition = "DATE")
    private LocalDate attributionDate;
}
