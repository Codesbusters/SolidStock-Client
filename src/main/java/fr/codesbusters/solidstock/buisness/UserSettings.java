package fr.codesbusters.solidstock.buisness;

import lombok.*;
import org.joda.time.DateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserSettings {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String userRole;

    private String langage;

    private DateTime lastConnection;
}
