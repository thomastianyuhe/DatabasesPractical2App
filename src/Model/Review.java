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

    public static String[] getFields(){
        return fields;
    }

    public String[] toStringArrayFormat(){
        String[] stringArrayFormat = {customer, ISBN.toString(), rating.toString(), title.toString(), comment, verified.toString()};
        return stringArrayFormat;
    }
}

