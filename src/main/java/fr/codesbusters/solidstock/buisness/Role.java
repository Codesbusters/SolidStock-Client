package fr.codesbusters.solidstock.buisness;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Role {

    private Integer id;

    private String name;

    private Date createdAt;

    private Date updatedAt;
}
