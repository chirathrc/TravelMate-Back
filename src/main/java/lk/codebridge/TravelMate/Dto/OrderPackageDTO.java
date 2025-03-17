package lk.codebridge.TravelMate.Dto;

import java.sql.Date;

import lombok.Data;

@Data
public class OrderPackageDTO {

    private String packageName;

    private String pricePP;

    private String persons;

    private Date startDate;

    private Date endDate;

}
