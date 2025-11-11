import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GymSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GymSystem().createAndShowGUI());
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Gym System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new FlowLayout());

        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create Account");

        loginButton.addActionListener(e -> {
            frame.dispose();
            showLoginWindow();
        });

        createAccountButton.addActionListener(e -> {
            frame.dispose();
            showCreateAccountWindow();
        });

        frame.add(loginButton);
        frame.add(createAccountButton);
        frame.setVisible(true);
    }

    private void showLoginWindow() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 200);
        loginFrame.setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username: ");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password: ");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Simulate login check (can be replaced by actual DB check)
            if (authenticateUser(username, password)) {
                JOptionPane.showMessageDialog(loginFrame, "Login Successful");
                loginFrame.dispose();
                openUserDashboard(username); // Open the dashboard based on the user type
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.setVisible(true);
    }

    private void showCreateAccountWindow() {
        JFrame createAccountFrame = new JFrame("Create Account");
        createAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createAccountFrame.setSize(400, 300);
        createAccountFrame.setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username: ");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password: ");
        JPasswordField passwordField = new JPasswordField(20);
        JLabel nameLabel = new JLabel("Full Name: ");
        JTextField nameField = new JTextField(20);
        JLabel roleLabel = new JLabel("Select Role: ");
        String[] roles = { "Member", "Trainer", "Admin" };
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        JButton createAccountButton = new JButton("Create Account");

        createAccountButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String name = nameField.getText();
            String role = (String) roleComboBox.getSelectedItem();

            // Simulate account creation (can be replaced with actual DB insert)
            createAccount(username, password, name, role);
            JOptionPane.showMessageDialog(createAccountFrame, "Account Created Successfully!");
            createAccountFrame.dispose();
        });

        createAccountFrame.add(usernameLabel);
        createAccountFrame.add(usernameField);
        createAccountFrame.add(passwordLabel);
        createAccountFrame.add(passwordField);
        createAccountFrame.add(nameLabel);
        createAccountFrame.add(nameField);
        createAccountFrame.add(roleLabel);
        createAccountFrame.add(roleComboBox);
        createAccountFrame.add(createAccountButton);

        createAccountFrame.setVisible(true);
    }

    private boolean authenticateUser(String username, String password) {
        // Here you can add authentication logic with your database
        return "test".equals(username) && "password".equals(password);
    }

    private void createAccount(String username, String password, String name, String role) {
        // Here you can add logic to create a new user in your database
        System.out.println("Account Created: " + username + ", Role: " + role);
    }

    private void openUserDashboard(String username) {
        // Fetch user role from DB (example: 'member', 'trainer', 'admin')
        String userRole = "member"; // Assuming 'member' for simplicity

        if ("member".equals(userRole)) {
            openMemberDashboard(username);
        } else if ("trainer".equals(userRole)) {
            openTrainerDashboard(username);
        } else if ("admin".equals(userRole)) {
            openAdminDashboard(username);
        }
    }

    private void openMemberDashboard(String username) {
        JFrame memberFrame = new JFrame("Member Dashboard");
        memberFrame.setSize(600, 400);
        memberFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        memberFrame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + username, JLabel.RIGHT);
        memberFrame.add(welcomeLabel, BorderLayout.NORTH);

        // Add tabs or buttons for features: BMI, Calories, Workout Plan, etc.
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("BMI & Calories", new JPanel());  // You can add your calculator here
        tabbedPane.addTab("Workout Plan", new JPanel());  // Display workout plan
        tabbedPane.addTab("Diet Plan", new JPanel());  // Display diet plan

        memberFrame.add(tabbedPane, BorderLayout.CENTER);
        memberFrame.setVisible(true);
    }

    private void openTrainerDashboard(String username) {
        JFrame trainerFrame = new JFrame("Trainer Dashboard");
        trainerFrame.setSize(600, 400);
        trainerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trainerFrame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Trainer " + username, JLabel.RIGHT);
        trainerFrame.add(welcomeLabel, BorderLayout.NORTH);

        JButton addPlanButton = new JButton("Add Plan");
        addPlanButton.addActionListener(e -> {
            // Open form to add workout/diet plan for a member
        });

        trainerFrame.add(addPlanButton, BorderLayout.CENTER);
        trainerFrame.setVisible(true);
    }

    private void openAdminDashboard(String username) {
        JFrame adminFrame = new JFrame("Admin Dashboard");
        adminFrame.setSize(600, 400);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Admin " + username, JLabel.RIGHT);
        adminFrame.add(welcomeLabel, BorderLayout.NORTH);

        // Add buttons or tabs for Admin functionalities like managing products, checking user activity
        JButton manageProductsButton = new JButton("Manage Products");
        manageProductsButton.addActionListener(e -> {
            // Open form to manage products in the store
        });

        adminFrame.add(manageProductsButton, BorderLayout.CENTER);
        adminFrame.setVisible(true);
    }
}
