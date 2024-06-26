package fr.codesbusters.solidstock.client.dto.role;

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
public class GetRoleDto {
    private int id;
    private String name;
    private List<Integer> users;
    private boolean isDisabled;
    private String createdAt;
    private String updatedAt;
}
