package aclc_lms.checkbox;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CheckBoxListRenderer extends JCheckBox implements
        ListCellRenderer<aclc_lms.checkbox.CheckBoxListItem> {
 
    @Override
    public Component getListCellRendererComponent(
        JList<? extends aclc_lms.checkbox.CheckBoxListItem> list, aclc_lms.checkbox.CheckBoxListItem value,
        int index, boolean isSelected, boolean cellHasFocus) {
        setEnabled(list.isEnabled());
        setSelected(value.isSelected());
        setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(value.toString());
        return this;
   }
}
