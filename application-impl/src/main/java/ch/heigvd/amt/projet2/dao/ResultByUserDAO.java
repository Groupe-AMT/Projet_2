package ch.heigvd.amt.projet2.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ResultByUserDAO implements Serializable {
    private UUID IDUser;
    private long NbBadges;
}
