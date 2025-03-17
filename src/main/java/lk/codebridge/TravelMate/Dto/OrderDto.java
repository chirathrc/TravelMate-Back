package lk.codebridge.TravelMate.Dto;

import lombok.Data;

@Data
public class OrderDto {

    private String userEmail;
    private String packageId;
    private String date;
    private String persons;
}
