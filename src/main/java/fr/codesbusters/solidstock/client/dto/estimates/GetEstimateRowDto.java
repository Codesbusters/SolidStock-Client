package fr.codesbusters.solidstock.client.dto.estimates;

import fr.codesbusters.solidstock.client.dto.product.GetProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetEstimateRowDto {
    private int id;
    private double quantity;
    private double sellPrice;
    private GetProductDto product;
    private String createdAt;
    private String updatedAt;
}
