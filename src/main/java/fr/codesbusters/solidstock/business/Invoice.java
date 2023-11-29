package fr.codesbusters.solidstock.business;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Invoice {

    private int id;

    private String subject;

    private int customerId;

    private int estimateId;

}
