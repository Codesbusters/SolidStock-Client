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
public class OrderForm {

    private Integer id;

    private String subject;

    private String description;

    private Integer customer;

    private Date dueDate;

    private Integer estimateId;

    private Date createdAt;

    private Date updatedAt;

}
