package Model;

import java.sql.Date;
import java.sql.Time;

public class AudioBook implements Model{
    private static String[] fields = {"ISBN", "title", "narrator", "running_time", "age_rating", "purchase_price", "publisher_name", "published_date"};
    private String ISBN;
    private String title;
    private String narrator;
    private Time runningTime;
    private Integer ageRating;
    private Float purchasePrice;
    private String publisherName;
    private Date publishedDate;

    public AudioBook(String ISBN, String title, String narrator, Time runningTime, Integer ageRating, Float purchasePrice, String publisherName, Date publishedDate){
        this.ISBN = ISBN;
        this.title = title;
        this.narrator = narrator;
        this.runningTime = runningTime;
        this.ageRating = ageRating;
        this.purchasePrice = purchasePrice;
        this.publishedDate = publishedDate;
        this.publisherName = publisherName;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public Integer getAgeRating() {
        return ageRating;
    }

    public String getNarrator() {
        return narrator;
    }

    public Float setPurchasePrice() {
        return purchasePrice;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public static String[] getFields(){
        return fields;
    }

    public String[] toStringArrayFormat(){
        String ageRatingString = ageRating.toString();
        if(ageRating == 0){
            ageRatingString = "No age rating";
        }
        String[] stringArrayFormat = {ISBN, title, narrator, runningTime.toString(), ageRatingString, purchasePrice.toString(), publisherName, publishedDate.toString()};
        return stringArrayFormat;
    }
}
