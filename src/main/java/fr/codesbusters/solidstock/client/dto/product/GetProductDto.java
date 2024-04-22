package fr.codesbusters.solidstock.client.dto.product;

import fr.codesbusters.solidstock.client.dto.productFamily.GetProductFamilyDto;
import fr.codesbusters.solidstock.client.dto.quantityType.GetQuantityTypeDto;
import fr.codesbusters.solidstock.client.dto.supplier.GetSupplierDto;
import fr.codesbusters.solidstock.client.dto.vat.GetVatDto;
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
    private double inStock;
    private double selled;
    private boolean isDeleted;
    private int minimumStockQuantity;
    private String createdAt;
    private String updatedAt;
    private boolean deleted;
}
