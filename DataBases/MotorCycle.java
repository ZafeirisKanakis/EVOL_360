import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MotorCycle {
    public int category_id;
    public boolean off_road;


    public MotorCycle(int vehicle_id , boolean off_road){
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            String insertMotorCycle = "INSERT INTO Motorcycle (vehicle_id, off_road) VALUES (?, ?)";

            assert(!(connection==null));
            preparedStatement = connection.prepareStatement(insertMotorCycle);

            preparedStatement.setInt(1, vehicle_id);
            preparedStatement.setBoolean(2, off_road);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("MotorCycle inserted successfully!");
            } else {
                System.out.println("No rows affected. MotorCycle not inserted.");
            }

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("MotorCycle Insertion Error !");
        }
    }

}
