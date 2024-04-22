package fr.codesbusters.solidstock.client.dto.admin;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAdminSettingsDto {
    private long id;
    @Nullable
    private String companyName;
    @Nullable
    private String ownerName;
    @Nullable
    private String siret;
    @Nullable
    private String siren;
    @Nullable
    private int rcs;
    @Nullable
    private String streetNumber;
    @Nullable
    private String streetName;
    @Nullable
    private String zipCode;
    @Nullable
    private String city;
    @Nullable
    private String country;
    @Nullable
    private String email;
    @Nullable
    private String phone;
    @Nullable
    private String iban;
    @Nullable
    private String image;
}
