package fr.codesbusters.solidstock.client.dto.invoice;

import fr.codesbusters.solidstock.client.dto.product.GetProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostInvoiceRowDto {
    private double quantity;
    private double sellPrice;
    private int productId;
}
