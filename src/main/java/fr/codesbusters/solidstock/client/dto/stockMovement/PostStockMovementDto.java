package fr.codesbusters.solidstock.client.dto.stockMovement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostStockMovementDto {
    private int productId;
    private int locationId;
    private String type;

    private double quantity;

    private String expiredDate;

    private String batchNumber;
}
