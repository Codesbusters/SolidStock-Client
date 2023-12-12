package fr.codesbusters.solidstock.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderForm {

    private int id;

    private String subject;

    private String description;

    private int customerId;

    private String customerName;

    private DateTime dueDate;

    private int estimateId;

    private int statusId;

    private String statusName;
}
