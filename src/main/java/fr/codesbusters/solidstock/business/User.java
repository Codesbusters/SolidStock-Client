package fr.codesbusters.solidstock.business;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private String lastName;

    private String firstName;

    private String mail;

    private String mobileNumber;

    private String login;

    private int thirdPartyId;

    private String password;

    private int roleId;

    private String roleName;

}
