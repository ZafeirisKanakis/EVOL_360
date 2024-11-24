import java.sql.*;

public class Initializers {
    public static void Category(){
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int rowsAffected = 0;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");
            String insertVehicle = "INSERT INTO Category (category_id, category_name) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(insertVehicle);

            // Check if entries exist
            if (!categoryExists(connection, "car")) {

                // Insert car
                preparedStatement.setInt(1, 1);
                preparedStatement.setString(2, "car");
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) System.out.println("Car category inserted successfully!");
                else System.out.println("No rows affected. Car category not inserted.");
            }

            if (!categoryExists(connection, "motorcycle")) {
                // Insert motorcycle
                preparedStatement.setInt(1, 2);
                preparedStatement.setString(2, "motorcycle");
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) System.out.println("Motorcycle category inserted successfully!");
                else System.out.println("No rows affected. Motorcycle category not inserted.");
            }

            if (!categoryExists(connection, "scooter")) {
                // Insert motorcycle
                preparedStatement.setInt(1, 3);
                preparedStatement.setString(2, "scooter");
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) System.out.println("scooter category inserted successfully!");
                else System.out.println("No rows affected. scooter category not inserted.");
            }

            if (!categoryExists(connection, "bicycle")) {
                // Insert motorcycle
                preparedStatement.setInt(1, 4);
                preparedStatement.setString(2, "bicycle");
                rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) System.out.println("bicycle category inserted successfully!");
                else System.out.println("No rows affected. bicycle category not inserted.");
            }

            // Add similar checks for scooter and bicycle categories

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in database operation: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean categoryExists(Connection connection, String categoryName) throws SQLException {
        // Check if a category with the specified name already exists
        String checkIfExists = "SELECT COUNT(*) FROM Category WHERE category_name = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkIfExists)) {
            checkStatement.setString(1, categoryName);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }

}
