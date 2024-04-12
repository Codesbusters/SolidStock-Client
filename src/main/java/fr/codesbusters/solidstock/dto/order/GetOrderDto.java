package fr.codesbusters.solidstock.dto.order;

import fr.codesbusters.solidstock.dto.admin.GetAdminSettingsDto;
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
public class GetOrderDto {

    private int id;
    private String name;
    private String description;
    private String estimateDate;
    private String status;
    private GetCustomerDto customer;
    private GetAdminSettingsDto ownerCompany;
    private List<GetOrderRowDto> orderRowDto;
    private String createdAt;
    private String updatedAt;

}