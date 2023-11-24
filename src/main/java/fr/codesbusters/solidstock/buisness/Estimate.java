package fr.codesbusters.solidstock.buisness;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estimate {

    private Integer id;

    private String subject;

    private String description;

    private Integer customerId;

    private Date expiryDate;

    private Date createdAt;

    private Date updatedAt;

}
