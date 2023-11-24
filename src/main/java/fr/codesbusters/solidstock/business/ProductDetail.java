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

    private Integer id;

    private Integer productId;

    private Integer OrderFormId;

    private Integer invoiceId;

    private Integer estimateId;

    private Integer quantity;

    private Date createdAt;

    private Date updatedAt;

}
