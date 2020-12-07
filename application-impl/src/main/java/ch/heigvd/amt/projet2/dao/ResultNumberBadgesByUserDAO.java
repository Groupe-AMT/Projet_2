package ch.heigvd.amt.projet2.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ResultNumberBadgesByUserDAO {
    private UUID IDUser;
    private long NbBadges;
}
