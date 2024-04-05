package fr.codesbusters.solidstock.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserDto {
    private String companyName;
    private String firstName;
    private String lastName;
    private String city;
    private String zipCode;
    private String address;
    private String streetNumber;
    private String email;
    private String mobilePhone;
    private String homePhone;
    private String workPhone;
    private String fax;
    private String website;
    private String country;
    private String siren;
    private String siret;
    private String rib;
    private int rcs;
    private String note;
}