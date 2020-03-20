package aclc_lms;

import aclc_lms.checkbox.CheckBoxListItem;
import aclc_lms.checkbox.CheckBoxListRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static java.util.Calendar.YEAR;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;

public class Helper {
    
    private final String className;

    public Helper(String className) {
        this.className = className;
    }
    
    public void logException(String ex, String type) {
        if (Config.isLogException){
            Logger.getLogger(className).logp(Level.OFF, type, type, ex);
            JOptionPane.showMessageDialog(null, type +": "+ ex);
        }
    }
    
    public void cellRenderer(JTable jtable) {
        jtable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, 
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
                Component c = super.getTableCellRendererComponent(table, 
                    value, isSelected, hasFocus, row, column);
                Color myWhite = new Color(245, 245, 240);
                ((DefaultTableCellRenderer)jtable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
                if(isSelected){
                    c.setBackground(new Color(51, 173, 255));
                }else {
                    c.setBackground(row%2==0 ? Color.WHITE : myWhite);
                }
                return c;
            };
        });
    }
    
    public java.sql.Date dateConvert(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
    
    public long getYearDiff(java.util.Date date1, java.util.Date date2) {
        Calendar calendar1 = getCalendar(dateConvert(date1));
        Calendar calendar2 = getCalendar(dateConvert(date2));
        int diff = calendar2.get(YEAR) - calendar1.get(YEAR);
        return diff;   
    }
    
    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.setTime(date);
        return calendar;
    }
    
    public long getDateDiff(java.util.Date date1, java.util.Date date2) {
        return (dateConvert(date2).getTime() - dateConvert(date1).getTime())/86400000;    
    }
    
    public static void showMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg);
        
    }
    
    public JDialog dialogBuilder(JFrame parentFrame, JPanel mainPanel, String title, boolean bool) {
        JDialog dialog = new JDialog(parentFrame, title, bool);
        dialog.getContentPane().add(mainPanel);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        return dialog;
    }
    
    public Date getCurrentDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date(System.currentTimeMillis());
        return dateobj;
    }
    
    public String getCurrentDateTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateobj = new Date(System.currentTimeMillis());
        return df.format(dateobj);
    }
    //dd MMMM yyyy = 02 January 2018
    public String getddMMyyyyObject(String dateObject) {
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
        java.util.Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateObject);
        } catch (ParseException ex) {
            return "Failed to parse string to date.";
        }
        return df.format(date);
    }
    
    public boolean sendSms(String contactNo, String message) {
        
        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost/api/send_sms.php?contact_no="+ contactNo + "&message="+ message;
        Request request = new Request.Builder().url(url).build();

        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string().equals("1");
        } catch (IOException ex) {
            this.logException("Failed to send sms.", ex.toString());
            return false;
        }
    }
    
    public void addWindowClosingEvent(JFrame jframe) {
        jframe.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close application?", "Close Application?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
                super.windowClosing(e);
            }
        });
    }
    
    public Password password = new Password();

    public class Password {
       
        private static final int iterations = 20*1000;
        private static final int saltLen = 32;
        private static final int desiredKeyLen = 256;

        public String getSaltedHash(String password) throws Exception {
            byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
            // store the salt with the password
            return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
        }

     
        public boolean check(String password, String stored) throws Exception{
            String[] saltAndHash = stored.split("\\$");
            if (saltAndHash.length != 2) {
                throw new IllegalStateException(
                    "The stored password must have the form 'salt$hash'");
            }
            String hashOfInput = hash(password, Base64.decodeBase64(saltAndHash[0]));
            return hashOfInput.equals(saltAndHash[1]);
        }

        private String hash(String password, byte[] salt) throws Exception {
            if (password == null || password.length() == 0)
                throw new IllegalArgumentException("Empty passwords are not supported.");
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, iterations, desiredKeyLen));
            return Base64.encodeBase64String(key.getEncoded());
        }
    }
}
