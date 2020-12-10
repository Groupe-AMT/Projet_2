package ch.heigvd.amt.projet2.entities;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Table
@Entity
@Data
public class PointScaleRewardEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ApplicationEntity application;

    @ManyToOne
    private PointScaleEntity pointScaleEntity;

    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID IDUser;

    private int amount;

    private Timestamp timestamp;
}
