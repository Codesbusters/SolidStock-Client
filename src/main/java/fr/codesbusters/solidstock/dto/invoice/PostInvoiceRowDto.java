package fr.codesbusters.solidstock.dto.invoice;

import fr.codesbusters.solidstock.dto.product.GetProductDto;
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
