package aclc_lms;

public class ACLC_LMS {

    public static void main(String[] args) {
        
        new Config().setLookAndFeel();
        
        
        LoginFrame loginForm = new LoginFrame();
        loginForm.setLocationRelativeTo(null); 
        loginForm.setTitle("Login");
        loginForm.setVisible(true);
    }
    
}
