package fr.codesbusters.solidstock.dto.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DelSupplierDto {
    private int id;
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
    private List<String> products;

}