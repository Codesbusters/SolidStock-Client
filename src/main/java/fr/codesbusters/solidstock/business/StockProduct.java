package fr.codesbusters.solidstock.business;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class StockProduct {

    private Integer id;

    private Integer productId;

    private Integer locationId;

    private Date createdAt;

    private Date updatedAt;

    private Date selledAt;
}
