package aclc_lms;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
/**
 *
 * @author Junrey Algario
 */
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
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        return dialog;
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
    
    public void sendSms(String contactNo, String message) {
        
        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost/api/send_sms.php?contact_no="+ contactNo + "&message="+ message;
        Request request = new Request.Builder().url(url).build();

        Response response;
        try {
            response = client.newCall(request).execute();
            JOptionPane.showMessageDialog(null, response.body().string());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed okhhtp");
            this.logException("FAILED OKHTTP", ex.toString());
        }
    }
}
