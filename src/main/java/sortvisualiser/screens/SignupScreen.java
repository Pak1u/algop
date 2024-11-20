package sortvisualiser.screens;

import sortvisualiser.MainApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class SignupScreen extends Screen {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public SignupScreen(MainApp app) {
        super(app);
        setUpGUI();
    }

    private void setUpGUI() {
        // Set the layout for the screen
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        
        // Create a panel for the signup form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.DARK_GRAY);

        // Create GridBagConstraints for positioning components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components
        gbc.anchor = GridBagConstraints.WEST;

        // Add heading at the top
        JLabel headingLabel = new JLabel("SIGN UP");
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Set font size and style for the heading
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create a container panel for the heading and form to center everything
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(Color.DARK_GRAY);
        headingPanel.add(headingLabel);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Sign Up button
        JButton signupButton = new JButton("Sign Up");
        signupButton.setPreferredSize(new Dimension(100, 40)); // Set a preferred size for the button
        signupButton.addActionListener(this::signupAction);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(signupButton, gbc);

        // Center the form in the screen
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.DARK_GRAY);
        containerPanel.add(headingPanel, BorderLayout.NORTH);  // Add heading at the top
        containerPanel.add(formPanel, BorderLayout.CENTER);   // Add form panel below

        // Add the container panel to the main screen layout
        add(containerPanel, BorderLayout.CENTER);
    }

    private void signupAction(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Validate that both fields are not empty
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Establish MySQL connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sortvisualiser", "root", "Vgncs12gh@");
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User successfully created!");
                app.pushScreen(new LoginScreen(app)); // Navigate back to the login screen
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Could not sign up.");
        }
    }

    @Override
    public void onOpen() {
        // Clear fields when the screen is opened
        usernameField.setText("");
        passwordField.setText("");
    }
}
