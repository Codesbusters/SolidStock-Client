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

    private String name;

    private String address;

    private String additionnalAddress;

    private String zipCode;

    private String city;

    private String country;

    private String phone;

    private String email;

    private String website;

}
