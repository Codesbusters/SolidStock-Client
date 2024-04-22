package fr.codesbusters.solidstock.dto.supplierOrder;

import fr.codesbusters.solidstock.dto.product.GetProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetSupplierOrderRowDto {
    private long id;
    private GetProductDto product;
    private double buyPrice;
    private double quantity;
    private String status;
    private String note;
}
