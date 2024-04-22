package fr.codesbusters.solidstock.dto.supplierOrder;

import fr.codesbusters.solidstock.dto.product.GetProductDto;
import fr.codesbusters.solidstock.dto.supplier.GetSupplierDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetSupplierOrderDto {

    private long id;
    private String orderNumber;
    private String orderDate;
    private String deliveryDate;
    private String status;
    private String note;
    private GetSupplierDto supplier;
    private GetSupplierOrderRowDto[] supplierOrderRows;
    private double total;

}
