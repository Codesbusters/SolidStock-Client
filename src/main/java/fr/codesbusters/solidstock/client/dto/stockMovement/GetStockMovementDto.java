package fr.codesbusters.solidstock.client.dto.stockMovement;

import fr.codesbusters.solidstock.client.dto.product.GetProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetStockMovementDto {
    private long id;
    private String type;
    private double quantity;
    private String expiredDate;
    private String batchNumber;
    private String note;
    private GetProductDto product;
    private long location;
    private String createdAt;
    private String updatedAt;
    private boolean deleted;
}
