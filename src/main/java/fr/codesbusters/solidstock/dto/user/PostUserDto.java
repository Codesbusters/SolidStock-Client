package fr.codesbusters.solidstock.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserDto {
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private long customerId;
}