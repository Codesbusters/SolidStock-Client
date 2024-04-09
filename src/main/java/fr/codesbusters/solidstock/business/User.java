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

    private String password;

    private int customerId;

    private String customerName;

    private int roleId;

    private String roleName;

}