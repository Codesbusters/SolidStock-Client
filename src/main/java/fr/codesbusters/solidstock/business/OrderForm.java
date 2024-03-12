package fr.codesbusters.solidstock.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    private LocalDate dueDate;

    private int estimateId;

    private int statusId;

    private String statusName;
}