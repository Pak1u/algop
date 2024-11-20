package sortvisualiser.screens;

import sortvisualiser.MainApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class LoginScreen extends Screen {
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public LoginScreen(MainApp app) {
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
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::loginAction);
        
        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(e -> app.pushScreen(new SignupScreen(app)));
        
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(signupButton);
    }
    
    private void loginAction(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sortvisualiser", "root", "Vgncs12gh@");
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            
            if (result.next()) {
                // Successful login
                app.pushScreen(new MainMenuScreen(app));
            } else {
                // Invalid credentials
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onOpen() {
        // Reset form when screen is opened
        usernameField.setText("");
        passwordField.setText("");
    }
}
