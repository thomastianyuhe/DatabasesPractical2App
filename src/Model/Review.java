package Model;

public class Review implements Model{
    private static String[] fields = {"customer", "ISBN", "rating", "title", "comment", "verified"};
    private String customer;
    private String ISBN;
    private Integer rating;
    private String title;
    private String comment;
    private Boolean verified;

    public Review(String customer, String ISBN, Integer rating, String title, String comment, Boolean verified){
        this.customer = customer;
        this.ISBN = ISBN;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.verified = verified;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getCustomerID() {
        return customer;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Boolean getVerified() {
        return verified;
    }

    public static String[] getFields(){
        return fields;
    }

    public String[] toStringArrayFormat(){
        String[] stringArrayFormat = {customer, ISBN.toString(), rating.toString(), title.toString(), comment, verified.toString()};
        return stringArrayFormat;
    }
}

