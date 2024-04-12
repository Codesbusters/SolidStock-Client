package fr.codesbusters.solidstock.dto.user;

import fr.codesbusters.solidstock.dto.customer.GetCustomerDto;
import fr.codesbusters.solidstock.dto.role.GetRoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDto {
    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private GetCustomerDto customer;
    private List<GetRoleDto> roles;
    private boolean isDeleted;
    private String createdAt;
    private String updatedAt;
}