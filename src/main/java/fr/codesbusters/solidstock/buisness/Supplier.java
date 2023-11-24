package fr.codesbusters.solidstock.buisness;

import lombok.*;

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
