package fr.codesbusters.solidstock.client.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostProductDto {
    private String name;
    private String description;
    private String barCode;
    private String buyPrice;
    private String sellPrice;
    private int minimumStockQuantity;
    private int supplierId;
    private int vatId;
    private String quantityTypeUnit;
    private int productFamilyId;
}