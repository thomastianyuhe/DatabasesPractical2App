import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;


public class Main {
    static JFrame f;
    Main(){
        f=new JFrame();
    }
    static final String DB_URL = "jdbc:mariadb://th61.host.cs.st-andrews.ac.uk/th61_cs3101_test1_db";
    //  Database credentials
    static final String USER = "th61";
    static final String PASSWORD = "v5e3V.Y0Qf1MKq";

    public static void main(String[] args) {
        JTable jt;
        ArrayList<ArrayList<Object>> audioBookTable = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
//            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            System.out.println(DB_URL+"?"+"user="+ USER + "&password="+PASSWORD);
            conn = DriverManager.getConnection(DB_URL+"?"+"user="+ USER + "&password="+PASSWORD);
            stmt = conn.createStatement();

            String sql = "SELECT * from audiobook;";
            ResultSet audioBookResults  = stmt.executeQuery(sql);
            while(audioBookResults.next()){
                //Retrieve by column name
                ArrayList<Object> row = new ArrayList<>();
                String id  = audioBookResults.getString("ISBN");
                row.add(id);
                String title = audioBookResults.getString("title");
                row.add("title");
                int narratorId = audioBookResults.getInt("narrator_id");
                row.add(narratorId);
                String runningTime = audioBookResults.getTime("running_time").toString();
                row.add(runningTime);
                int ageRating = audioBookResults.getInt("age_rating");
                row.add(ageRating);
                float purchasePrice = audioBookResults.getFloat("purchase_price");
                row.add(purchasePrice);
                String publisherName = audioBookResults.getString("publisher_name");
                row.add(publisherName);
                String publishedDate = audioBookResults.getDate("published_date").toString();
                row.add(publishedDate);
                audioBookTable.add(row);
                //Display values
            }
            audioBookResults.close();
            System.out.println("Database created successfully...");

            Object[][] audioBookTableArray = new Object[audioBookTable.size()][audioBookTable.get(0).size()];
            for(int i=0; i<audioBookTable.size(); i++){
                audioBookTableArray[i] = audioBookTable.get(i).toArray();
            }
            String[] column ={"ISBN", "title", "narrator_id", "running_time", "age_rating", "purchase_price", "publisher_name", "published_date"};
            jt=new JTable(audioBookTableArray,column);
            jt.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent mouseEvent) {
                    JTable table =(JTable) mouseEvent.getSource();
                    Point point = mouseEvent.getPoint();
                    int row = table.rowAtPoint(point);
                    if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {

                    }
                }
            });
//            jt.setBounds(30,40,200,300);
            JScrollPane sp=new JScrollPane(jt);
            f.add(sp);
            f.setSize(300,400);
            f.setVisible(true);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        new Main();
    }//end main
}