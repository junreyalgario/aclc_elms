package aclc_lms;

import java.awt.Color;
import java.awt.Component;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

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
}
