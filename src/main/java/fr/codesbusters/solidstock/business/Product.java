package fr.codesbusters.solidstock.business;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private String name;
    private String description;
    private double sellPrice;
    private double buyPrice;
    private double vat;
    private int supplierId;
    private int productFamilyId;
    private int quantityTypeId;
    private String image;
}
