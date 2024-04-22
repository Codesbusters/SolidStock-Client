package fr.codesbusters.solidstock.client.dto.quantityType;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetQuantityTypeDto {
    private int id;
    private String name;
    private String description;
    private String unit;
    private String createdAt;
    private String updatedAt;

}
