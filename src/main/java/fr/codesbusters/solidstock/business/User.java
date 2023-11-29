package fr.codesbusters.solidstock.business;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User {

    private int id;

    private String name;

    private int thirdPartyId;

    private String password;

    private int roleId;

}
