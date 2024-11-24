import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Customer {
    public String customer_name;
    public int license_number;
    public String customer_address;
    public int phone_number;
    public String birth_date;
    public int card_number;
    public String card_holder;
    public String card_expiration;
    public int card_cvv;



    public Customer(String customer_name, int license_number, String customer_address, int phone_number, String birth_date, int card_number, String card_holder, String card_expiration, int card_cvv){
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String  date = String.valueOf(birth_date.charAt(6)) + String.valueOf(birth_date.charAt(7)) + String.valueOf(birth_date.charAt(8)) + String.valueOf(birth_date.charAt(9));
        int numdate = Integer.valueOf(date);
        System.out.println(numdate);
        // exw theorisei oti to license number == 0 tote den enoikiazei mixani h amaji
        if(numdate > 2005 && license_number == 0){
            System.out.println("The driver is to young");
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            String insertVehicle = "INSERT INTO Customer (customer_name, license_number, customer_address, phone_number, birth_date, card_number, card_holder, card_expiration, card_cvv) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            assert(!(connection==null));
            preparedStatement = connection.prepareStatement(insertVehicle);

            preparedStatement.setString(1, customer_name);
            preparedStatement.setInt(2, license_number);
            preparedStatement.setString(3, customer_address);
            preparedStatement.setInt(4, phone_number);
            preparedStatement.setString(5, birth_date);
            preparedStatement.setInt(6, card_number);
            preparedStatement.setString(7, card_holder);
            preparedStatement.setString(8, card_expiration);
            preparedStatement.setInt(9, card_cvv);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer inserted successfully!");
            } else {
                System.out.println("No rows affected. Customer not inserted.");
            }
//            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("OXIIIII!!!");
        }
    }
}