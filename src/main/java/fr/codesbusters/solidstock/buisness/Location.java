package fr.codesbusters.solidstock.buisness;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Location {

    private Integer id;

    private String name;

    private String description;

    private String position;

    private Date createdAt;

    private Date updatedAt;
}
