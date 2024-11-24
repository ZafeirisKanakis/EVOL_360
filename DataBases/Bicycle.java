import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Bicycle {

    public int category_id;
    public String pedal_type;

    public Bicycle(int vehicle_id , String pedal_type){
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            String insertBicycle = "INSERT INTO Bicycle (vehicle_id, pedal_type) VALUES (?, ?)";

            assert(!(connection==null));
            preparedStatement = connection.prepareStatement(insertBicycle);

            preparedStatement.setInt(1, vehicle_id);
            preparedStatement.setString(2, pedal_type);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Bicycle inserted successfully!");
            } else {
                System.out.println("No rows affected. Bicycle not inserted.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Bicycle Insertion Error !");
        }
    }

}
