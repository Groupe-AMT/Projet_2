package ch.heigvd.amt.projet2.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class RegistrationDAO implements Serializable {
    private String name;
    private String description;
    private String contact;
}
