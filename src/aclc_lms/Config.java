/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aclc_lms;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 *
 * @author Junrey Algario
 */
public class Config {
    // Db config
    public static String dbHostUrl = "jdbc:mysql://localhost/elms";
    public static String dbUsername = "root";
    public static String dbPassword = "";
    
    // GUI Components
    public Color formBgColor = Color.white;
    public Image appIconImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("asset/icon.png"));
    public ImageIcon logoImage = new ImageIcon("asset/icon.png");
    
    // Label
    public String appName = "Employee Leave Monitoring System";
    public String companyName = "ACLC";
    
    // Logging parameter
    public static boolean isLogException = true;

    // Set flat design UI
    public void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        UIManager.put( "Component.focusWidth", 2 );
        UIManager.put( "Button.arc", 0 );
        UIManager.put( "Component.arc", 0 );
    }
    
}
