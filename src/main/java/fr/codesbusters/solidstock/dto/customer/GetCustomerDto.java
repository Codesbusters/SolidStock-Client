package fr.codesbusters.solidstock.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCustomerDto {
    private int id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zipCode;
    private String streetNumber;
    private String email;
    private String mobilePhone;
    private String homePhone;
    private String workphone;
    private String webSite;
    private String country;
    private Boolean corporation;
    private String siren;
    private String siret;
    private String rib;
    private int rcs;
    private List<String> estimates;
    private List<String> invoices;
    private List<String> orderForms;
    private String user;
    private String createdAt;
    private String updatedAt;
}
