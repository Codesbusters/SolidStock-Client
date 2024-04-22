package fr.codesbusters.solidstock.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostOrderRowDto {
    private double quantity;
    private int productId;
}