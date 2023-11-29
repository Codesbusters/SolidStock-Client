package fr.codesbusters.solidstock.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private int id;

    private String name;

    private int thirdPartyId;

    private String address;

    private boolean corporation;

    private String corporateName;

    private int siren;

    private int siret;

    private String rib;

    private int rcs;

}
