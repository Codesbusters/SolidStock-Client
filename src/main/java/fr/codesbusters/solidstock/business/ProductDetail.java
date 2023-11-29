package fr.codesbusters.solidstock.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {

    private int id;

    private int productId;

    private int OrderFormId;

    private int invoiceId;

    private int estimateId;

    private int quantity;





}
