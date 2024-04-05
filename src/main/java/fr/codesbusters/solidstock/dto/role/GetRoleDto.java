package fr.codesbusters.solidstock.dto.role;

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
public class GetRoleDto {
    private int id;
    private String name;
    private List<Integer> users;
    private String createdAt;
    private String updatedAt;
}
