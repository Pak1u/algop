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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(20);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(20);
        
        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(this::signupAction);
        
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(signupButton);
    }
    
    private void signupAction(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sortvisualiser", "root", "Vgncs12gh@");
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User successfully created!");
                app.pushScreen(new LoginScreen(app)); // Go back to login screen
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Could not sign up.");
        }
    }

    @Override
    public void onOpen() {
        // Reset form when screen is opened
        usernameField.setText("");
        passwordField.setText("");
    }
}
