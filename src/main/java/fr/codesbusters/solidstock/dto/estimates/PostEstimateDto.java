package fr.codesbusters.solidstock.dto.estimates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostEstimateDto {
    private String name;
    private String description;
    private int customerId;
}
