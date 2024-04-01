package fr.codesbusters.solidstock.dto.invoice;

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
public class GetInvoiceRowDto {
    private int id;
    private double quantity;
    private double sellPrice;
    private GetProductDto product;
    private String createdAt;
    private String updatedAt;
}
