package fr.codesbusters.solidstock.business;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Vat {

    private Integer id;

    private String name;

    private String description;

    private String percent;

    private Date createdAt;

    private Date updatedAt;
}
