package ch.heigvd.amt.projet2.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ApiKeyDAO implements Serializable {
    private UUID XApiKey;
}
