package fr.codesbusters.solidstock.buisness;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Invoice {

    private Integer id;

    private String subject;

    private Integer customerId;

    private Integer estimateId;

    private Date createdAt;

    private Date updatedAt;
}
