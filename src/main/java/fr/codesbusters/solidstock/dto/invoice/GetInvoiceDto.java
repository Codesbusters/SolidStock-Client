package fr.codesbusters.solidstock.dto.invoice;

import fr.codesbusters.solidstock.dto.customer.GetCustomerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetInvoiceDto {

        private int id;
        private String name;
        private String description;
        private GetCustomerDto customer;
        private List<GetInvoiceRowDto> invoiceRows;
        private double totalHt;
        private double totalTtc;
        private String createdAt;
        private String updatedAt;
}
