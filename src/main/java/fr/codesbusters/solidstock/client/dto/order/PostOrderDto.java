package fr.codesbusters.solidstock.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostOrderDto {
    private String name;
    private String description;
    private long customerId;
    private String estimateDate;
    private String status;
}