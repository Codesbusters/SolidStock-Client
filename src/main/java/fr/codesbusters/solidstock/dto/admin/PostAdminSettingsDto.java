package fr.codesbusters.solidstock.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostAdminSettingsDto {
    private String companyName;
    private String ownerName;
    private String siret;
    private String siren;
    private int rcs;
    private String streetNumber;
    private String streetName;
    private String zipCode;
    private String city;
    private String country;
    private String email;
    private String phone;
    private String iban;
}
