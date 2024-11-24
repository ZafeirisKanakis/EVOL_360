import java.sql.*;


public class Rental {

    public Rental(Date rental_date, Date rental_deadline, int rental_duration, int total_cost, int vehicle_id, int category_id, int customer_id, String drivers_license_number, String birth_date){
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        Connection connection = null;
        int times_rented = 0;

        if(!isAvailableForRent(vehicle_id)){
            System.out.println("Vehicle " + vehicle_id + " is not Available for rent");
            return;
        }

        String sql = "UPDATE Vehicle SET state = 'rented' WHERE vehicle_id = ?";

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");
            preparedStatement2 = connection.prepareStatement(sql);
            preparedStatement2.setInt(1, vehicle_id);
            String insertRental = "INSERT INTO Rental (rental_date, rental_deadline, rental_duration, total_cost, vehicle_id, category_id, customer_id, drivers_license_number, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            assert(!(connection==null));
            preparedStatement = connection.prepareStatement(insertRental);

            preparedStatement.setDate(1, rental_date);
            preparedStatement.setDate(2, rental_deadline);
            preparedStatement.setInt(3, rental_duration);
            preparedStatement.setInt(4, total_cost);
            preparedStatement.setInt(5, vehicle_id);
            preparedStatement.setInt(6, category_id);
            preparedStatement.setInt(7, customer_id);
            preparedStatement.setString(8, drivers_license_number);
            preparedStatement.setString(9, birth_date);

            int rowsAffected = preparedStatement.executeUpdate();
            int rowsAffected2 = preparedStatement2.executeUpdate();


            if (rowsAffected > 0) {
                System.out.println("Rental inserted successfully!");
            } else {
                System.out.println("No rows affected. Rental not inserted.");
            }

            if (rowsAffected2 > 0) {
                System.out.println("State inserted successfully!");
            } else {
                System.out.println("No rows affected. State not inserted.");
            }
            String find_the_vehicle = "SELECT * FROM Vehicle WHERE vehicle_id = " + vehicle_id;
            preparedStatement = connection.prepareStatement(find_the_vehicle);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                times_rented = resultSet.getInt("times_rented");
                times_rented++;
            }
            String update = "UPDATE Vehicle SET times_rented = " + times_rented  + " WHERE vehicle_id = " + vehicle_id;
            preparedStatement = connection.prepareStatement(update);

            rowsAffected = preparedStatement.executeUpdate();

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Vehicle does not exist in order to be rented!");
        }
    }

    private boolean isAvailableForRent(int vehicle_id){
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String result = " ";

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "SELECT state FROM Vehicle WHERE vehicle_id = ? ";

            assert(!(connection==null));
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1 , vehicle_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getString("state");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("New rental Exception!!!");
        }

        return result.equals("to_rent");

    }
}