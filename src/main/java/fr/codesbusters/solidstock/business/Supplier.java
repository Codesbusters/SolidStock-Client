package fr.codesbusters.solidstock.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    private int id;

    private String companyName;

    private String firstName;

    private String lastName;

    private String address;

    private String streetNumber;

    private String zipCode;

    private String city;

    private String country;

    private String mobilePhone;

    private String workPhone;

    private String homePhone;

    private String siret;

    private String siren;

    private String rib;

    private int rcs;

    private String email;

    private String website;

    private String fax;

    private String note;
}