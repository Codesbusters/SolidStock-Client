package fr.codesbusters.solidstock.business;

import lombok.*;
import org.joda.time.DateTime;

import java.beans.Transient;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class StockProduct {

    private int id;

    private int productId;

    private int locationId;

    private DateTime selledAt;
}
