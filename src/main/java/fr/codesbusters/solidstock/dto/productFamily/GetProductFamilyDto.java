package fr.codesbusters.solidstock.dto.productFamily;


import fr.codesbusters.solidstock.dto.product.GetProductDto;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductFamilyDto {
    private int id;
    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;
    private boolean isDeleted;
    private GetProductDto productDto;
    private List<Integer> products;

    public GetProductFamilyDto(int id) {
        this.id = id;
    }

    public static GetProductFamilyDto fromId(int id) {
        return new GetProductFamilyDto();
    }
}
