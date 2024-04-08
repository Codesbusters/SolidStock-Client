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
    private String name;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String confirmPassword;
    private String phone;
    private GetCustomerDto customer;
    private GetRoleDto role;
    private List<Integer> customers;
    private boolean isDeleted;
    private String createdAt;
    private String updatedAt;
}