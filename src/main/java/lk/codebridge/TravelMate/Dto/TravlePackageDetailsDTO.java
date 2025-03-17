package lk.codebridge.TravelMate.Dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravlePackageDetailsDTO {

    private int id;

    private String packageName;

    private String descOne;

    private String descTwo;

    private double pricePerPerson;

    private int days;

    private int nights;

    private String wikiUrl;

    private String city;

    private int status;

    private String resource;

    private String location;


    public TravlePackageDetailsDTO(int id, String packageName, String descOne, String descTwo, double pricePerPerson,
            int days, int nights, String wikiUrl, String city, int status, String resource,String location) {
        this.id = id;
        this.packageName = packageName;
        this.descOne = descOne;
        this.descTwo = descTwo;
        this.pricePerPerson = pricePerPerson;
        this.days = days;
        this.nights = nights;
        this.wikiUrl = wikiUrl;
        this.city = city;
        this.status = status;
        this.resource = resource;
        this.location = location;
    }



    @Override
    public String toString() {
        return "TravlePackageDetailsDTO [packageName=" + packageName + ", descOne=" + descOne + ", descTwo=" + descTwo
                + ", pricePerPerson=" + pricePerPerson + ", days=" + days + ", nights=" + nights + ", wikiUrl="
                + wikiUrl + ", city=" + city + ", resource=" + resource + "]";
    }

}
