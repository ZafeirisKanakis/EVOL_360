import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

enum Type {SUV , Cabrio ,City_car , Van;}

public class Car {

    //public int car_registration_number; //(Primary key)
    public int category_id;
    public int number_of_passengers;
    Type type;

    public Car(int vehicle_id , int number_of_passengers , Type type) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            String insertCar = "INSERT INTO Car (vehicle_id, number_of_passengers, type) VALUES (?, ?, ?)";

            assert(!(connection==null));
            preparedStatement = connection.prepareStatement(insertCar);

            preparedStatement.setInt(1, vehicle_id);
            preparedStatement.setInt(2, number_of_passengers);
            preparedStatement.setString(3, type.name());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Car inserted successfully!");
            } else {
                System.out.println("No rows affected. Data not inserted.");
            }

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Car Insertion Error !");
        }
    }


}
