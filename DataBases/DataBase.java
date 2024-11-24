import javax.swing.*;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DataBase {
    public static enum ServiceType {Damage, Accident, Maintainance;}

    private static Connection con;

    public static void initialize() {
        String url = new String("jdbc:mysql://localhost");
        String databaseName = new String("EVOL");
        int port = 3306;
        String username = new String("root");
        String password = new String(""); //Για σύνδεση στη βάση χωρίς κωδικό
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    url + ":" + port + "/" + databaseName + "?characterEncoding=UTF-8", username, password);

            Statement statement = con.createStatement();

            String createTableSQL = "CREATE TABLE IF NOT EXISTS Category (" +
                    "category_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "category_name ENUM ('car', 'motorcycle', 'scooter', 'bicycle'))";
            statement.executeUpdate(createTableSQL);

            // SQL statement to create a table
            createTableSQL = "CREATE TABLE IF NOT EXISTS Vehicle (" +
                    "vehicle_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "rental_cost INT," +
                    "state ENUM ('to_rent', 'rented', 'in_service', 'to_fix')," +
                    "km_autonomy INT," +
                    "times_rented INT," +
                    "model VARCHAR(255)," +
                    "insurance_cost INT," +
                    "color VARCHAR(255)," +
                    "brand VARCHAR(255)," +
                    "category_id INT," +
                    "FOREIGN KEY (category_id) REFERENCES Category(category_id))";
            statement.executeUpdate(createTableSQL);

            createTableSQL = "CREATE TABLE IF NOT EXISTS Customer (" +
                    "customer_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "customer_name VARCHAR(255)," +
                    "license_number INT," +
                    "customer_address VARCHAR(255)," +
                    "phone_number INT," +
                    "birth_date VARCHAR(255)," +
                    "card_number INT," +
                    "card_holder VARCHAR(255)," +
                    "card_expiration VARCHAR(255)," +
                    "card_cvv INT)";
            statement.executeUpdate(createTableSQL);

            createTableSQL = "CREATE TABLE IF NOT EXISTS Rental (" +
                    "rental_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "rental_date DATE," +
                    "rental_deadline DATE," +
                    "rental_duration INT," +
                    "total_cost INT," +
                    "vehicle_id INT," +
                    "category_id INT," +
                    "customer_id INT," +
                    "FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)," +
                    "FOREIGN KEY (category_id) REFERENCES Category(category_id)," +
                    "FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)," +
                    "drivers_license_number VARCHAR(255)," +
                    "birth_date VARCHAR(255))";
            statement.executeUpdate(createTableSQL);

            createTableSQL = "CREATE TABLE IF NOT EXISTS Service (" +
                    "service_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "start_date DATE," +
                    "end_date DATE," +
                    "service_cost INT," +
                    "vehicle_id INT," +
                    "FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id))";
            statement.executeUpdate(createTableSQL);

            createTableSQL = "CREATE TABLE IF NOT EXISTS Car (" +
                    "car_registration_number INT PRIMARY KEY AUTO_INCREMENT," +
                    "vehicle_id INT," +
                    "number_of_passengers INT," +
                    "type ENUM ('SUV', 'Cabrio', 'City_car', 'Van')," +
                    "FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id))";
            statement.executeUpdate(createTableSQL);

            createTableSQL = "CREATE TABLE IF NOT EXISTS Motorcycle (" +
                    "motorcycle_registration_number INT PRIMARY KEY AUTO_INCREMENT," +
                    "off_road BOOLEAN," +
                    "vehicle_id INT," +
                    "FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id))";
            statement.executeUpdate(createTableSQL);

            createTableSQL = "CREATE TABLE IF NOT EXISTS Scooter (" +
                    "scooter_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "top_speed INT," +
                    "vehicle_id INT," +
                    "FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id))";
            statement.executeUpdate(createTableSQL);

            createTableSQL = "CREATE TABLE IF NOT EXISTS Bicycle (" +
                    "motorcycle_registration_number INT PRIMARY KEY AUTO_INCREMENT," +
                    "pedal_type VARCHAR(255)," +
                    "vehicle_id INT," +
                    "FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id))";
            statement.executeUpdate(createTableSQL);

        } catch (Exception e) {
            System.out.println("Den douleuei bro");
            throw new RuntimeException(e);
        }
    }

    public static String getListOfVehicles(String List , int vehicle_id , int category_id) {
        try {
            switch (category_id) {
                case 1:
                    String cars = "SELECT * FROM Car WHERE vehicle_id = " + vehicle_id;
                    PreparedStatement preparedStatement1 = con.prepareStatement(cars);
                    ResultSet resultSet1 = preparedStatement1.executeQuery();

                    while (resultSet1.next()) {
                        String carType = resultSet1.getString("type");
                        String carNumOfPassengers = resultSet1.getString("number_of_passengers");
                        List = List + ", type: " + carType + ", number of passengers : " + carNumOfPassengers;
                    }
                    break;
                case 2:
                    String motorcycles = "SELECT * FROM Motorcycle WHERE vehicle_id = " + vehicle_id;
                    PreparedStatement preparedStatement2 = con.prepareStatement(motorcycles);
                    ResultSet resultSet2 = preparedStatement2.executeQuery();

                    while (resultSet2.next()) {
                        String off_road = resultSet2.getString("off_road");
                        List = List + ", Off Road: " + off_road;
                    }
                    break;
                case 3:
                    String scooters = "SELECT * FROM Scooter WHERE vehicle_id = " + vehicle_id;
                    PreparedStatement preparedStatement3 = con.prepareStatement(scooters);
                    ResultSet resultSet3 = preparedStatement3.executeQuery();

                    while (resultSet3.next()) {
                        String top_speed = resultSet3.getString("top_speed");
                        List = List + ", Top Speed: " + top_speed;

                    }
                    break;
                case 4:
                    String bicycles = "SELECT * FROM Bicycle WHERE vehicle_id = " + vehicle_id;
                    PreparedStatement preparedStatement4 = con.prepareStatement(bicycles);
                    ResultSet resultSet4 = preparedStatement4.executeQuery();

                    while (resultSet4.next()) {
                        String pedal_type = resultSet4.getString("pedal_type");
                        List = List + ", Pedal Type: " + pedal_type;

                    }
                    break;
                default:
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return List;
    }

    public static String GetAllAvailableVehiclesByCat(int category_id) {
        String sql = "SELECT * FROM Vehicle WHERE state = 'to_rent' && category_id = " + category_id;
        String availablevehicles = "";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                int vehicle_id = resultSet.getInt("vehicle_id");
                String model = resultSet.getString("model");
                availablevehicles = availablevehicles + "vehicle_id: " + Integer.toString(vehicle_id) + ", category: " + Integer.toString(category_id) + ", model: " + model;

                availablevehicles = getListOfVehicles(availablevehicles , vehicle_id , category_id);

                // You can print or process the data as needed
                availablevehicles = availablevehicles + "\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return availablevehicles;
    }

    public static String GetAllAvailableVehicles() {
        String sql = "SELECT * FROM Vehicle WHERE state = 'to_rent'";
        String availablevehicles = "";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                int vehicle_id = resultSet.getInt("vehicle_id");
                int category_id = resultSet.getInt("category_id");
                String model = resultSet.getString("model");
                availablevehicles = availablevehicles + "vehicle_id: " + Integer.toString(vehicle_id) + ", category: " + Integer.toString(category_id) + ", model: " + model;

                availablevehicles = getListOfVehicles(availablevehicles , vehicle_id , category_id);

                // You can print or process the data as needed
                availablevehicles = availablevehicles + "\n";
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return availablevehicles;
    }

    public static void UpdateCost(int rental_id, int penalty){
        int total_cost = 0;
        String find_the_rental = "SELECT * FROM Rental WHERE rental_id = " + rental_id;
        try {

            PreparedStatement preparedStatement = con.prepareStatement(find_the_rental);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                total_cost = resultSet.getInt("total_cost");
            }
            total_cost = total_cost + penalty;
            String update = "UPDATE Rental SET total_cost = "+ total_cost +" WHERE rental_id = " + rental_id;
            preparedStatement = con.prepareStatement(update);
            preparedStatement.executeUpdate();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static int ReturnFromRental(int customer_id , int vehicle_id, int actualreturnedDay) {
        String find_the_rental = "SELECT r.* FROM Rental r WHERE vehicle_id = ? AND customer_id = ? " +
                                 "ORDER BY rental_id DESC " +
                                 "LIMIT 1;";
        int rental_duration = 0;
        int rental_id = 0;
        int penalty;

        try {

            PreparedStatement preparedStatement = con.prepareStatement(find_the_rental);

            preparedStatement.setInt(1 , vehicle_id);
            preparedStatement.setInt(2 , customer_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rental_id = resultSet.getInt("rental_id");
                rental_duration = resultSet.getInt("rental_duration");
            }
            String update = "UPDATE Vehicle SET state = 'to_rent' WHERE vehicle_id = ?";
            PreparedStatement preparedStatement2 = con.prepareStatement(update);

            preparedStatement2.setInt(1, vehicle_id);
            preparedStatement2.executeUpdate();

            if (rental_duration == actualreturnedDay) {
                return 0;//epivarinsi tou kostous enoikiasis
            } else {
                penalty = (actualreturnedDay - rental_duration) * 10;
                UpdateCost(rental_id, penalty);
                return (actualreturnedDay - rental_duration) * 10;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static String GetAllRentedVehiclesByCat(int category_id) {
        String sql = "SELECT * FROM Vehicle WHERE state = 'rented' && category_id = " + category_id;
        String rentedVehicles = "";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                int vehicle_id = resultSet.getInt("vehicle_id");
                String model = resultSet.getString("model");
                rentedVehicles = rentedVehicles + "vehicle_id: " + Integer.toString(vehicle_id) + ", category: " + Integer.toString(category_id) + ", model: " + model;
                // If the vehicle is a car, specify the type of the car as well
                rentedVehicles = getListOfVehicles(rentedVehicles , vehicle_id , category_id);
                // You can print or process the data as needed
                rentedVehicles = rentedVehicles + "\n";
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rentedVehicles;
    }

    public static String GetAllRentedVehiclesBy(int customer_id){
        String sql = "SELECT v.* " +
                "FROM Rental r " +
                "JOIN Vehicle v ON r.vehicle_id = v.vehicle_id " +
                "WHERE r.customer_id = ? AND v.state = 'rented'";
        String rentedVehicles = "";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1 , customer_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int vehicle_id = resultSet.getInt("vehicle_id");
                int category_id = resultSet.getInt("category_id");
                String model = resultSet.getString("model");
                rentedVehicles = rentedVehicles + "vehicle_id: " + Integer.toString(vehicle_id) + ", category: " + Integer.toString(category_id) + ", model: " + model;
                // If the vehicle is a car, specify the type of the car as well
                rentedVehicles = getListOfVehicles(rentedVehicles , vehicle_id , category_id);
                // You can print or process the data as needed
                rentedVehicles = rentedVehicles + "\n";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rentedVehicles;
    }

    public static String GetAllRentedVehicles() {
        String sql = "SELECT * FROM Vehicle WHERE state = 'rented'";
        String rentedVehicles = "";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                int vehicle_id = resultSet.getInt("vehicle_id");
                int category_id = resultSet.getInt("category_id");
                String model = resultSet.getString("model");
                rentedVehicles = rentedVehicles + "vehicle_id: " + Integer.toString(vehicle_id) + ", category: " + Integer.toString(category_id) + ", model: " + model;

                rentedVehicles = getListOfVehicles(rentedVehicles , vehicle_id , category_id);
                // You can print or process the data as needed
                rentedVehicles = rentedVehicles + "\n";
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rentedVehicles;
    }

    public static int isValidCustomer(String c){
        PreparedStatement preparedStatement = null;
        int customerId = -1;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            preparedStatement = con.prepareStatement("SELECT customer_id FROM CUSTOMER WHERE license_number = ?");
            assert (!(con == null));
            preparedStatement.setString(1, c);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the result set has a row
            if (resultSet.next()) {
                // Retrieve the customer_id value
                customerId = resultSet.getInt("customer_id");
            }
            System.out.print(customerId);
            return customerId;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean isValidVehicle(int v){
        PreparedStatement preparedStatement = null;
        int vehicle_id = 0;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");
            preparedStatement =  con.prepareStatement("SELECT vehicle_id FROM Vehicle WHERE vehicle_id = ?");
            assert (!(con == null));
            preparedStatement.setInt(1, v);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the result set has a row
            if (resultSet.next()) {
                // Retrieve the customer_id value
                vehicle_id = resultSet.getInt("vehicle_id");
            }
            System.out.print(vehicle_id);
            return (vehicle_id != 0);

        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public static String GetAllVehicles() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String allvehicles = "";
        try {
            String sqlQuery = "SELECT * FROM Vehicle";
            preparedStatement = con.prepareStatement(sqlQuery);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int vehicle_id = resultSet.getInt("vehicle_id");
                int rental_cost = resultSet.getInt("rental_cost");
                String state = resultSet.getString("state");
                int km_autonomy = resultSet.getInt("km_autonomy");
                int times_rented = resultSet.getInt("times_rented");
                String model = resultSet.getString("model");
                int insurance_cost = resultSet.getInt("insurance_cost");
                String color = resultSet.getString("color");
                String brand = resultSet.getString("brand");
                int category_id = resultSet.getInt("category_id");
                allvehicles =allvehicles +  "vehicle_id: " + vehicle_id + ", rental_cost: "+ rental_cost + ", state: "  + state + ", km_autonomy: "+ km_autonomy + ", times_rented: " + times_rented + ", model: " + model + ", insurance_cost: " +insurance_cost + ", color: " + color + ", brand: " + brand + ", category_id: " + category_id;
                if(category_id == 1){
                    String cars = "SELECT * FROM Car WHERE vehicle_id = " + vehicle_id;
                    PreparedStatement preparedStatement2 = con.prepareStatement(cars);
                    ResultSet resultSet2 = preparedStatement2.executeQuery();

                    while (resultSet2.next()) {
                        String carType = resultSet2.getString("type");
                        allvehicles = allvehicles + ", type: " +  carType;
                    }
                }
                allvehicles = allvehicles + "\n";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return allvehicles;
    }

    public static String GetMostFamousOfAllCategories(){
        String mostfamous = "";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int for_cars1, for_motorcycle2, for_scooter3, for_bicycle4;
        int prevfor_cars1 = -1, prevfor_motorcycle2 = -1, prevfor_scooter3 = -1, prevfor_bicycle4 = -1;
        int start = -1;
        String vehicle, car = "", motorcycle = "", scooter = "", bicycle = "";
        try {
            String sqlQuery = "SELECT * FROM Vehicle";
            preparedStatement = con.prepareStatement(sqlQuery);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int vehicle_id = resultSet.getInt("vehicle_id");
                int rental_cost = resultSet.getInt("rental_cost");
                String state = resultSet.getString("state");
                int km_autonomy = resultSet.getInt("km_autonomy");
                int times_rented = resultSet.getInt("times_rented");
                String model = resultSet.getString("model");
                int insurance_cost = resultSet.getInt("insurance_cost");
                String color = resultSet.getString("color");
                String brand = resultSet.getString("brand");
                int category_id = resultSet.getInt("category_id");
                vehicle = "vehicle_id: " + vehicle_id + ", rental_cost: "+ rental_cost + ", state: "  + state + ", km_autonomy: "+ km_autonomy + ", times_rented: " + times_rented + ", model: " + model + ", insurance_cost: " +insurance_cost + ", color: " + color + ", brand: " + brand + ", category_id: " + category_id;
                if(category_id == 1){
                    if(times_rented > prevfor_cars1){
                        for_cars1 = times_rented;
                        prevfor_cars1 = times_rented;
                        car = vehicle;
                        String cars = "SELECT * FROM Car WHERE vehicle_id = " + vehicle_id;
                        PreparedStatement preparedStatement2 = con.prepareStatement(cars);
                        ResultSet resultSet2 = preparedStatement2.executeQuery();

                        while (resultSet2.next()) {
                            String carType = resultSet2.getString("type");
                            car = car + ", type: " +  carType;
                        }
                    }else if(times_rented == prevfor_cars1){
                        car = car + "\n" +vehicle;
                    }

                }else if(category_id == 2){
                    if(times_rented > prevfor_motorcycle2){
                        prevfor_motorcycle2 = times_rented;
                        for_motorcycle2 = times_rented;
                        motorcycle = vehicle;
                    }else if(times_rented == prevfor_motorcycle2){
                        motorcycle = motorcycle + "\n" +vehicle;
                    }
                }else if(category_id == 3){
                    if(times_rented > prevfor_scooter3){
                        prevfor_scooter3 = times_rented;
                        for_scooter3 = times_rented;
                        scooter = vehicle;
                    }else if(times_rented == prevfor_scooter3){
                        scooter = scooter + "\n" +vehicle;
                    }
                }else{
                    if(times_rented > prevfor_bicycle4){
                        prevfor_bicycle4 = times_rented;
                        for_bicycle4 = times_rented;
                        bicycle = vehicle;
                    }else if(times_rented == prevfor_bicycle4){
                        bicycle = bicycle + "\n" +vehicle;
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        mostfamous ="Car: \n" + car + "\nMotorcycle: \n" + motorcycle + "\nScooter: \n" + scooter + "\nBicycle: \n" + bicycle;
        return mostfamous;
    }

    public static String GetAllRentalsBetween(String first ,String last){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder resultStringBuilder = new StringBuilder();
        try {
            String sqlQuery = "SELECT * FROM Rental WHERE rental_date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);

            // Set parameters
            preparedStatement.setString(1, dateFormat.format(Date.valueOf(first)));
            preparedStatement.setString(2, dateFormat.format(Date.valueOf(last)));

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                // Extract rental details
                int rentalId = resultSet.getInt("rental_id");
                Date rentalDate = resultSet.getDate("rental_date");
                int totalCost = resultSet.getInt("total_cost");
                // Add more fields as needed

                // Append details to the result string
                resultStringBuilder.append("rental_id: ").append(rentalId)
                        .append(", rental_date: ").append(rentalDate)
                        .append("\n");
            }
        }catch  (Exception e){
            e.printStackTrace();
        }
        // Print or use the result string as needed
        return resultStringBuilder.toString();

    }

    public static String GetMaxRentalDuration() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String maxRentalDuration = "";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database


            // SQL statement to retrieve the maximum rental duration
            String sqlQuery = "SELECT MAX(rental_duration) AS max_duration FROM Rental";
            preparedStatement = con.prepareStatement(sqlQuery);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Process the result set
            if (resultSet.next()) {
                // Extract the maximum rental duration
                int maxDuration = resultSet.getInt("max_duration");

                // Set the result string
                maxRentalDuration = String.valueOf(maxDuration);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return maxRentalDuration;
    }

    public static String GetMedianRentalDuration(){
        List<Integer> rentalDurations = new ArrayList<>();
        try{
            String sqlQuery = "SELECT rental_duration FROM Rental";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set and populate the list of rental durations
            while (resultSet.next()) {
                int rentalDuration = resultSet.getInt("rental_duration");
                rentalDurations.add(rentalDuration);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Collections.sort(rentalDurations);

        int size = rentalDurations.size();
        double median;
        if (size % 2 == 0) {
            median = (rentalDurations.get(size / 2 - 1) + rentalDurations.get(size / 2)) / 2.0;
        } else {
            median = rentalDurations.get(size / 2);
        }

        // Return the result as a formatted string
        return String.format("%.2f", median);
    }

    public static String GetMinRentalDuration() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String minRentalDuration = "";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database


            // SQL statement to retrieve the maximum rental duration
            String sqlQuery = "SELECT MIN(rental_duration) AS min_duration FROM Rental";
            preparedStatement = con.prepareStatement(sqlQuery);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Process the result set
            if (resultSet.next()) {
                // Extract the maximum rental duration
                int maxDuration = resultSet.getInt("min_duration");

                // Set the result string
                minRentalDuration = String.valueOf(maxDuration);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return minRentalDuration;
    }

    public static int getRentalIdBy(int vehicle_id) {

//        String sql = "SELECT rental_id FROM Rental WHERE vehicle_id = ?";
        String sql = "SELECT r.rental_id " +
                     "FROM Rental r " +
                     "JOIN Vehicle v ON r.vehicle_id = v.vehicle_id " +
                     "WHERE r.vehicle_id = ? AND v.state = 'rented'";

        int result = -1; /*If NOT found*/
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, vehicle_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("rental_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static int getFirstVehicleIdBy(int category_id) {
        String sql = "SELECT vehicle_id FROM Vehicle WHERE category_id = ? AND state = ?";
        int result = -1; /*If NOT found*/
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, category_id);
            preparedStatement.setString(2, "to_rent");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("vehicle_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static int getCategoryIdBy(int vehicle_id) {
        String sql = "SELECT category_id FROM Vehicle WHERE vehicle_id = ?";
        int result = -1; /*If NOT found*/
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, vehicle_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("category_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static void updateDamagedRental(int rental_id, int new_vehicle_id) {
        String sql = "UPDATE Rental SET vehicle_id = ? WHERE rental_id = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, new_vehicle_id);
            preparedStatement.setInt(2, rental_id);

            int updateRowsAffected = preparedStatement.executeUpdate();
            if (updateRowsAffected > 0) {
                System.out.println("Rental_id : " + rental_id + " -> new vehicle is : " + new_vehicle_id);
            } else {
                System.out.println("No rows affected. Rental : " + rental_id + " -> vehicle was not replaced");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sql = "UPDATE Vehicle SET state = 'rented' WHERE vehicle_id = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, new_vehicle_id);

            int updateRowsAffected = preparedStatement.executeUpdate();
            if (updateRowsAffected > 0) {
                System.out.println("Vehicle " + new_vehicle_id + " is now rented");
            } else {
                System.out.println("No rows affected.Vehicle" + new_vehicle_id + " was NOT rented");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static boolean customerHasPaidInsurance(int rental_id , int vehicle_id){

        String rentalCostSQL = "SELECT rental_cost FROM Vehicle WHERE vehicle_id = ?";
        String insuranceCostSQL = "SELECT insurance_cost FROM Vehicle WHERE vehicle_id = ?";
        String totalCostSQL = "SELECT total_cost FROM Rental WHERE rental_id = ?";
        String rental_durationSQL = "SELECT rental_duration FROM Rental WHERE rental_id = ?";

        boolean result = false; /*If NOT found*/

        /*Initial values for debugging*/
        int rental_cost = -1;
        int insurance_cost = -1;
        int total_cost = -1;
        int rental_duration = -1;

        try {
            PreparedStatement preparedStatement1 = con.prepareStatement(rentalCostSQL);
            PreparedStatement preparedStatement2 = con.prepareStatement(insuranceCostSQL);
            PreparedStatement preparedStatement3 = con.prepareStatement(totalCostSQL);
            PreparedStatement preparedStatement4 = con.prepareStatement(rental_durationSQL);

            preparedStatement1.setInt(1, vehicle_id);
            preparedStatement2.setInt(1, vehicle_id);
            preparedStatement3.setInt(1, rental_id);
            preparedStatement4.setInt(1, rental_id);


            /*Get the Rental cost (Per Day)*/
            try (ResultSet resultSet = preparedStatement1.executeQuery()) {
                if (resultSet.next()) {
                    rental_cost = resultSet.getInt("rental_cost");
                }
            }

            /*Get the Insurance cost*/
            try (ResultSet resultSet = preparedStatement2.executeQuery()) {
                if (resultSet.next()) {
                    insurance_cost = resultSet.getInt("insurance_cost");
                }
            }

            /*Get the total cost the customer has paid*/
            try (ResultSet resultSet = preparedStatement3.executeQuery()) {
                if (resultSet.next()) {
                    total_cost = resultSet.getInt("total_cost");
                }
            }

            /*Get the rental_duration that the customer has rented the vehicle for*/
            try (ResultSet resultSet = preparedStatement4.executeQuery()) {
                if (resultSet.next()) {
                    rental_duration = resultSet.getInt("rental_duration");
                }
            }

            if(rental_cost == -1 || insurance_cost == -1 || total_cost == -1 || rental_duration == -1){
                System.out.println("Error while checking for insurance Payment");
                return false;
            }

            if(total_cost == (rental_duration * rental_cost) + insurance_cost){
                /*Customer has paid insurance*/
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /*Customer has not paid Insurance, Just the rental cost for the duration of the rental*/
        return false;
    }

    public static int getTotalCostBy(int rental_id){
        String sql = "SELECT total_cost FROM Rental WHERE rental_id = ?";
        int result = -1; /*If NOT found*/
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, rental_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("total_cost");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /*
    * Returns the penalty the customer has
    * return 0;  --> There is no penalty
    * return -1; --> Error
    * return p>0 --> The amount of the penalty for not paying insurance and getting involved in an accident
    * */
    public static int VehicleService(int vehicle_id, String start_date, String end_date, int service_cost, ServiceType type) {

        Service service;
        int rentalID = getRentalIdBy(vehicle_id);
        int categoryId;
        int newVehicleId;

        switch (type) {
            case Damage:
                service = new Service(Date.valueOf(start_date), Date.valueOf(end_date), service_cost, vehicle_id, "to_fix");

                if (rentalID == -1) return 0 ;

                categoryId = getCategoryIdBy(vehicle_id);
                newVehicleId = getFirstVehicleIdBy(categoryId);

                if (newVehicleId == -1) {
                    System.out.println("Sorry, now equivalent vehicle found to replace");
                    //Return from rental Maybe????
                    return 0;
                }

                updateDamagedRental(rentalID, newVehicleId);
                return 0;

            case Accident:
                if (rentalID == -1) {
                    System.out.println("The Vehicle : " + vehicle_id + " was not rented, therefore it can have been on an accident");
                    return 0;
                }

                service = new Service(Date.valueOf(start_date), Date.valueOf(end_date), service_cost, vehicle_id, "to_fix");
                if(customerHasPaidInsurance(rentalID , vehicle_id)){

                    System.out.println("Customer Has paid Insurance Cost");

                    categoryId = getCategoryIdBy(vehicle_id);
                    newVehicleId = getFirstVehicleIdBy(categoryId);

                    if (newVehicleId == -1) {
                        System.out.println("Sorry, now equivalent vehicle found to replace");
                        return 0;
                    }

                    updateDamagedRental(rentalID, newVehicleId);
                    return 0;
                }else{
                    System.out.println("Customer Has NOT paid Insurance Cost");
                    int total_cost = getTotalCostBy(rentalID);

                    if(total_cost == -1){
                        System.out.println("Error while trying to get total_cost from the database");
                        return 0;
                    }

                    int penalty = total_cost * 3;
                    UpdateCost(rentalID , penalty);

                    System.out.println("Customer Has to pay an additional " + penalty + " euros !!");
                    return penalty;
                }

            case Maintainance:
                service = new Service(Date.valueOf(start_date), Date.valueOf(end_date), service_cost, vehicle_id, "to_service");
                return 0;

            default:
                break;
        }
        return 0;
    }

    public static void VehicleServiceFinished(int vehicle_id) {
        String sql = "UPDATE Vehicle SET state = 'to_rent' WHERE vehicle_id = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, vehicle_id);

            int updateRowsAffected = preparedStatement.executeUpdate();
            if (updateRowsAffected > 0) {
                System.out.println("Vehicle " + vehicle_id + " -> Service is over, Back to being available");
            } else {
                System.out.println("No rows affected.Vehicle" + vehicle_id + " Service is NOT finished");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isSelectQuery(String sqlQuery) {
        // A simple check to determine if the query is a SELECT query
        return sqlQuery.trim().toUpperCase().startsWith("SELECT");
    }

    public static String executeArbitraryQueries(String sql) {
        try {
            // Using CallableStatement to execute arbitrary SQL
            try (CallableStatement callableStatement = con.prepareCall(sql)) {
                if (callableStatement.execute()) {
                    try (ResultSet resultSet = callableStatement.getResultSet()) {
                        StringBuilder result = new StringBuilder();
                        ResultSetMetaData metaData = resultSet.getMetaData();

                        // Append column names
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            result.append(metaData.getColumnName(i)).append("\t");
                        }
                        result.append("\n");

                        // Append data rows
                        while (resultSet.next()) {
                            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                                result.append(resultSet.getString(i)).append("\t");
                            }
                            result.append("\n");
                        }

                        return result.toString();
                    }
                } else {
                    // If the query doesn't return a ResultSet
                    int updateCount = callableStatement.getUpdateCount();
                    return "Query executed successfully. Affected rows: " + updateCount;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String GetTotalServiceCost(String first , String last){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder resultStringBuilder = new StringBuilder();
        int total_service_cost = 0;
        try {
            String sqlQuery = "SELECT * FROM Service WHERE start_date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);

            // Set parameters
            preparedStatement.setString(1, dateFormat.format(Date.valueOf(first)));
            preparedStatement.setString(2, dateFormat.format(Date.valueOf(last)));
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int service_cost = resultSet.getInt("service_cost");
                total_service_cost = total_service_cost + service_cost;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return Integer.toString(total_service_cost);
    }

    public static String GetAllVehiclesInService() {
        String sql = "SELECT * FROM Vehicle WHERE state = 'in_service' OR state = 'to_fix'";
        String availablevehicles = "";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                int vehicle_id = resultSet.getInt("vehicle_id");
                int category_id = resultSet.getInt("category_id");
                String state = resultSet.getString("state");
                String model = resultSet.getString("model");
                availablevehicles = availablevehicles + "vehicle_id: " + Integer.toString(vehicle_id) + ", state: " + state + ", category: " + Integer.toString(category_id) + ", model: " + model;

                availablevehicles = getListOfVehicles(availablevehicles , vehicle_id , category_id);

                // You can print or process the data as needed
                availablevehicles = availablevehicles + "\n";
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return availablevehicles;
    }

}

