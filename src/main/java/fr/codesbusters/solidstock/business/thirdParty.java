package fr.codesbusters.solidstock.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class thirdParty {

    private Integer thirdPartyId;

    private String firstName;

    private String lastName;

    private String city;

    private Integer zipCode;

    private String address;

    private Integer streetNumber;

    private String email;

    private String mobilePhone;

    private Date createdAt;

    private Date updatedAt;

}
