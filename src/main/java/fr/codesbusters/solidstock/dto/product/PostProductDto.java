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
public class PostProductDto {
    private String name;
    private String description;
    private String barCode;
    private String buyPrice;
    private String sellPrice;
    private double minimumStockQuantity;
    private int supplierId;
    private int vatId;
    private int quantityTypeId;
    private int productFamilyId;
}