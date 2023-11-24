package fr.codesbusters.solidstock.buisness;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User {

    private Integer id;

    private String name;

    private Integer thirdPartyId;

    private String password;

    private String role;

    private Date createdAt;

    private Date updatedAt;
}
