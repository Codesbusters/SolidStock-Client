package fr.codesbusters.solidstock.client.dto.productFamily;


import fr.codesbusters.solidstock.client.dto.product.GetProductDto;
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
}
