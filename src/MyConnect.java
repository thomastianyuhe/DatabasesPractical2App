import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;


public class MyConnect {

    static final String DB_URL = "jdbc:mysql://th61.host.cs.st-andrews.ac.uk:3306/th61_cs3101_test1_db";
    static final String USERNAME = "th61";
    static final String PASSWORD = "v5e3V.Y0Qf1MKq";

    public static Connection makeConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            System.out.println(DB_URL+"?"+"user="+ USERNAME + "&password="+PASSWORD);
            connection = DriverManager.getConnection(DB_URL+"?"+"user="+ USERNAME + "&password="+PASSWORD);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public static ArrayList<Model> getAllAudioBooks(){
        ArrayList<Model> audioBooks = new ArrayList<>();
        Connection connection = makeConnection();
        Statement statement = null;
        try{
             statement = connection.createStatement();
            String sql = "select\n" +
                    "ISBN,\n" +
                    "title,\n" +
                    "concat(forename, ' ', coalesce(concat(middle_initials, ' '), ''), surname) as narrator,\n" +
                    "running_time,\n" +
                    "age_rating,\n" +
                    "purchase_price,\n" +
                    "publisher_name,\n" +
                    "published_date\n" +
                    "from\n" +
                    "person,\n" +
                    "audiobook where person.ID = narrator_id;";
            ResultSet audioBookResults  = statement.executeQuery(sql);
            while(audioBookResults.next()){
                //Retrieve by column name
                String ISBN  = audioBookResults.getString("ISBN");
                String title = audioBookResults.getString("title");
                String narrator = audioBookResults.getString("narrator");
                Time runningTime = audioBookResults.getTime("running_time");
                int ageRating = audioBookResults.getInt("age_rating");
                float purchasePrice = audioBookResults.getFloat("purchase_price");
                String publisherName = audioBookResults.getString("publisher_name");
                Date publishedDate = audioBookResults.getDate("published_date");
                AudioBook audioBook = new AudioBook(ISBN, title, narrator, runningTime, ageRating, purchasePrice, publisherName, publishedDate);
                audioBooks.add(audioBook);
            }
            audioBookResults.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            try{
                if(connection !=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return audioBooks;
    }

    public static ArrayList<Model> getReviewsForCertainAudioBook(String ISBN){
        ArrayList<Model> reviews = new ArrayList<>();
        Connection connection = makeConnection();
        PreparedStatement statement = null;
        try{
            String sql = "select\n" +
                    "concat(forename, ' ', coalesce(concat(middle_initials, ' '), ''), surname) as customer,\n" +
                    "ISBN,\n" +
                    "title,\n" +
                    "rating,\n" +
                    "comment,\n" +
                    "verified\n" +
                    "from\n" +
                    "person,\n" +
                    "audiobook_reviews\n" +
                    "where audiobook_reviews.ISBN = ? and person.ID = customer_ID;";
            statement = connection.prepareStatement(sql);
            statement.setString(1, ISBN);
            ResultSet reviewResults  = statement.executeQuery();
            while(reviewResults.next()){
                String title = reviewResults.getString("title");
                String customer = reviewResults.getString("customer");
                Integer rating = reviewResults.getInt("rating");
                String comment = reviewResults.getString("comment");
                Boolean verified = reviewResults.getBoolean("verified");
                Review review = new Review(customer, ISBN, rating, title, comment, verified);
                reviews.add(review);
            }
            reviewResults.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            try{
                if(connection !=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return reviews;
    }

    public static ArrayList<Model> getPublisherForCertainAudioBook(String publisherName){
        ArrayList<Model> publishers = new ArrayList<>();
        Connection connection = makeConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("SELECT * FROM publisher WHERE publisher.name = ?;");
            statement.setString(1, publisherName);
            ResultSet publisherResults  = statement.executeQuery();
            while(publisherResults.next()){
                String building = publisherResults.getString("building");
                String street = publisherResults.getString("street");
                String city = publisherResults.getString("city");
                String country = publisherResults.getString("country");
                String postcode = publisherResults.getString("postcode");
                String phoneNumber = publisherResults.getString("phone_number");
                Date establishedDate = publisherResults.getDate("established_date");
                Publisher publisher = new Publisher(publisherName,building, street, city, country, postcode, phoneNumber, establishedDate);
                publishers.add(publisher);
            }
            publisherResults.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            try{
                if(connection !=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return publishers;
    }

    public static ArrayList<Model> getAuthorForCertainAudioBook(String ISBN){
        ArrayList<Model> authors = new ArrayList<>();
        Connection connection = makeConnection();
        PreparedStatement statement = null;
        try{
            String sql = "select\n" +
                    "concat(forename, ' ', coalesce(concat(middle_initials, ' '), ''), surname) as full_name,\n" +
                    "date_of_birth,\n" +
                    "biography\n" +
                    "from\n" +
                    "person,\n" +
                    "audiobook_authors,\n" +
                    "contributor\n" +
                    "where person.ID = audiobook_authors.contributor_ID and audiobook_authors.ISBN = ? " +
                    "and audiobook_authors.contributor_ID = contributor.person_ID;\n";
            statement = connection.prepareStatement(sql);
            statement.setString(1, ISBN);
            ResultSet authorResults  = statement.executeQuery();
            while(authorResults.next()){
                String fullName = authorResults.getString("full_name");
                Date dateOfBirth = authorResults.getDate("date_of_birth");
                String biography = authorResults.getString("biography");
                Author author = new Author(fullName, dateOfBirth, biography);
                authors.add(author);
            }
            authorResults.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            try{
                if(connection !=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return authors;
    }

    public static String getCustomerPassword(String username){
        ArrayList<String> passwordList = new ArrayList<>();
        Connection connection = makeConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("SELECT password FROM customer WHERE email_address = ?;");
            statement.setString(1, username);
            ResultSet passwordResult  = statement.executeQuery();
            while(passwordResult.next()){
                passwordList.add(passwordResult.getString("password"));
            }
            passwordResult.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            try{
                if(connection !=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        if(passwordList.size() != 1){
            //should only have one password
            return null;
        }
        return passwordList.get(0);
    }

    public static int getCustomerId(String username){
        ArrayList<Integer> customerIdList = new ArrayList<>();
        Connection connection = makeConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("SELECT person_ID FROM customer WHERE email_address = ?;");
            statement.setString(1, username);
            ResultSet passwordResult  = statement.executeQuery();
            while(passwordResult.next()){
                customerIdList.add(passwordResult.getInt("person_ID"));
            }
            passwordResult.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            try{
                if(connection !=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        if(customerIdList.size() != 1){
            //should only have one password
            return -1;
        }
        return customerIdList.get(0);
    }

    public static ArrayList<Model> getPurchaseRecordForCustomer(int id){
        ArrayList<Model> purchaseRecordList = new ArrayList<>();
        Connection connection = makeConnection();
        PreparedStatement statement = null;
        try{
            String sql = "select ISBN, title, purchase_price, purchase_date from audiobook natural join audiobook_purchases where customer_ID = ?;";
            statement = connection.prepareStatement(sql);
            statement.setString(1, Integer.toString(id));
            ResultSet purchaseRecordResult  = statement.executeQuery();
            while(purchaseRecordResult.next()){
                //Retrieve by column name
                String ISBN  = purchaseRecordResult.getString("ISBN");
                String title = purchaseRecordResult.getString("title");
                float purchasePrice = purchaseRecordResult.getFloat("purchase_price");
                Date purchaseDate = purchaseRecordResult.getDate("purchase_date");
                PurchaseRecord purchaseRecord = new PurchaseRecord(ISBN, title, purchasePrice, purchaseDate);
                purchaseRecordList.add(purchaseRecord);
            }
            purchaseRecordResult.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            try{
                if(connection !=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return purchaseRecordList;
    }

}