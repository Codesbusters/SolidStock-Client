package fr.codesbusters.solidstock.dto.vat;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetVatDto {
    @Getter
    private int id;
    private double rate;
    private String description;
    private String percentage;
    private String createdAt;
    private String updatedAt;
    private List<Integer> products;

    public GetVatDto(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static GetVatDto fromId(int id) {
        return new GetVatDto(id);
    }

}
