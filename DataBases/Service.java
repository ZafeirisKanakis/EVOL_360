import java.sql.*;

public class Service {

    public Date start_date;
    public Date end_date;
    public int service_cost;
    public int vehicle_id; /*Foreign Key*/

    public Service(Date start_date , Date end_date , int service_cost , int vehicle_id , String service_state){
        PreparedStatement preparedStatement = null;
        PreparedStatement updatePreparedStatement = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EVOL", "root", "");
            Class.forName("com.mysql.cj.jdbc.Driver");

            String insertService = "INSERT INTO Service (start_date, end_date, service_cost, vehicle_id) VALUES (?, ?, ?, ?)";
            String stateUpdateSQL;

            /*Set up the appropriate SQL depending on the state that is given*/
            if(service_state.equals("in_service"))
                stateUpdateSQL = "UPDATE Vehicle SET state = 'in_service' WHERE vehicle_id = ?";
            else
                stateUpdateSQL = "UPDATE Vehicle SET state = 'to_fix' WHERE vehicle_id = ?";

            assert(!(connection==null));
            preparedStatement = connection.prepareStatement(insertService);
            updatePreparedStatement = connection.prepareStatement(stateUpdateSQL);


            preparedStatement.setDate(1, start_date);
//            preparedStatement.setString(2, end_date);
            preparedStatement.setDate(2 , end_date);
            preparedStatement.setInt(3, service_cost);
            preparedStatement.setInt(4, vehicle_id);


            updatePreparedStatement.setInt(1 , vehicle_id);

            int rowsAffected = preparedStatement.executeUpdate();
            int updateRowsAffected = updatePreparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Service inserted successfully!");
            } else {
                System.out.println("No rows affected. Service Data not inserted.");
            }

            if (updateRowsAffected > 0) {
                System.out.println("Vehicle state changed to " + service_state);
            } else {
                System.out.println("No rows affected. Vehicle State was now updated.");
            }
//            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Service insertion Error");
        }

    }


}
