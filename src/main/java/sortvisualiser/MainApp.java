package sortvisualiser;

import sortvisualiser.screens.MainMenuScreen;
import sortvisualiser.screens.LoginScreen;
import sortvisualiser.screens.Screen;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import javax.swing.*;

public class MainApp {
    private final JFrame window;
    
    public static final int WIN_WIDTH = 1280;
    public static final int WIN_HEIGHT = 720;
    
    private final ArrayList<Screen> screens;
    
    public MainApp() {
        screens = new ArrayList<>();
        window = new JFrame ("Sort visualiser");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Image icon = Toolkit.getDefaultToolkit().getImage("path/to/icon.png");
        // window.setIconImage(icon);
        window.setVisible(true);
    }


    
    public Screen getCurrentScreen() {
        return screens.get(screens.size() - 1);
    }
    
    public void pushScreen(Screen screen) {
        if (!screens.isEmpty()) {
        	window.remove(getCurrentScreen());
        }
        screens.add(screen);
        window.setContentPane(screen);
        window.validate();
        screen.onOpen();
    }
    
    public void popScreen() {
        if (!screens.isEmpty()) {
            Screen prev = getCurrentScreen();
            screens.remove(prev);
            window.remove(prev);
            if (!screens.isEmpty()) {
            	Screen current = getCurrentScreen();
            	window.setContentPane(current);
            	window.validate();
                current.onOpen();
            }
            else {
                window.dispose();
            }
        }
    }
    
    public void start() {
        pushScreen(new LoginScreen(this));  // Show login screen first
        window.pack();
    }
    
    
    public static void main(String... args) {
        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(() -> {
            new MainApp().start();
        });
    }
}
