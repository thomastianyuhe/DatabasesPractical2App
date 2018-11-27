package Model;

import java.sql.Date;

public class PurchaseRecord implements Model{
    private static String[] fields = {"ISBN", "title", "purchase price ($)", "purchase date"};
    private String ISBN;
    private String title;
    private Float purchasePrice;
    private Date purchaseDate;

    public PurchaseRecord(String ISBN, String title, float purchasePrice, Date purchaseDate){
        this.ISBN = ISBN;
        this.title = title;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
    }

    public static String[] getFields(){
        return fields;
    }

    public String[] toStringArrayFormat(){
        String[] stringArrayFormat = {ISBN, title, purchasePrice.toString(), purchaseDate.toString()};
        return stringArrayFormat;
    }
}
