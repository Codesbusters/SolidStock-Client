package fr.codesbusters.solidstock.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdParty {

    private int id;

    private String firstName;

    private String lastName;

    private String city;

    private int zipCode;

    private String address;

    private int streetNumber;

    private String email;

    private String mobilePhone;

}
