package fr.codesbusters.solidstock.dto.product;

import fr.codesbusters.solidstock.dto.productFamily.GetProductFamilyDto;
import fr.codesbusters.solidstock.dto.quantityFamily.GetQuantityFamilyDto;
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
    private int productFamily;
    private int supplier;
    private int vat;
    private int quantityType;
    private String barCode;
    private double sellPrice;
    private double buyPrice;
    private boolean isDeleted;
    private double minimumStockQuantity;
    private List<String> stockMovements;
    private List<Integer> stocks;
    private List<Integer> estimateRows;
    private List<Integer> invoiceRows;
    private List<Integer> orderFormRows;
    private List<Integer> deliveryRows;
    private String createdAt;
    private String updatedAt;
    private boolean deleted;
}
