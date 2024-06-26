package fr.codesbusters.solidstock.client.dto.order;

import fr.codesbusters.solidstock.client.dto.admin.GetAdminSettingsDto;
import fr.codesbusters.solidstock.client.dto.customer.GetCustomerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderDto {

    private long id;
    private String name;
    private String description;
    private String estimateDate;
    private String status;
    private GetCustomerDto customer;
    private GetAdminSettingsDto ownerCompany;
    private List<GetOrderRowDto> orderFormRows;
    private String createdAt;
    private String updatedAt;

}