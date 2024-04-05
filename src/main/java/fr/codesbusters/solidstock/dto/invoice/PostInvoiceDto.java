package fr.codesbusters.solidstock.dto.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostInvoiceDto {
    private String name;
    private String description;
    private int customerId;
}
