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
public class Supplier {

    private Integer id;

    private String name;

    private String thirdPartyId;

    private Date createdAt;

    private Date updatedAt;

}
