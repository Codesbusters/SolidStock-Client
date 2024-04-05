package fr.codesbusters.solidstock.dto.product;

import fr.codesbusters.solidstock.dto.productFamily.GetProductFamilyDto;
import fr.codesbusters.solidstock.dto.quantityType.GetQuantityTypeDto;
import fr.codesbusters.solidstock.dto.supplier.GetSupplierDto;
import fr.codesbusters.solidstock.dto.vat.GetVatDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductDto {
    private int id;
    private String name;
    private String description;
    private GetSupplierDto supplier;
    private GetProductFamilyDto productFamily;
    private GetVatDto vat;
    private GetQuantityTypeDto quantityType;
    private String barCode;
    private String sellPrice;
    private String buyPrice;
    private String supplierName;
    private boolean isDeleted;
    private int minimumStockQuantity;
    private List<String> stockMovements;
    private List<Integer> stocks;
    private List<Integer> deliveryRows;
    private List<Integer> estimateRows;
    private List<Integer> invoiceRows;
    private List<Integer> orderFormRows;
    private String createdAt;
    private String updatedAt;
    private boolean deleted;
}
