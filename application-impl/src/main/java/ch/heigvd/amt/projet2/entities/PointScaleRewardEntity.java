package ch.heigvd.amt.projet2.entities;

import lombok.Data;

import javax.persistence.*;

@Table
@Entity
@Data
public class PointScaleRewardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ApplicationEntity application;

    @ManyToOne
    private PointScaleEntity pointScaleEntity;
}
