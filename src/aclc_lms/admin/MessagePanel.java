package aclc_lms.admin;

import aclc_lms.Helper;
import aclc_lms.Model;
import aclc_lms.ProgressBarPanel;
import aclc_lms.UserModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import aclc_lms.checkbox.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class MessagePanel extends javax.swing.JPanel {

    private UserModel user;
    private final Model model = new Model();
    private Helper helper;
    private JFrame parentFrame;
    
    private MouseAdapter mouseAdapter = null;
    private DefaultListModel mainListModel = new DefaultListModel();
    private DefaultListModel tempListModel = new DefaultListModel();
    
    public MessagePanel(UserModel user, JFrame frame) {
        initComponents();
        this.user = user;
        helper = new Helper(MessagePanel.class.getName());
        parentFrame = frame;
        
        txtSearch.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchContact(txtSearch.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                searchContact(txtSearch.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
               
            }
        });
                
        ResultSet resultSet = model.getAllEmployeeContact();  
        try {
            while (resultSet.next()) {
                CheckBoxListItem checkboxListItem = new CheckBoxListItem(resultSet.getString("fullname"));
                checkboxListItem.setpId(resultSet.getString("p_id"));
                mainListModel.addElement(checkboxListItem);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CheckBoxListItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        tempListModel = mainListModel;
        setContactListModel();
    }
    
    private void searchContact(String keyword) {
        tempListModel = new DefaultListModel(); 
        if (keyword.trim().equals("")) {
            tempListModel = mainListModel;
        } else {
            int mainListSize = mainListModel.getSize();
            for (int x=0; x<mainListSize; x++) {
                CheckBoxListItem mainItem = (CheckBoxListItem) mainListModel.getElementAt(x);
                if (mainItem.toString().contains(keyword))
                    tempListModel.addElement(mainItem);
            }
        }
        setContactListModel();
    }
    
    private void setContactListModel() {
        list.setModel(tempListModel);
        list.setCellRenderer(new CheckBoxListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        if (mouseAdapter != null)
            list.removeMouseListener(mouseAdapter);
        
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                JList<CheckBoxListItem> jList = (JList<CheckBoxListItem>) event.getSource();
                int index = jList.locationToIndex(event.getPoint());
                CheckBoxListItem item = (CheckBoxListItem) jList.getModel().getElementAt(index);
                item.setSelected(!item.isSelected());
                jList.repaint(jList.getCellBounds(index, index));
                
                int mainListSize = mainListModel.getSize();
                for (int x=0; x<mainListSize; x++) {
                    CheckBoxListItem mainItem = (CheckBoxListItem) mainListModel.getElementAt(index);
                    if (mainItem.getpId().equals(item.getpId()))
                        mainItem  = item;
                }
                
                int size = mainListModel.getSize(), count = 0;
                for (int i=0; i<size; i++) {
                    CheckBoxListItem checkItem = (CheckBoxListItem) mainListModel.getElementAt(i);
                    if (checkItem.isSelected())
                        count++;
                }
                selectAllCheck.setSelected(count == size);
            }
        };
        list.addMouseListener(mouseAdapter);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMsg = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        selectAllCheck = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        list = new javax.swing.JList();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setBackground(new java.awt.Color(255, 255, 255));

        txtMsg.setColumns(20);
        txtMsg.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMsg.setRows(5);
        jScrollPane1.setViewportView(txtMsg);

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SEND");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(153, 153, 153));
        jLabel26.setText("Write Message");
        jLabel26.setToolTipText("");

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(153, 153, 153));
        jLabel25.setText(" Search Employee");

        selectAllCheck.setBackground(new java.awt.Color(255, 255, 255));
        selectAllCheck.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        selectAllCheck.setForeground(java.awt.SystemColor.textHighlight);
        selectAllCheck.setText("Select All Employee");
        selectAllCheck.setFocusable(false);
        selectAllCheck.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectAllCheckItemStateChanged(evt);
            }
        });
        selectAllCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllCheckActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(list);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(selectAllCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectAllCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!txtMsg.getText().trim().equals("")) {
            int size = list.getModel().getSize();
            boolean isContactEmpty = true;
            ListModel listModel = list.getModel();
            for (int a=0; a<size; a++) {
                CheckBoxListItem item = (CheckBoxListItem) list.getModel().getElementAt(a);
                if (item.isSelected()) {
                    //helper.sendSms(item.toString(), txtMsg.getText());
                    String sql = "INSERT INTO message VALUES(message_id, '"+ txtMsg.getText() +"', '"+ helper.getCurrentDateTime() +"', 0, "+ item.getpId() +", "+ user.getpId() +")";
                    model.query(sql);
                    item.setSelected(false);
                }
            }
            JOptionPane.showMessageDialog(null, "Sms message successfully sent.");
            txtMsg.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a message.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void selectAllCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllCheckActionPerformed
        list.setModel(mainListModel);
        int size = list.getModel().getSize();
        for (int i=0; i<size; i++) {
            CheckBoxListItem item = (CheckBoxListItem) list.getModel().getElementAt(i);
            item.setSelected(selectAllCheck.isSelected()); 
            list.repaint(list.getCellBounds(i, i));
        }
    }//GEN-LAST:event_selectAllCheckActionPerformed

    private void selectAllCheckItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectAllCheckItemStateChanged
        //txtSearch.setEnabled(!selectAllCheck.isSelected());
        //txtSearch.setText("");
    }//GEN-LAST:event_selectAllCheckItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList list;
    private javax.swing.JCheckBox selectAllCheck;
    private javax.swing.JTextArea txtMsg;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
