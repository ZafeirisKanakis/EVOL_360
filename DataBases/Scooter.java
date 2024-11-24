import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Scooter {

    public int category_id;
    public int top_speed;

    public Scooter(int vehicle_id , int top_speed){
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            String insertScooter = "INSERT INTO Scooter (vehicle_id, top_speed) VALUES (?, ?)";

            assert(!(connection==null));
            preparedStatement = connection.prepareStatement(insertScooter);

            preparedStatement.setInt(1, vehicle_id);
            preparedStatement.setInt(2, top_speed);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Scooter inserted successfully!");
            } else {
                System.out.println("No rows affected. Data not inserted.");
            }

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Scooter Insertion Error !");
        }
    }


}
