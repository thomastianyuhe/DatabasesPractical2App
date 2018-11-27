package Model;

import java.sql.Date;

public class Author implements Model{
    private static String[] fields = {"full name", "date of birth", "biography"};
    private String fullName;
    private Date dateOfBirth;
    private String biography;

    public Author(String fullname, Date dateOfBirth, String biography){
        this.fullName = fullname;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBiography() {
        return biography;
    }

    public static String[] getFields() {
        return fields;
    }

    public String[] toStringArrayFormat(){
        String[] stringArrayFormat = {fullName, dateOfBirth.toString(), biography};
        return stringArrayFormat;
    }
}
