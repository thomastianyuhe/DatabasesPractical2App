package Model;

import java.sql.Date;

public class Publisher implements Model{
    private String name;
    private String building;
    private String street;
    private String city;

    private String country;
    private String postcode;
    private String phoneNumber;
    private Date establishedDate;
    private static String[] fields = {"name", "building", "street", "city", "country", "postcode", "phone number", "established date"};

    public Publisher(String name, String building, String street, String city, String country, String postcode, String phoneNumber, Date establishedDate){
        this.name = name;
        this.building = building;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postcode = postcode;
        this.phoneNumber = phoneNumber;
        this.establishedDate = establishedDate;
    }

    public String getName() {
        return name;
    }

    public String getBuilding() {
        return building;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public Date getEstablishedDate() {
        return establishedDate;
    }

    public static String[] getFields(){
        return fields;
    }

    public String[] toStringArrayFormat(){
        String[] stringArrayFormat = {name, building, street, city, country, postcode, phoneNumber, establishedDate.toString()};
        return stringArrayFormat;
    }
}
