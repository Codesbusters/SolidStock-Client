package fr.codesbusters.solidstock.dto.productFamily;


import fr.codesbusters.solidstock.dto.product.GetProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<Integer> products;
}
