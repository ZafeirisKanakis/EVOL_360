import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });

        DataBase.initialize();
        Initializers.Category();

        /*Vehicle v1 = new Vehicle(15, 300, "Cobra", 30, "black", "Toyota",1, "SUV", 0);
        Vehicle v2 = new Vehicle(10, 60, "Patini", 5, "yellow", "Xiaomi",3, "", 32);
        Vehicle v3 = new Vehicle(15, 300, "asdade", 30, "green", "BMW",1, "SUV", 0);
        Vehicle v4 = new Vehicle(10, 60, "axedf", 5, "yellow", "Lime",3, "", 32);
        Vehicle v5 = new Vehicle(15, 300, "4r4", 30, "white", "Toyota",1, "SUV", 0);
        Vehicle v6 = new Vehicle(10, 60, "22234", 5, "black", "kawasaki",2, "", 32);

        Customer c1 = new Customer("zaf", 123, "hras 5", 21060, "17/09/2003", 1213, "ZAF", "12/02/2026", 234);

        Rental r1 = new Rental(Date.valueOf("2023-12-20") , Date.valueOf("2023-12-23"), 3, 45, 1, 1, 1, "amaji", "12/02/2003");
        Rental r2 = new Rental(Date.valueOf("2023-12-25") , Date.valueOf("2023-12-30"), 5, 0, 2, 3, 1, "patini", "12/02/2003");
*/

        /*int penalty = DataBase.ReturnFromRental(1, 2 , 5);
        System.out.println("Penalty " + penalty);
*/
        System.out.println("========================================");

    }
}