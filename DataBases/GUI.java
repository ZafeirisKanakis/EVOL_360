import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.*;

public class GUI {
    private int loggedInCustomerId;
    private final JFrame frame;
    private JPanel currentPanel;
    private JTextField identifierField;
    private final Map<String, JTextField> fieldMap = new HashMap<>();
    private final Map<String, Object> globalMap = new HashMap<>();

    public GUI() {
        frame = new JFrame("EVOL Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600); // Adjusted size to be larger with more height than width
        frame.setLocationRelativeTo(null);

        showLoginPanel();

        frame.setVisible(true);
    }

    private void setPanel(JPanel panel) {
        if (currentPanel != null) {
            frame.remove(currentPanel);
        }

        currentPanel = panel;
        frame.add(currentPanel);
        frame.revalidate();
        frame.repaint();
    }


    //-----------------------------------Main functions-----------------------------------


    private void showLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        // Add some space at the top
        loginPanel.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        loginPanel.add(titleLabel);

        // Add space between title and separator
        loginPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        loginPanel.add(separator);

        // Reduce vertical space
        loginPanel.add(Box.createVerticalStrut(-200));

        // Buttons
        JButton adminButton = new JButton("Login as Admin");
        configureBigButtons(adminButton);
        adminButton.addActionListener(e -> showAdminPanel());
        loginPanel.add(adminButton);

        // Reduce space between buttons
        loginPanel.add(Box.createVerticalStrut(5));

        JButton customerButton = new JButton("Login as Customer");
        configureBigButtons(customerButton);
        customerButton.addActionListener(e -> showCustomerPanel());
        loginPanel.add(customerButton);

        // Add some more vertical space
        loginPanel.add(Box.createVerticalGlue());

        // Exit button
        JButton exitButton = new JButton("Exit");
        configureSmallButtons(exitButton);
        exitButton.addActionListener(e -> System.exit(0));
        loginPanel.add(exitButton);

        loginPanel.add(Box.createVerticalStrut(10));

        setPanel(loginPanel);
    }

    private void showCustomerPanel() {
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BoxLayout(customerPanel ,BoxLayout.Y_AXIS));


        // Add some space at the top
        customerPanel.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Login as Customer");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        customerPanel.add(titleLabel);

        // Add space between title and separator
        customerPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        customerPanel.add(separator);

        customerPanel.add(Box.createVerticalStrut(20));

        JLabel identifierLabel = new JLabel("Enter License Number:");
        identifierLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        identifierLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        customerPanel.add(identifierLabel);

        customerPanel.add(Box.createVerticalStrut(20));

        identifierField = new JTextField();
        identifierField.setFont(identifierField.getFont().deriveFont(Font.PLAIN, 16));
        identifierField.setHorizontalAlignment(JTextField.CENTER);
        customerPanel.add(identifierField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            loggedInCustomerId = DataBase.isValidCustomer(identifierField.getText());
            if (loggedInCustomerId != -1) {
                showLoggedInCustomerPanel();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid License Number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        configureSmallButtons(loginButton);
        customerPanel.add(loginButton);

        customerPanel.add(Box.createVerticalStrut(250));

        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showLoginPanel());
        customerPanel.add(backButton);

        customerPanel.add(Box.createVerticalStrut(10));
        setPanel(customerPanel);
    }

    private void showAdminPanel() {
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new BoxLayout(adminPanel ,BoxLayout.Y_AXIS));

        // Add some space at the top
        adminPanel.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Login as Admin");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        adminPanel.add(titleLabel);

        // Add space between title and separator
        adminPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        adminPanel.add(separator);

        adminPanel.add(Box.createVerticalStrut(20));

        JLabel identifierLabel = new JLabel("Enter Password:");
        identifierLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        identifierLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminPanel.add(identifierLabel);

        adminPanel.add(Box.createVerticalStrut(20));

        identifierField = new JPasswordField();
        identifierField.setHorizontalAlignment(JTextField.CENTER);
        identifierField.setFont(identifierField.getFont().deriveFont(Font.PLAIN, 16));
        adminPanel.add(identifierField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            if (Objects.equals(identifierField.getText(), "admin")) {
                showLoggedInAdminPanel();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        configureSmallButtons(loginButton);
        adminPanel.add(loginButton);

        adminPanel.add(Box.createVerticalStrut(250));

        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showLoginPanel());
        adminPanel.add(backButton);

        adminPanel.add(Box.createVerticalStrut(10));
        setPanel(adminPanel);
    }

    private void showLoggedInCustomerPanel() {
        JPanel loggedInCustomerPanel = new JPanel();
        loggedInCustomerPanel.setLayout(new BoxLayout(loggedInCustomerPanel,BoxLayout.Y_AXIS));

        loggedInCustomerPanel.add(Box.createVerticalStrut(20));
        // Title
        JLabel titleLabel = new JLabel("Choose Action");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        loggedInCustomerPanel.add(titleLabel);

        // Add space between title and separator
        loggedInCustomerPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        loggedInCustomerPanel.add(separator);

        loggedInCustomerPanel.add(Box.createVerticalStrut(-200));

        JButton rent = new JButton("Rent A Vehicle");
        rent.addActionListener(e -> showRentVehiclePanel());
        configureBigButtons(rent);
        loggedInCustomerPanel.add(rent);

        loggedInCustomerPanel.add(Box.createVerticalStrut(5));

        JButton return_rented = new JButton("Return A Rented Vehicle");
        return_rented.addActionListener(e -> showReturnRentedVehiclePanel());
        configureBigButtons(return_rented);
        loggedInCustomerPanel.add(return_rented);


        loggedInCustomerPanel.add(Box.createVerticalGlue());

        JButton logoutButton = new JButton("Logout");
        configureSmallButtons(logoutButton);
        logoutButton.addActionListener(e -> showLoginPanel());
        loggedInCustomerPanel.add(logoutButton);

        loggedInCustomerPanel.add(Box.createVerticalStrut(10));

        setPanel(loggedInCustomerPanel);
    }

    private void showRentVehiclePanel(){
        JPanel showRentVehiclePanel = new JPanel();
        showRentVehiclePanel.setLayout(new BoxLayout(showRentVehiclePanel,BoxLayout.Y_AXIS));

        showRentVehiclePanel.add(Box.createVerticalStrut(20));
        // Title
        JLabel titleLabel = new JLabel("Choose Category");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        showRentVehiclePanel.add(titleLabel);

        // Add space between title and separator
        showRentVehiclePanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        showRentVehiclePanel.add(separator);

        showRentVehiclePanel.add(Box.createVerticalStrut(-50));

        JButton allButton = new JButton("All Vehicles");
        allButton.addActionListener(e -> showRentalInfoPanel(5));
        configureMediumButtons(allButton);
        showRentVehiclePanel.add(allButton);

        showRentVehiclePanel.add(Box.createVerticalStrut(5));

        JButton carsButton = new JButton("Cars");
        carsButton.addActionListener(e -> showRentalInfoPanel(1));
        configureMediumButtons(carsButton);
        showRentVehiclePanel.add(carsButton);

        showRentVehiclePanel.add(Box.createVerticalStrut(5));

        JButton motorcyclesButton = new JButton("Motorcycles");
        motorcyclesButton.addActionListener(e -> showRentalInfoPanel(2));
        configureMediumButtons(motorcyclesButton);
        showRentVehiclePanel.add(motorcyclesButton);

        showRentVehiclePanel.add(Box.createVerticalStrut(5));

        JButton scootersButton = new JButton("Scooters");
        scootersButton.addActionListener(e -> showRentalInfoPanel(3));
        configureMediumButtons(scootersButton);
        showRentVehiclePanel.add(scootersButton);

        showRentVehiclePanel.add(Box.createVerticalStrut(5));

        JButton bicyclesButton = new JButton("Bicycles");
        bicyclesButton.addActionListener(e -> showRentalInfoPanel(4));
        configureMediumButtons(bicyclesButton);
        showRentVehiclePanel.add(bicyclesButton);

        showRentVehiclePanel.add(Box.createVerticalGlue());

        JButton logoutButton = new JButton("Back");
        configureSmallButtons(logoutButton);
        logoutButton.addActionListener(e -> showLoggedInCustomerPanel());
        showRentVehiclePanel.add(logoutButton);

        showRentVehiclePanel.add(Box.createVerticalStrut(10));

        setPanel(showRentVehiclePanel);
    }
    private void showReturnRentedVehiclePanel(){
        JPanel showReturnRentedVehiclePanel = new JPanel();
        showReturnRentedVehiclePanel.setLayout(new BoxLayout(showReturnRentedVehiclePanel, BoxLayout.Y_AXIS));

        // Add some space at the top
        showReturnRentedVehiclePanel.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Choose Vehicle To Return");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        showReturnRentedVehiclePanel.add(titleLabel);

        // Add space between title and separator
        showReturnRentedVehiclePanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        showReturnRentedVehiclePanel.add(separator);

        showReturnRentedVehiclePanel.add(Box.createVerticalStrut(-20));

        // Text area to display rentals
        JTextArea rentalsTextArea = new JTextArea();
        rentalsTextArea.setEditable(false);
        try {
            rentalsTextArea.setText(DataBase.GetAllRentedVehiclesBy(loggedInCustomerId));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Couldn't get rented vehicles");
            showLoggedInAdminPanel();
        }
        JScrollPane scrollPane = new JScrollPane(rentalsTextArea);
        scrollPane.setPreferredSize(new Dimension(350, 100));
        showReturnRentedVehiclePanel.add(scrollPane);

        showReturnRentedVehiclePanel.add(createInputLabelAndTextField("Vehicle Id"));
        showReturnRentedVehiclePanel.add(Box.createVerticalStrut(5)); // Add space between fields
        showReturnRentedVehiclePanel.add(createInputLabelAndTextField("Days passed since rental"));

        showReturnRentedVehiclePanel.add(Box.createVerticalGlue());

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureBigButtons(acceptButton);
        acceptButton.addActionListener(e -> handleReturnFromRentalDetails());
        showReturnRentedVehiclePanel.add(acceptButton);

        showReturnRentedVehiclePanel.add(Box.createVerticalStrut(5));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showLoggedInCustomerPanel());
        showReturnRentedVehiclePanel.add(backButton);

        showReturnRentedVehiclePanel.add(Box.createVerticalStrut(10));

        setPanel(showReturnRentedVehiclePanel);
    }
    private void handleReturnFromRentalDetails(){
        String vehicle_id = getTextFromField("Vehicle Id");
        String days_passed_since_rental = getTextFromField("Days passed since rental");
        int penalty_cost = 0;


        if (vehicle_id.isEmpty() || !isInteger(vehicle_id)) {
            showErrorMessage("Vehicle Id",vehicle_id);
            return;
        }
        if (days_passed_since_rental.isEmpty() || !isInteger(days_passed_since_rental)) {
            showErrorMessage("Days passed since rental",days_passed_since_rental);
            return;
        }

        penalty_cost = DataBase.ReturnFromRental(loggedInCustomerId,Integer.parseInt(vehicle_id),Integer.parseInt(days_passed_since_rental));
        if(penalty_cost == 0)
            JOptionPane.showConfirmDialog(frame, "Return rental details accepted!","",JOptionPane.DEFAULT_OPTION);
        else
            JOptionPane.showMessageDialog(frame, "Due to late return of the vehicle, you have been charged an additional \"" + penalty_cost + "\" euros.","Rental penalty!", JOptionPane.WARNING_MESSAGE);
    }

    private void showLoggedInAdminPanel() {
        JPanel showLoggedInAdminPanel = new JPanel();
        showLoggedInAdminPanel.setLayout(new BoxLayout(showLoggedInAdminPanel,BoxLayout.Y_AXIS));

        showLoggedInAdminPanel.add(Box.createVerticalStrut(20));
        // Title
        JLabel titleLabel = new JLabel("Choose Action");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        showLoggedInAdminPanel.add(titleLabel);

        // Add space between title and separator
        showLoggedInAdminPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        showLoggedInAdminPanel.add(separator);

        showLoggedInAdminPanel.add(Box.createVerticalStrut(-50));

        JButton showRentalsButton = new JButton("Show Rentals");
        showRentalsButton.addActionListener(e -> showRentalsPanel());
        configureMediumButtons(showRentalsButton);
        showLoggedInAdminPanel.add(showRentalsButton);

        showLoggedInAdminPanel.add(Box.createVerticalStrut(5));

        JButton addCustomerButton = new JButton("Add a Customer");
        configureMediumButtons(addCustomerButton);
        addCustomerButton.addActionListener(e -> showAddCustomerDetailsPanel());
        showLoggedInAdminPanel.add(addCustomerButton);

        showLoggedInAdminPanel.add(Box.createVerticalStrut(5));

        JButton addVehicleButton = new JButton("Add a Vehicle");
        configureMediumButtons(addVehicleButton);
        addVehicleButton.addActionListener(e -> showAddVehicleDetailsPanel());
        showLoggedInAdminPanel.add(addVehicleButton);

        showLoggedInAdminPanel.add(Box.createVerticalStrut(5));

        JButton sendMaintenanceButton = new JButton("Send a Vehicle for Service");
        configureMediumButtons(sendMaintenanceButton);
        sendMaintenanceButton.addActionListener(e -> showService());
        showLoggedInAdminPanel.add(sendMaintenanceButton);

        showLoggedInAdminPanel.add(Box.createVerticalStrut(5));


        JButton returnFromService = new JButton("Return a Vehicle from Service");
        configureMediumButtons(returnFromService);
        returnFromService.addActionListener(e -> returnFromRental());
        showLoggedInAdminPanel.add(returnFromService);

        showLoggedInAdminPanel.add(Box.createVerticalStrut(5));

        JButton sendRepairButton = new JButton("Questions for DB");
        sendRepairButton.addActionListener(e -> showQueriesPanel());
        configureMediumButtons(sendRepairButton);
        showLoggedInAdminPanel.add(sendRepairButton);

        showLoggedInAdminPanel.add(Box.createVerticalStrut(20));

        JButton logoutButton = new JButton("Logout");
        configureSmallButtons(logoutButton);
        logoutButton.addActionListener(e -> showLoginPanel());
        showLoggedInAdminPanel.add(logoutButton);

        showLoggedInAdminPanel.add(Box.createVerticalStrut(10));

        setPanel(showLoggedInAdminPanel);
    }


    private void showAddCustomerDetailsPanel() {

        JPanel addCustomerDetailsPanel = new JPanel();
        addCustomerDetailsPanel.setLayout(new BoxLayout(addCustomerDetailsPanel, BoxLayout.Y_AXIS));

        // Add some space at the top
        addCustomerDetailsPanel.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Customer Details");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        addCustomerDetailsPanel.add(titleLabel);

        // Add space between title and separator
        addCustomerDetailsPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        addCustomerDetailsPanel.add(separator);

        // Customer details input fields
        addCustomerDetailsPanel.add(createInputLabelAndTextField("Customer Name"));
        addCustomerDetailsPanel.add(createInputLabelAndTextField("License Number"));
        addCustomerDetailsPanel.add(createInputLabelAndTextField("Customer Address"));
        addCustomerDetailsPanel.add(createInputLabelAndTextField("Phone Number"));
        addCustomerDetailsPanel.add(createInputLabelAndTextField("Birth Date"));
        addCustomerDetailsPanel.add(createInputLabelAndTextField("Card Number"));
        addCustomerDetailsPanel.add(createInputLabelAndTextField("Card Holder"));
        addCustomerDetailsPanel.add(createInputLabelAndTextField("Card Expiration"));
        addCustomerDetailsPanel.add(createInputLabelAndTextField("Card CVV"));

        // Add some space between input fields and buttons
        addCustomerDetailsPanel.add(Box.createHorizontalStrut(20));

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureBigButtons(acceptButton);
        acceptButton.addActionListener(e -> handleAcceptCustomerDetails());
        addCustomerDetailsPanel.add(acceptButton);

        // Add some space between buttons
        addCustomerDetailsPanel.add(Box.createVerticalStrut(5));

        // Cancel button
        JButton logoutButton = new JButton("Cancel");
        configureSmallButtons(logoutButton);
        logoutButton.addActionListener(e -> showLoggedInAdminPanel());
        addCustomerDetailsPanel.add(logoutButton);

        addCustomerDetailsPanel.add(Box.createVerticalStrut(10));

        setPanel(addCustomerDetailsPanel);
    }
    private void handleAcceptCustomerDetails() {
        String customer_name = getTextFromField("Customer Name");
        String license_number = getTextFromField("License Number");
        String customer_address = getTextFromField("Customer Address");
        String phone_number = getTextFromField("Phone Number");
        String birth_date = getTextFromField("Birth Date");
        String card_number = getTextFromField("Card Number");
        String card_holder = getTextFromField("Card Holder");
        String card_expiration = getTextFromField("Card Expiration");
        String card_cvv = getTextFromField("Card CVV");

        if (customer_name.isEmpty() || isInteger(customer_name)) {
            showErrorMessage("Customer Name",customer_name);
            return;
        }
        if (license_number.isEmpty() || !isInteger(license_number)) {
            showErrorMessage("License Number",license_number);
            return;
        }
        if (customer_address.isEmpty() || isInteger(customer_address)) {
            showErrorMessage("Customer Address",customer_address);
            return;
        }
        if (phone_number.isEmpty() || !isInteger(phone_number)) {
            showErrorMessage("Phone Number",phone_number);
            return;
        }
        if (birth_date.isEmpty() || isInteger(birth_date)) {
            showErrorMessage("Birth Date",birth_date);
            return;
        }
        if (card_number.isEmpty() || !isInteger(card_number)) {
            showErrorMessage("Card Number",card_number);
            return;
        }
        if (card_holder.isEmpty() || isInteger(card_holder)) {
            showErrorMessage("Card Holder",card_holder);
            return;
        }
        if (card_expiration.isEmpty() || isInteger(card_expiration)) {
            showErrorMessage("Card Expiration",card_expiration);
            return;
        }
        if (card_cvv.isEmpty() || !isInteger(card_cvv)) {
            showErrorMessage("Card CVV",card_cvv);
            return;
        }

        Customer c = new Customer(customer_name, Integer.parseInt(license_number), customer_address,
                Integer.parseInt(phone_number), birth_date, Integer.parseInt(card_number), card_holder,
                card_expiration, Integer.parseInt(card_cvv));

        JOptionPane.showConfirmDialog(frame, "Customer details accepted!","",JOptionPane.DEFAULT_OPTION);
    }

    private void showAddVehicleDetailsPanel() {
        JPanel showAddVehicleDetailsPanel = new JPanel();
        showAddVehicleDetailsPanel.setLayout(new BoxLayout(showAddVehicleDetailsPanel, BoxLayout.Y_AXIS));

        // Add some space at the top
        showAddVehicleDetailsPanel.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Vehicle Details");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        showAddVehicleDetailsPanel.add(titleLabel);

        // Add space between title and separator
        showAddVehicleDetailsPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        showAddVehicleDetailsPanel.add(separator);

        // Customer details input fields
        showAddVehicleDetailsPanel.add(createInputLabelAndTextField("Rental Cost"));
        showAddVehicleDetailsPanel.add(createInputLabelAndTextField("KM Autonomy"));
        showAddVehicleDetailsPanel.add(createInputLabelAndTextField("Model"));
        showAddVehicleDetailsPanel.add(createInputLabelAndTextField("Insurance Cost"));
        showAddVehicleDetailsPanel.add(createInputLabelAndTextField("Color"));
        showAddVehicleDetailsPanel.add(createInputLabelAndTextField("Brand"));
        String[] options = {"Car", "Motorcycle", "Bicycle", "Scooter"};
        JComboBox<String> dropdown = new JComboBox<String>(options);
        showAddVehicleDetailsPanel.add(dropdown);

        // Add some space between input fields and buttons
        showAddVehicleDetailsPanel.add(Box.createVerticalStrut(10));

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureBigButtons(acceptButton);

        acceptButton.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
                                               handleAcceptVehicleDetails(dropdown);
                                               getAdditionalData();
                                           }
                                       });
                showAddVehicleDetailsPanel.add(acceptButton);

        // Add some space between buttons
        showAddVehicleDetailsPanel.add(Box.createVerticalStrut(5));

        // Cancel button
        JButton logoutButton = new JButton("Cancel");
        configureSmallButtons(logoutButton);
        logoutButton.addActionListener(e -> showLoggedInAdminPanel());
        showAddVehicleDetailsPanel.add(logoutButton);

        showAddVehicleDetailsPanel.add(Box.createVerticalStrut(10));

        setPanel(showAddVehicleDetailsPanel);
    }


    private void handleAcceptVehicleDetails(JComboBox<String> dropdown) {
        String rental_cost = getTextFromField("Rental Cost");
        String km_autonomy = getTextFromField("KM Autonomy");
        String model = getTextFromField("Model");
        String insurance_cost = getTextFromField("Insurance Cost");
        String color = getTextFromField("Color");
        String brand = getTextFromField("Brand");
        String arg1 = "";
        Integer arg2 = 0;
        int category_id = 0;

        if (rental_cost.isEmpty() || !isInteger(rental_cost)) {
            showErrorMessage("Rental Cost", rental_cost);
            return;
        }
        if (km_autonomy.isEmpty() || !isInteger(km_autonomy)) {
            showErrorMessage("KM Autonomy", km_autonomy);
            return;
        }
        if (model.isEmpty() || isInteger(model)) {
            showErrorMessage("Model", model);
            return;
        }
        if (insurance_cost.isEmpty() || !isInteger(insurance_cost)) {
            showErrorMessage("Insurance Cost", insurance_cost);
            return;
        }
        if (color.isEmpty() || isInteger(color)) {
            showErrorMessage("Color", color);
            return;
        }
        if (brand.isEmpty() || isInteger(brand)) {
            showErrorMessage("Brand", brand);
            return;
        }


        globalMap.put("rental_cost",Integer.parseInt(rental_cost));
        globalMap.put("km_autonomy",Integer.parseInt(km_autonomy));
        globalMap.put("model",model);
        globalMap.put("insurance_cost",Integer.parseInt(insurance_cost));
        globalMap.put("color",color);
        globalMap.put("brand",brand);
        if (dropdown.getSelectedItem() == "Car") globalMap.put("category_id",1);
        else if (dropdown.getSelectedItem() == "Motorcycle") globalMap.put("category_id",2);
        else if (dropdown.getSelectedItem() == "Scooter") globalMap.put("category_id",3);
        else if (dropdown.getSelectedItem() == "Bicycle") globalMap.put("category_id",4);


        JOptionPane.showConfirmDialog(frame, "Vehicle details accepted!","",JOptionPane.DEFAULT_OPTION);
    }

    private void getAdditionalData() {
        Integer category_id = 0;
        try {
            category_id = (Integer) globalMap.get("category_id");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Don't have category Id");
        }
        JPanel addVehicleSpecsPanel = new JPanel();
        addVehicleSpecsPanel.setLayout(new BoxLayout(addVehicleSpecsPanel, BoxLayout.Y_AXIS));

        // Add some space at the top
        addVehicleSpecsPanel.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Add Vehicle Specifications");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        addVehicleSpecsPanel.add(titleLabel);

        // Add space between title and separator
        addVehicleSpecsPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        addVehicleSpecsPanel.add(separator);

        addVehicleSpecsPanel.add(Box.createVerticalStrut(20));

        JLabel arg1Label = new JLabel("");
        arg1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        arg1Label.setFont(new Font("Arial", Font.PLAIN, 18));
        addVehicleSpecsPanel.add(arg1Label);

        JTextField arg1_field = new JTextField();
        arg1_field.setPreferredSize(new Dimension(200, 30));
        addVehicleSpecsPanel.add(arg1_field);

        // Add space between input fields
        addVehicleSpecsPanel.add(Box.createVerticalStrut(10));

        // Label and dropdown menu for vehicle type
        JLabel arg2Label = new JLabel("");
        arg2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        arg2Label.setFont(new Font("Arial", Font.PLAIN, 18));
        addVehicleSpecsPanel.add(arg2Label);

        String[] dropDownItems = {};

        JComboBox<String> dropdown = new JComboBox<>(dropDownItems);

        if (category_id == 1) {
            arg1Label.setText("Number Of Passengers:");
            arg2Label.setText("Type");
            dropDownItems = new String[]{"SUV", "Cabrio", "City Car", "Van"};
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dropDownItems);
            dropdown.setModel(model);
            dropdown.setPreferredSize(new Dimension(200, 30));
            addVehicleSpecsPanel.add(dropdown);

        } else if (category_id == 2) {
            arg2Label.setText("On/Off:");
            arg1_field.setVisible(false);
            dropDownItems = new String[]{"True", "False"};
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dropDownItems);
            dropdown.setModel(model);
            dropdown.setPreferredSize(new Dimension(200, 30));
            addVehicleSpecsPanel.add(dropdown);

        } else if (category_id == 3) {
            arg1Label.setText("Top Speed:");
            dropdown.setVisible(false);
        } else if (category_id == 4) {
            arg1Label.setText("Type of Pedal");
            dropdown.setVisible(false);
        }

        // Set the items before creating the JComboBox


        // Add space between input fields and button
        addVehicleSpecsPanel.add(Box.createVerticalStrut(20));

        JButton acceptButton = new JButton("Accept");
        configureSmallButtons(acceptButton);
        acceptButton.addActionListener(e -> {
            String arg1 = "";
            int arg2 = 0;

            if (dropdown.getSelectedItem() == null){
                if(!isInteger(arg1_field.getText()))arg1 = arg1_field.getText(); //Bicycle Case
                else arg2 = Integer.parseInt(arg1_field.getText()); //Scooter Case
            }
            else{
                arg1 = (String)dropdown.getSelectedItem(); //Car & Motorcycle case
                if(!Objects.equals(arg1_field.getText(), ""))arg2 = Integer.parseInt(arg1_field.getText());
            }

            Vehicle v = new Vehicle(
                    (int) globalMap.get("rental_cost"),
                    (int) globalMap.get("km_autonomy"),
                    (String) globalMap.get("model"),
                    (int) globalMap.get("insurance_cost"),
                    (String) globalMap.get("color"),
                    (String) globalMap.get("brand"),
                    (int) globalMap.get("category_id"),
                    arg1,
                    arg2
            );


            // Retrieve values from input fields
            JOptionPane.showConfirmDialog(frame, "Vehicle details accepted!", "", JOptionPane.DEFAULT_OPTION);

            showLoggedInAdminPanel();
        });
        addVehicleSpecsPanel.add(acceptButton);

        setPanel(addVehicleSpecsPanel);
    }



    private void showService() {
        JPanel service = new JPanel();
        service.setLayout(new BoxLayout(service, BoxLayout.Y_AXIS));

        // Add some space at the top
        service.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Choose Vehicle for Service");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        service.add(titleLabel);

        // Add space between title and separator
        service.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        service.add(separator);

        service.add(Box.createVerticalStrut(-20));

        // Text area to display rentals
        JTextArea rentalsTextArea = new JTextArea();
        rentalsTextArea.setEditable(false);
        try {
            rentalsTextArea.setText(DataBase.GetAllVehicles());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Couldn't get vehicles");
            showLoggedInAdminPanel();
        }
        JScrollPane scrollPane = new JScrollPane(rentalsTextArea);
        scrollPane.setPreferredSize(new Dimension(350, 100));
        service.add(scrollPane);


        // Left side panel for Vehicle ID and Service Cost
        JPanel leftSidePanel = new JPanel();
        leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS)); // Vertical BoxLayout
        leftSidePanel.add(createInputLabelAndTextField("Vehicle Id"));
        leftSidePanel.add(Box.createVerticalStrut(10)); // Add space between fields
        leftSidePanel.add(createInputLabelAndTextField("Service Cost"));

        // Right side panel for Start and End Date
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new BoxLayout(rightSidePanel, BoxLayout.Y_AXIS)); // Vertical BoxLayout
        rightSidePanel.add(createInputLabelAndTextField("Start Date"));
        rightSidePanel.add(Box.createVerticalStrut(10)); // Add space between fields
        rightSidePanel.add(createInputLabelAndTextField("End Date"));

        // Horizontal panel to contain left and right side panels
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS)); // Horizontal BoxLayout
        horizontalPanel.add(leftSidePanel);
        horizontalPanel.add(Box.createHorizontalStrut(20)); // Add space between left and right sides
        horizontalPanel.add(Box.createVerticalStrut(20)); // Add space between left and right sides
        horizontalPanel.add(rightSidePanel);

        // Add horizontal panel to the main panel
        service.add(horizontalPanel);

        service.add(Box.createVerticalStrut(5));

        // Drop-down menu with options
        String[] options = {"Damage", "Accident", "Maintenance"};
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        service.add(dropdown);

        // Add some space between drop-down menu and buttons
        service.add(Box.createVerticalStrut(50));

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureBigButtons(acceptButton);
        acceptButton.addActionListener(e -> handleServiceDetails(dropdown));
        service.add(acceptButton);

        service.add(Box.createVerticalStrut(5));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showLoggedInAdminPanel());
        service.add(backButton);

        service.add(Box.createVerticalStrut(10));

        setPanel(service);
    }

    private void showQueriesPanel(){
        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));

        // Add some space at the top
        questionsPanel.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Arbitrary Queries");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        questionsPanel.add(titleLabel);

        // Add space between title and separator
        questionsPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        questionsPanel.add(separator);

        // Add some space between separator and buttons
        questionsPanel.add(Box.createVerticalStrut(5));

        // Buttons
        JButton customQuestions = new JButton("Arbitrary Queries");
        configureMediumButtons(customQuestions);
        customQuestions.addActionListener(e -> showArbitraryQuery());
        questionsPanel.add(customQuestions);

        questionsPanel.add(Box.createVerticalStrut(5));

        JButton statusByCategoryButton = new JButton("Status by Category");
        configureMediumButtons(statusByCategoryButton);
        statusByCategoryButton.addActionListener(e -> showStatusByCategoryPanel());
        questionsPanel.add(statusByCategoryButton);

        questionsPanel.add(Box.createVerticalStrut(5));

        JButton rentalStatusButton = new JButton("Rental Status per Time Period");
        configureMediumButtons(rentalStatusButton);
        rentalStatusButton.addActionListener(e -> showRentalStatusPanel());
        questionsPanel.add(rentalStatusButton);

        questionsPanel.add(Box.createVerticalStrut(5));

        JButton rentalDurationButton = new JButton("Max, Min, Avg Rental Duration");
        configureMediumButtons(rentalDurationButton);
        rentalDurationButton.addActionListener(e -> showRentalDurationPanel());
        questionsPanel.add(rentalDurationButton);

        questionsPanel.add(Box.createVerticalStrut(5));

        JButton incomeByTimePeriodButton = new JButton("Rental Income by Time Period");
        configureMediumButtons(incomeByTimePeriodButton);
        incomeByTimePeriodButton.addActionListener(e -> showIncomeByTimePeriodPanel());
        questionsPanel.add(incomeByTimePeriodButton);

        questionsPanel.add(Box.createVerticalStrut(5));

        JButton maintenanceCostsButton = new JButton("Total Maintenance and Repair Costs");
        configureMediumButtons(maintenanceCostsButton);
        maintenanceCostsButton.addActionListener(e -> showMaintenanceCostsPanel());
        questionsPanel.add(maintenanceCostsButton);

        questionsPanel.add(Box.createVerticalStrut(5));

        JButton popularVehicleButton = new JButton("Most Popular Vehicle by Category");
        configureMediumButtons(popularVehicleButton);
        popularVehicleButton.addActionListener(e -> showPopularVehiclePanel());
        questionsPanel.add(popularVehicleButton);

        questionsPanel.add(Box.createVerticalStrut(5));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showLoggedInAdminPanel());
        questionsPanel.add(backButton);

        questionsPanel.add(Box.createVerticalStrut(10));

        setPanel(questionsPanel);
    }

    private void showArbitraryQuery() {
        JPanel arbitraryQuery = new JPanel();
        arbitraryQuery.setLayout(new BoxLayout(arbitraryQuery, BoxLayout.Y_AXIS));

        // Add some space at the top
        arbitraryQuery.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Write your DB query");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the title
        arbitraryQuery.add(titleLabel);

        // Add space between title and separator
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        arbitraryQuery.add(separator);

        arbitraryQuery.add(Box.createVerticalStrut(20));

        JLabel input = new JLabel("Input");
        input.setAlignmentX(Component.CENTER_ALIGNMENT);
        input.setFont(new Font("Arial", Font.PLAIN, 25));
        arbitraryQuery.add(input);

        // Text area to display rentals
        JTextArea queryInputTextArea = new JTextArea();
        JTextArea queryOutputTextArea = new JTextArea();

        queryInputTextArea.setAlignmentY(Component.TOP_ALIGNMENT);
        queryOutputTextArea.setAlignmentY(Component.TOP_ALIGNMENT);

        JScrollPane inputScrollPane = new JScrollPane(queryInputTextArea);

        JScrollPane outputScrollPane = new JScrollPane(queryOutputTextArea);

        inputScrollPane.setPreferredSize(new Dimension(400, 200));
        arbitraryQuery.add(inputScrollPane);
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureMediumButtons(acceptButton);
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        acceptButton.addActionListener(e -> {
            queryOutputTextArea.setText(DataBase.executeArbitraryQueries(queryInputTextArea.getText()));
        });
        arbitraryQuery.add(acceptButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        JLabel result = new JLabel("Result");
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        result.setFont(new Font("Arial", Font.PLAIN, 25));
        arbitraryQuery.add(result);

        // Text area to display rentals
        queryOutputTextArea.setFocusable(false);
        queryOutputTextArea.setPreferredSize(new Dimension(400, 600));
        arbitraryQuery.add(outputScrollPane);
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showQueriesPanel());
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        arbitraryQuery.add(backButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        setPanel(arbitraryQuery);
    }




    private void showStatusByCategoryPanel() {
        JPanel arbitraryQuery = new JPanel();
        arbitraryQuery.setLayout(new BoxLayout(arbitraryQuery, BoxLayout.Y_AXIS));

        // Add some space at the top
        arbitraryQuery.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Get all vehicles by id");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the title
        arbitraryQuery.add(titleLabel);

        // Add space between title and separator
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        arbitraryQuery.add(separator);

        arbitraryQuery.add(Box.createVerticalStrut(20));

        arbitraryQuery.add(createInputLabelAndTextField("Category Id"));

        // Text area to display rentals
        JTextArea queryOutputTextArea = new JTextArea();

        queryOutputTextArea.setAlignmentY(Component.TOP_ALIGNMENT);


        JScrollPane outputScrollPane = new JScrollPane(queryOutputTextArea);


        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureMediumButtons(acceptButton);
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        acceptButton.addActionListener(e -> {
            String category_id = getTextFromField("Category Id");
            if (category_id.isEmpty() || !isInteger(category_id) || Integer.parseInt(category_id) < 1 || Integer.parseInt(category_id) > 4) {
                showErrorMessage("Category Id", category_id );
                return;
            }
            queryOutputTextArea.setText(DataBase.GetAllAvailableVehiclesByCat(Integer.parseInt(category_id)));
            JOptionPane.showConfirmDialog(frame, "Accepted!", "", JOptionPane.DEFAULT_OPTION);
        });
        arbitraryQuery.add(acceptButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        JLabel result = new JLabel("Result");
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        result.setFont(new Font("Arial", Font.PLAIN, 25));
        arbitraryQuery.add(result);

        // Text area to display rentals
        queryOutputTextArea.setFocusable(false);
        queryOutputTextArea.setPreferredSize(new Dimension(400, 600));
        arbitraryQuery.add(outputScrollPane);
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showQueriesPanel());
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the back button
        arbitraryQuery.add(backButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        setPanel(arbitraryQuery);


    }

    private void showRentalStatusPanel() {
        JPanel arbitraryQuery = new JPanel();
        arbitraryQuery.setLayout(new BoxLayout(arbitraryQuery, BoxLayout.Y_AXIS));

        // Add some space at the top
        arbitraryQuery.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Get all rentals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the title
        arbitraryQuery.add(titleLabel);

        // Add space between title and separator
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        arbitraryQuery.add(separator);

        arbitraryQuery.add(Box.createVerticalStrut(20));

        arbitraryQuery.add(createInputLabelAndTextField("Rentals from"));
        arbitraryQuery.add(createInputLabelAndTextField("Rentals until"));

        // Text area to display rentals
        JTextArea queryOutputTextArea = new JTextArea();

        queryOutputTextArea.setAlignmentY(Component.TOP_ALIGNMENT);


        JScrollPane outputScrollPane = new JScrollPane(queryOutputTextArea);


        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureMediumButtons(acceptButton);
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        acceptButton.addActionListener(e -> {
            String rentals_from = getTextFromField("Rentals from");
            String rentals_until = getTextFromField("Rentals until");
            if (rentals_from.isEmpty() || isInteger(rentals_from)) {
                showErrorMessage("Rentals from", rentals_from);
                return;
            }

            if (rentals_until.isEmpty() || isInteger(rentals_until)) {
                showErrorMessage("Rentals until", rentals_until);
                return;
            }
            queryOutputTextArea.setText(DataBase.GetAllRentalsBetween(rentals_from, rentals_until));
            JOptionPane.showConfirmDialog(frame, "Accepted!", "", JOptionPane.DEFAULT_OPTION);
        });
        arbitraryQuery.add(acceptButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        JLabel result = new JLabel("Result");
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        result.setFont(new Font("Arial", Font.PLAIN, 25));
        arbitraryQuery.add(result);

        // Text area to display rentals
        queryOutputTextArea.setFocusable(false);
        queryOutputTextArea.setPreferredSize(new Dimension(400, 600));
        arbitraryQuery.add(outputScrollPane);
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showQueriesPanel());
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the back button
        arbitraryQuery.add(backButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        setPanel(arbitraryQuery);
    }

    private void showRentalDurationPanel() {
        JPanel arbitraryQuery = new JPanel();
        arbitraryQuery.setLayout(new BoxLayout(arbitraryQuery, BoxLayout.Y_AXIS));

        // Add some space at the top
        arbitraryQuery.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Get the Max, Min, Average rental duration by category");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the title
        arbitraryQuery.add(titleLabel);

        // Add space between title and separator
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        arbitraryQuery.add(separator);

        arbitraryQuery.add(Box.createVerticalStrut(20));

        arbitraryQuery.add(createInputLabelAndTextField("Type Max Min Average"));

        // Text area to display rentals
        JTextArea queryOutputTextArea = new JTextArea();

        queryOutputTextArea.setAlignmentY(Component.TOP_ALIGNMENT);


        JScrollPane outputScrollPane = new JScrollPane(queryOutputTextArea);


        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureMediumButtons(acceptButton);
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        acceptButton.addActionListener(e -> {
            String type_max_min_average = getTextFromField("Type Max Min Average");

            if (type_max_min_average.isEmpty() || isInteger(type_max_min_average)) {
                showErrorMessage("Rentals from", type_max_min_average);
                return;
            }
            if(type_max_min_average.equals("Max")){
                queryOutputTextArea.setText(DataBase.GetMaxRentalDuration());
            }else if(type_max_min_average.equals("Min")){
                queryOutputTextArea.setText(DataBase.GetMinRentalDuration());
            }else {
                queryOutputTextArea.setText(DataBase.GetMedianRentalDuration());
            }

            JOptionPane.showConfirmDialog(frame, "Accepted!", "", JOptionPane.DEFAULT_OPTION);
        });
        arbitraryQuery.add(acceptButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        JLabel result = new JLabel("Result");
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        result.setFont(new Font("Arial", Font.PLAIN, 25));
        arbitraryQuery.add(result);

        // Text area to display rentals
        queryOutputTextArea.setFocusable(false);
        queryOutputTextArea.setPreferredSize(new Dimension(400, 600));
        arbitraryQuery.add(outputScrollPane);
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showQueriesPanel());
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the back button
        arbitraryQuery.add(backButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        setPanel(arbitraryQuery);
    }

    private void showIncomeByTimePeriodPanel() {
        JPanel arbitraryQuery = new JPanel();
        arbitraryQuery.setLayout(new BoxLayout(arbitraryQuery, BoxLayout.Y_AXIS));

        // Add some space at the top
        arbitraryQuery.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Get the Max, Min, Average rental duration by category");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the title
        arbitraryQuery.add(titleLabel);

        // Add space between title and separator
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        arbitraryQuery.add(separator);

        arbitraryQuery.add(Box.createVerticalStrut(20));

        arbitraryQuery.add(createInputLabelAndTextField("Type Max Min Average"));

        // Text area to display rentals
        JTextArea queryOutputTextArea = new JTextArea();

        queryOutputTextArea.setAlignmentY(Component.TOP_ALIGNMENT);


        JScrollPane outputScrollPane = new JScrollPane(queryOutputTextArea);


        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureMediumButtons(acceptButton);
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        acceptButton.addActionListener(e -> {
            String type_max_min_average = getTextFromField("Type Max Min Average");

            if (type_max_min_average.isEmpty() || isInteger(type_max_min_average)) {
                showErrorMessage("Rentals from", type_max_min_average);
                return;
            }
            if(type_max_min_average.equals("Max")){
                queryOutputTextArea.setText(DataBase.GetMaxRentalDuration());
            }else if(type_max_min_average.equals("Min")){
                queryOutputTextArea.setText(DataBase.GetMinRentalDuration());
            }else {
                queryOutputTextArea.setText(DataBase.GetMedianRentalDuration());
            }

            JOptionPane.showConfirmDialog(frame, "Accepted!", "", JOptionPane.DEFAULT_OPTION);
        });
        arbitraryQuery.add(acceptButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        JLabel result = new JLabel("Result");
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        result.setFont(new Font("Arial", Font.PLAIN, 25));
        arbitraryQuery.add(result);

        // Text area to display rentals
        queryOutputTextArea.setFocusable(false);
        queryOutputTextArea.setPreferredSize(new Dimension(400, 600));
        arbitraryQuery.add(outputScrollPane);
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showQueriesPanel());
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the back button
        arbitraryQuery.add(backButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        setPanel(arbitraryQuery);
    }

    private void showMaintenanceCostsPanel() {
        JPanel arbitraryQuery = new JPanel();
        arbitraryQuery.setLayout(new BoxLayout(arbitraryQuery, BoxLayout.Y_AXIS));

        // Add some space at the top
        arbitraryQuery.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Get all rentals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the title
        arbitraryQuery.add(titleLabel);

        // Add space between title and separator
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        arbitraryQuery.add(separator);

        arbitraryQuery.add(Box.createVerticalStrut(20));

        arbitraryQuery.add(createInputLabelAndTextField("Service cost from"));
        arbitraryQuery.add(createInputLabelAndTextField("Service cost until"));

        // Text area to display rentals
        JTextArea queryOutputTextArea = new JTextArea();

        queryOutputTextArea.setAlignmentY(Component.TOP_ALIGNMENT);


        JScrollPane outputScrollPane = new JScrollPane(queryOutputTextArea);


        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureMediumButtons(acceptButton);
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        acceptButton.addActionListener(e -> {
            String service_cost_from = getTextFromField("Service cost from");
            String service_cost_until = getTextFromField("Service cost until");
            if (service_cost_from.isEmpty() || isInteger(service_cost_from)) {
                showErrorMessage("Rentals from", service_cost_from);
                return;
            }

            if (service_cost_until.isEmpty() || isInteger(service_cost_until)) {
                showErrorMessage("Rentals until", service_cost_until);
                return;
            }
            queryOutputTextArea.setText(DataBase.GetTotalServiceCost(service_cost_from, service_cost_until));
            JOptionPane.showConfirmDialog(frame, "Accepted!", "", JOptionPane.DEFAULT_OPTION);
        });
        arbitraryQuery.add(acceptButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        JLabel result = new JLabel("Result");
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        result.setFont(new Font("Arial", Font.PLAIN, 25));
        arbitraryQuery.add(result);

        // Text area to display rentals
        queryOutputTextArea.setFocusable(false);
        queryOutputTextArea.setPreferredSize(new Dimension(400, 600));
        arbitraryQuery.add(outputScrollPane);
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showQueriesPanel());
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the back button
        arbitraryQuery.add(backButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        setPanel(arbitraryQuery);
    }

    private void showPopularVehiclePanel() {
        JPanel arbitraryQuery = new JPanel();
        arbitraryQuery.setLayout(new BoxLayout(arbitraryQuery, BoxLayout.Y_AXIS));

        // Add some space at the top
        arbitraryQuery.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Get all rentals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the title
        arbitraryQuery.add(titleLabel);

        // Add space between title and separator
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        arbitraryQuery.add(separator);

        arbitraryQuery.add(Box.createVerticalStrut(20));



        // Text area to display rentals
        JTextArea queryOutputTextArea = new JTextArea();

        queryOutputTextArea.setAlignmentY(Component.TOP_ALIGNMENT);


        JScrollPane outputScrollPane = new JScrollPane(queryOutputTextArea);


        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Accept button
        JButton acceptButton = new JButton("Reveal the vehicles");
        configureMediumButtons(acceptButton);
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        acceptButton.addActionListener(e -> {
            queryOutputTextArea.setText(DataBase.GetMostFamousOfAllCategories());
            JOptionPane.showConfirmDialog(frame, "Accepted!", "", JOptionPane.DEFAULT_OPTION);
        });
        arbitraryQuery.add(acceptButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        JLabel result = new JLabel("Result");
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        result.setFont(new Font("Arial", Font.PLAIN, 25));
        arbitraryQuery.add(result);

        // Text area to display rentals
        queryOutputTextArea.setFocusable(false);
        queryOutputTextArea.setPreferredSize(new Dimension(400, 600));
        arbitraryQuery.add(outputScrollPane);
        arbitraryQuery.add(Box.createVerticalStrut(10));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showQueriesPanel());
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Set alignment for the back button
        arbitraryQuery.add(backButton);

        arbitraryQuery.add(Box.createVerticalStrut(10));

        setPanel(arbitraryQuery);
    }

    private void handleServiceDetails(JComboBox<String> dropdown) {
        String vehicle_id = getTextFromField("Vehicle Id");
        String start_date = getTextFromField("Start Date");
        String service_cost = getTextFromField("Service Cost");
        String end_date = getTextFromField("End Date");
        int penalty_cost = 0;

        if (vehicle_id.isEmpty() || !isInteger(vehicle_id)) {
            showErrorMessage("Vehicle Id", vehicle_id);
            return;
        }
        if (!DataBase.isValidVehicle(Integer.parseInt(vehicle_id))){
            JOptionPane.showMessageDialog(null, "Select a valid vehicle ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (start_date.isEmpty() || isInteger(start_date)) {
            showErrorMessage("Start Date", start_date);
            return;
        }
        if (service_cost.isEmpty() || !isInteger(service_cost)) {
            showErrorMessage("Service Cost", service_cost);
            return;
        }
        if (end_date.isEmpty() || isInteger(end_date)) {
            showErrorMessage("End Date", end_date);
            return;
        }
        if (dropdown.getSelectedItem() == "Damage")
            penalty_cost = DataBase.VehicleService(Integer.parseInt(vehicle_id), start_date, end_date,Integer.parseInt(service_cost), DataBase.ServiceType.Damage);
        else if (dropdown.getSelectedItem() == "Accident")
            penalty_cost = DataBase.VehicleService(Integer.parseInt(vehicle_id), start_date, end_date,Integer.parseInt(service_cost), DataBase.ServiceType.Accident);
        else if (dropdown.getSelectedItem() == "Maintenance")
            penalty_cost = DataBase.VehicleService(Integer.parseInt(vehicle_id), start_date, end_date,Integer.parseInt(service_cost), DataBase.ServiceType.Maintainance);

        if(penalty_cost == 0)
            JOptionPane.showConfirmDialog(frame, "Vehicle for Service accepted!", "", JOptionPane.DEFAULT_OPTION);
        else
            JOptionPane.showMessageDialog(frame, "Due to unpaid insurance, the customer has been charged an additional \"" + penalty_cost + "\" euros.","Insurance penalty!", JOptionPane.WARNING_MESSAGE);

    }

    private void showRentalsPanel() {
        JPanel showRentalsPanel = new JPanel();
        showRentalsPanel.setLayout(new BoxLayout(showRentalsPanel, BoxLayout.Y_AXIS));

        // Add some space at the top
        showRentalsPanel.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Show Rentals");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        showRentalsPanel.add(titleLabel);

        // Add space between title and separator
        showRentalsPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        showRentalsPanel.add(separator);

        showRentalsPanel.add(Box.createVerticalStrut(20));

        // Text area to display rentals
        try {
            JTextArea rentalsTextArea = new JTextArea(DataBase.GetAllRentedVehicles());

            rentalsTextArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(rentalsTextArea);
            scrollPane.setPreferredSize(new Dimension(400, 400));
            showRentalsPanel.add(scrollPane);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Could not get all vehicles");
        }

        showRentalsPanel.add(Box.createVerticalStrut(100));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showLoggedInAdminPanel());
        showRentalsPanel.add(backButton);

        showRentalsPanel.add(Box.createVerticalStrut(10));

        setPanel(showRentalsPanel);
    }

    private void showRentalInfoPanel(int category) {
        JPanel rentalInfoPanel = new JPanel();
        rentalInfoPanel.setLayout(new BoxLayout(rentalInfoPanel, BoxLayout.Y_AXIS));

        // Add some space at the top
        rentalInfoPanel.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Add Rental Info");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rentalInfoPanel.add(titleLabel);

        // Add space between title and separator
        rentalInfoPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        rentalInfoPanel.add(separator);

        rentalInfoPanel.add(Box.createVerticalStrut(10));

        try {
            JTextArea rentalsTextArea = new JTextArea();
            if(category == 1) rentalsTextArea.setText(DataBase.GetAllAvailableVehiclesByCat(category));
            else if(category == 2) rentalsTextArea.setText(DataBase.GetAllAvailableVehiclesByCat(category));
            else if(category == 3) rentalsTextArea.setText(DataBase.GetAllAvailableVehiclesByCat(category));
            else if(category == 4) rentalsTextArea.setText(DataBase.GetAllAvailableVehiclesByCat(category));
            else if(category == 5) rentalsTextArea.setText(DataBase.GetAllAvailableVehicles());

            rentalsTextArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(rentalsTextArea);
            scrollPane.setPreferredSize(new Dimension(400, 400));
            rentalInfoPanel.add(scrollPane);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Could not get all vehicles");
        }


        // Components for rental information
        rentalInfoPanel.add(createInputLabelAndTextField("Rental Date"));
        rentalInfoPanel.add(createInputLabelAndTextField("Rental Deadline"));
        rentalInfoPanel.add(createInputLabelAndTextField("Rental Duration"));
        rentalInfoPanel.add(createInputLabelAndTextField("Total Cost"));
        rentalInfoPanel.add(createInputLabelAndTextField("Vehicle ID"));
        rentalInfoPanel.add(createInputLabelAndTextField("Category ID"));
        rentalInfoPanel.add(createInputLabelAndTextField("Driver's License Number"));
        rentalInfoPanel.add(createInputLabelAndTextField("Birth Date"));

        // Add some space between input fields and buttons
        rentalInfoPanel.add(Box.createVerticalStrut(10));

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureBigButtons(acceptButton);

        acceptButton.addActionListener(e -> handleAcceptRentalDetails());

        rentalInfoPanel.add(acceptButton);

        // Add some space between buttons
        rentalInfoPanel.add(Box.createVerticalStrut(5));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showRentVehiclePanel());

        rentalInfoPanel.add(backButton);

        rentalInfoPanel.add(Box.createVerticalStrut(10));

        setPanel(rentalInfoPanel);
    }

    private void handleAcceptRentalDetails() {
        String rental_date = getTextFromField("Rental Date");
        String rental_deadline = getTextFromField("Rental Deadline");
        String rental_duration = getTextFromField("Rental Duration");
        String total_cost = getTextFromField("Total Cost");
        String vehicle_id = getTextFromField("Vehicle ID");
        String category_id = getTextFromField("Category ID");
        String drivers_license_number = getTextFromField("Driver's License Number");
        String birth_date = getTextFromField("Birth Date");


        if (rental_date.isEmpty() || isInteger(rental_date)) {
            showErrorMessage("Rental Date", rental_date);
            return;
        }
        if (rental_deadline.isEmpty() || isInteger(rental_deadline)) {
            showErrorMessage("Rental Deadline", rental_deadline);
            return;
        }
        if (rental_duration.isEmpty() || !isInteger(rental_duration)) {
            showErrorMessage("Rental Duration", rental_duration);
            return;
        }
        if (total_cost.isEmpty() || !isInteger(total_cost)) {
            showErrorMessage("Total Cost", total_cost);
            return;
        }
        if (vehicle_id.isEmpty() || !isInteger(vehicle_id)) {
            showErrorMessage("Vehicle ID", vehicle_id);
            return;
        }
        if (category_id.isEmpty() || !isInteger(category_id)) {
            showErrorMessage("Category ID", vehicle_id);
            return;
        }
        if (drivers_license_number.isEmpty() || isInteger(drivers_license_number)) {
            showErrorMessage("Driver's License Number", drivers_license_number);
            return;
        }
        if (birth_date.isEmpty() || isInteger(birth_date)) {
            showErrorMessage("Birth Date", birth_date);
            return;
        }
        Rental rental = new Rental(Date.valueOf(rental_date), Date.valueOf(rental_deadline), Integer.parseInt(rental_duration), Integer.parseInt(total_cost), Integer.parseInt(vehicle_id), Integer.parseInt(category_id), loggedInCustomerId,drivers_license_number, birth_date);
        JOptionPane.showConfirmDialog(frame, "Rental details accepted!", "", JOptionPane.DEFAULT_OPTION);
    }

    private void returnFromRental(){
        JPanel returnFromRentalPanel = new JPanel();
        returnFromRentalPanel.setLayout(new BoxLayout(returnFromRentalPanel, BoxLayout.Y_AXIS));

        // Add some space at the top
        returnFromRentalPanel.add(Box.createVerticalStrut(20));

        // Title
        JLabel titleLabel = new JLabel("Choose Vehicle To Return From Service");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        returnFromRentalPanel.add(titleLabel);

        // Add space between title and separator
        returnFromRentalPanel.add(Box.createVerticalStrut(10));

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        returnFromRentalPanel.add(separator);

        returnFromRentalPanel.add(Box.createVerticalStrut(-20));

        // Text area to display rentals
        JTextArea rentalsTextArea = new JTextArea();
        rentalsTextArea.setEditable(false);
        try {
            rentalsTextArea.setText(DataBase.GetAllVehiclesInService());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Couldn't get rented vehicles");
            showLoggedInAdminPanel();
        }
        JScrollPane scrollPane = new JScrollPane(rentalsTextArea);
        scrollPane.setPreferredSize(new Dimension(350, 100));
        returnFromRentalPanel.add(scrollPane);

        returnFromRentalPanel.add(createInputLabelAndTextField("Vehicle Id"));

        returnFromRentalPanel.add(Box.createVerticalGlue());

        // Accept button
        JButton acceptButton = new JButton("Accept");
        configureBigButtons(acceptButton);
        acceptButton.addActionListener(e -> handleReturnFromRental());
        returnFromRentalPanel.add(acceptButton);

        returnFromRentalPanel.add(Box.createVerticalStrut(5));

        // Back button
        JButton backButton = new JButton("Back");
        configureSmallButtons(backButton);
        backButton.addActionListener(e -> showLoggedInAdminPanel());
        returnFromRentalPanel.add(backButton);

        returnFromRentalPanel.add(Box.createVerticalStrut(10));

        setPanel(returnFromRentalPanel);
    }


    private void handleReturnFromRental() {
        String vehicle_id = getTextFromField("Vehicle Id");

        if (vehicle_id.isEmpty() || !isInteger(vehicle_id)) {
            showErrorMessage("Vehicle ID", vehicle_id);
            return;
        }

        DataBase.VehicleServiceFinished(Integer.parseInt(vehicle_id));
        JOptionPane.showConfirmDialog(frame, "Vehicle returned from service!", "", JOptionPane.DEFAULT_OPTION);
    }


    //------------------------------Additional helpful functions------------------------------

    private JPanel createInputLabelAndTextField(String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel labelComponent = new JLabel(label);
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 25));
        textField.setFont(textField.getFont().deriveFont(Font.PLAIN, 15));
        fieldMap.put(label, textField);
        panel.add(labelComponent);
        panel.add(textField);

        return panel;
    }

    private String getTextFromField(String fieldName) {
        JTextField textField = fieldMap.get(fieldName);
        return textField != null ? textField.getText() : "";
    }

    private void showErrorMessage(String fieldName,String field) {
        if(field.isEmpty()) JOptionPane.showMessageDialog(null, fieldName + " is required.", "Error", JOptionPane.ERROR_MESSAGE);
        else if(!isInteger(field))
            JOptionPane.showMessageDialog(null, fieldName + " must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, fieldName + " must be a string.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void configureBigButtons(JButton b){
        b.setFocusable(false);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(400,200));
        b.setPreferredSize(new Dimension(300,75));
        b.setFont(b.getFont().deriveFont(Font.BOLD, 16));
    }

    private void configureSmallButtons(JButton b){
        b.setFocusable(false);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(400,100));
        b.setPreferredSize(new Dimension(100,50));
        b.setFont(b.getFont().deriveFont(Font.BOLD, 16));
    }

    private void configureMediumButtons(JButton b){
        b.setFocusable(false);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(400,200));
        b.setPreferredSize(new Dimension(300,60));
        b.setFont(b.getFont().deriveFont(Font.BOLD, 16));
    }
}
//TODO:
//Create Service finished button and call the already finished function.
//input: vehicle_id
//return nothing