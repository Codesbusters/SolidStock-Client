package fr.codesbusters.solidstock.dto.order;

import fr.codesbusters.solidstock.dto.product.GetProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderRowDto {
    private int id;
    private double quantity;
    private GetProductDto product;
    private String createdAt;
    private String updatedAt;
}