package fr.codesbusters.solidstock.dto.vat;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetVatDto {

    private int id;
    private double rate;
    private String description;
    private String percentage;
    private String createdAt;
    private String updatedAt;

}
