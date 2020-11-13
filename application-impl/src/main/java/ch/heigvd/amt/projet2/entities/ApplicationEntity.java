package ch.heigvd.amt.projet2.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private String contact;
    private UUID XApiKey;

    @Column(columnDefinition = "DATE")
    private LocalDate expirationDate;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime expirationDateTime;

}
