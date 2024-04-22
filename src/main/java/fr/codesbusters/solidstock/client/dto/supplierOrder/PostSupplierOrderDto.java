package fr.codesbusters.solidstock.client.dto.supplierOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSupplierOrderDto {
    private String supplierId;
    private String orderNumber;
    private String orderDate;
    private String deliveryDate;
    private String status;
}
