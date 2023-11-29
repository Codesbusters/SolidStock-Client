package fr.codesbusters.solidstock.business;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Location {

    private int id;

    private String name;

    private String description;

    private int parentLocationId;

}
