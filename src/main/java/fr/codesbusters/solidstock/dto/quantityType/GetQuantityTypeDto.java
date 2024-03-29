package fr.codesbusters.solidstock.dto.quantityType;

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
    private List<Integer> products;

    public GetQuantityTypeDto(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static  GetQuantityTypeDto fromId(int id) {
        return new GetQuantityTypeDto(id);
    }
}
