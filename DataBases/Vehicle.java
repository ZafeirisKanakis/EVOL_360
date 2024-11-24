import java.net.Inet4Address;
import java.sql.*;

public class Vehicle {
    public int vehicle_id;
    public int rental_cost;
    //State ENUM
    public int km_autonomy;
    public int times_rented;
    public String model;
    public int insurance_cost;
    public String color;
    public String brand;
    public int category_id;


    public Vehicle(int rental_cost, int km_autonomy, String model, int insurance_cost, String color, String brand, int category_id, String arg1, int arg2){
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            String insertVehicle = "INSERT INTO Vehicle (rental_cost, state, km_autonomy, times_rented, model, insurance_cost, color, brand, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            assert(!(connection==null));
            preparedStatement = connection.prepareStatement(insertVehicle);

            preparedStatement.setInt(1, rental_cost);
            preparedStatement.setString(2, "to_rent");
            preparedStatement.setInt(3, km_autonomy);
            preparedStatement.setInt(4, 0);
            preparedStatement.setString(5, model);
            preparedStatement.setInt(6, insurance_cost);
            preparedStatement.setString(7, color);
            preparedStatement.setString(8, brand);
            preparedStatement.setInt(9, category_id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Vehicle inserted successfully!");
            } else {
                System.out.println("No rows affected. Data not inserted.");
            }
//            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Vehicle insertion Error");
        }
        /*After Inserting the vehicle, insert the vehicle to the necessary category as well*/
        try{
            int vehicle_id = getLatestVehicleId();
            switch (category_id){
                case 1:
                    /*The data for the constructor will be parsed from the GUI*/
                    if(arg1.equals("SUV")) {
                        Car car = new Car(vehicle_id, arg2, Type.SUV);
                    }else if(arg1.equals("Van")){
                        Car car = new Car(vehicle_id, arg2, Type.Van);
                    }else if(arg1.equals("Cabrio")){
                        Car car = new Car(vehicle_id, arg2, Type.Cabrio);
                    }else{
                        Car car = new Car(vehicle_id, arg2, Type.City_car);
                    }
                    break;
                case 2:
                    if(arg1.equals("True")) {
                        MotorCycle motorCycle = new MotorCycle(vehicle_id, true);
                    }else {
                        MotorCycle motorCycle = new MotorCycle(vehicle_id, false);
                    }
                    break;
                case 3:
                    Scooter scooter = new Scooter(vehicle_id , arg2);
                    break;
                case 4:
                    Bicycle bicycle = new Bicycle(vehicle_id , arg1);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Category Insertion Error !");
        }
    }

    public static int getLatestVehicleId() {
        int latestVehicleId = -1; // Initialize with a default value

        try {
            Connection con;
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Statement statement = con.createStatement();

            // SQL query to get the maximum vehicle_id from the Vehicle table
            String query = "SELECT MAX(vehicle_id) AS latest_vehicle_id FROM Vehicle";
            ResultSet resultSet = statement.executeQuery(query);

            // Check if there is a result
            if (resultSet.next()) {
                latestVehicleId = resultSet.getInt("latest_vehicle_id");
            }

            // Close the result set and the statement
            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println("Error getting latest vehicle_id: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return latestVehicleId;
    }

    public void Delete_Vehicle(int vehicle_id){
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            String deleteVehicle = "DELETE FROM Vehicle WHERE vehicle_id = " + vehicle_id + ";";

            assert(!(connection==null));
            preparedStatement = connection.prepareStatement(deleteVehicle);



            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Vehicle deleted successfully!");
            } else {
                System.out.println("No rows affected. Data not inserted.");
            }
//            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Vehicle Deletion Error !");
        }


    }
}