package fr.codesbusters.solidstock.client.dto.estimates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostEstimateRowDto {
    private double quantity;
    private double sellPrice;
    private int productId;
}
