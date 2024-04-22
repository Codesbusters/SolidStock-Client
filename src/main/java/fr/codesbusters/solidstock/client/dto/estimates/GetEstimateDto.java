package fr.codesbusters.solidstock.client.dto.estimates;

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
public class GetEstimateDto {
    private int id;
    private String name;
    private String description;
    private GetCustomerDto customer;
    private GetAdminSettingsDto ownerCompany;
    private List<GetEstimateRowDto> estimateRows;
    private double totalHt;
    private double totalTtc;
    private String createdAt;
    private String updatedAt;
}
