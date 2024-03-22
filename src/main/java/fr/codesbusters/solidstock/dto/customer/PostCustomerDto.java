package fr.codesbusters.solidstock.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCustomerDto {
    private int id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String zipCode;
    private String streetNumber;
    private String address;
    private String email;
    private String mobilePhone;
    private String homePhone;
    private String workPhone;
    private String website;
    private String siren;
    private String siret;
    private String rib;
    private int rcs;
    private String fax;
    private boolean deleted;
}
