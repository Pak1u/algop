package sortvisualiser.screens;

import sortvisualiser.MainApp;
import sortvisualiser.SessionManager;
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
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.DARK_GRAY);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel headingLabel = new JLabel("LOGIN");
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 36));
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(Color.DARK_GRAY);
        headingPanel.add(headingLabel);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);
        
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);
        
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 40));
        loginButton.addActionListener(this::loginAction);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);
        
        JButton signupButton = new JButton("Sign Up");
        signupButton.setPreferredSize(new Dimension(100, 40));
        signupButton.addActionListener(e -> app.pushScreen(new SignupScreen(app)));
        gbc.gridy = 3;
        formPanel.add(signupButton, gbc);
        
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.DARK_GRAY);
        containerPanel.add(headingPanel, BorderLayout.NORTH);
        containerPanel.add(formPanel, BorderLayout.CENTER);
        
        add(containerPanel, BorderLayout.CENTER);
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
                // Set session for logged-in user
                SessionManager.setLoggedInUser(username);
                
                // Navigate to the MainMenuScreen
                app.pushScreen(new MainMenuScreen(app));
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onOpen() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
