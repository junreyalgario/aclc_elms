package aclc_lms.admin;

import aclc_lms.Model;
import aclc_lms.UserModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import aclc_lms.checkbox.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;


public class MessagePanel extends javax.swing.JPanel {

    private UserModel user;
    private final Model model = new Model();
    
    public MessagePanel(UserModel user) {
        initComponents();
        this.user = user;
                
        ResultSet resultSet = model.getAllEmployeeContact();
       JList<CheckBoxListItem> list = new JList<>();
      
      DefaultListModel mod = new DefaultListModel();
    
      try {
           while (resultSet.next()) {
               CheckBoxListItem checkboxListItem = new CheckBoxListItem(resultSet.getString("fullname"));
               checkboxListItem.setpId(resultSet.getString("p_id"));
               mod.addElement(checkboxListItem);
           }} catch (SQLException ex) {
           Logger.getLogger(CheckBoxListItem.class.getName()).log(Level.SEVERE, null, ex);
       }
      
      list.setModel(mod);
      
 
      // Use a CheckboxListRenderer (see below)
      // to renderer list cells
 
      list.setCellRenderer(new CheckBoxListRenderer());
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 
      // Add a mouse listener to handle changing selection
 
      list.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent event) {
            JList<CheckBoxListItem> list =
               (JList<CheckBoxListItem>) event.getSource();
 
            // Get index of item clicked
 
            int index = list.locationToIndex(event.getPoint());
            CheckBoxListItem item = (CheckBoxListItem) list.getModel()
                  .getElementAt(index);
 
            // Toggle selected state
 
            item.setSelected(!item.isSelected());
 
            // Repaint cell
 
            list.repaint(list.getCellBounds(index, index));
            }
        });
        list.setBounds(8,30,150,280);
        contactPanel.add(list);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        contactPanel = new javax.swing.JPanel();
        selectAllCheck = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtFname = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setBackground(new java.awt.Color(255, 255, 255));

        contactPanel.setBackground(new java.awt.Color(255, 255, 255));
        contactPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.controlHighlight));

        selectAllCheck.setBackground(new java.awt.Color(255, 255, 255));
        selectAllCheck.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        selectAllCheck.setForeground(java.awt.SystemColor.textHighlight);
        selectAllCheck.setText("Select All");

        javax.swing.GroupLayout contactPanelLayout = new javax.swing.GroupLayout(contactPanel);
        contactPanel.setLayout(contactPanelLayout);
        contactPanelLayout.setHorizontalGroup(
            contactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectAllCheck, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addContainerGap())
        );
        contactPanelLayout.setVerticalGroup(
            contactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectAllCheck)
                .addContainerGap(301, Short.MAX_VALUE))
        );

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SEND");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(153, 153, 153));
        jLabel26.setText("Write Message");
        jLabel26.setToolTipText("");

        txtFname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(153, 153, 153));
        jLabel25.setText(" Search Employee");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(contactPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1)
                        .addComponent(jLabel26)
                        .addComponent(jLabel25)
                        .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(contactPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contactPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JCheckBox selectAllCheck;
    private javax.swing.JTextField txtFname;
    // End of variables declaration//GEN-END:variables
}
