package aclc_lms;
/**
 *
 * @author Junrey Algario
 */
public class ACLC_LMS {

    public static void main(String[] args) {
        
        new Config().setLookAndFeel();
        
        
        LoginForm loginForm = new LoginForm();
        loginForm.setLocationRelativeTo(null); 
        loginForm.setTitle("Login");
        loginForm.setVisible(true);
    }
    
}
