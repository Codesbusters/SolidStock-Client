package fr.codesbusters.solidstock.buisness;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Integer id;
    
    private String name;
    
    private Integer thirdPartyId;

    private String address;
    
    private Boolean corporation;
    
    private String corporateName;
    
    private Integer siren;
    
    private Integer siret;
    
    private String rib;
    
    private Integer rcsInteger;
    
    private Date createdAt;
    
    private Date updatedAt;

}
