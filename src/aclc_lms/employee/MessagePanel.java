package aclc_lms.employee;

import aclc_lms.Helper;
import aclc_lms.Model;
import aclc_lms.UserModel;
import aclc_lms.ViewMessage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;

public class MessagePanel extends javax.swing.JPanel {
    
    private final Model model = new Model();
    private final Helper helper;
    private final UserModel user;
    private final JFrame parentFrame;

    public MessagePanel(UserModel user, JFrame jframe) {
        initComponents();
        helper = new Helper(ReportPanel.class.getName());
        this.user = user;
        parentFrame = jframe;
        helper.cellRenderer(tblMsg);
        loadMessage("");
        
        txtSearch.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadMessage("search");
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                loadMessage("search");
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
               
            }
        });
    }
    
    private void loadMessage(String mode) {
        TableModel msgTblModel;
        String and = "";
        if (mode.equals("search")) { 
            String search = txtSearch.getText(); // message.message_id, message.message, message.sent_date, CONCAT(employee.f_name, ' ', employee.l_name) as fullname
            and = and +" AND (message_id LIKE '%"+ search +"%' OR message LIKE '%"+ search +"%' OR sent_date LIKE '%"+ search +"%' OR f_name LIKE '%"+ search +"%' OR l_name LIKE '%"+ search +"%')";
        }
        msgTblModel = model.getEmployeeMessageTblModel(and, user.getpId());
        // Update start date and end date column 
        int[] colIndex = {2};// Put the column index of the start_date and end_date column
        msgTblModel = model.updateStartEndDateColumn(msgTblModel, colIndex);
        
        tblMsg.setShowGrid(false);
        tblMsg.setIntercellSpacing(new Dimension(0,0));
        
        tblMsg.setModel(msgTblModel);
        tblMsg.getColumnModel().getColumn(0).setHeaderValue("#");
        tblMsg.getColumnModel().getColumn(1).setHeaderValue("Message");
        tblMsg.getColumnModel().getColumn(2).setHeaderValue("Date Sent");
        tblMsg.getColumnModel().getColumn(3).setHeaderValue("Sent By");
        tblMsg.getTableHeader().setBackground(Color.DARK_GRAY);
        tblMsg.getTableHeader().setForeground(Color.WHITE);
        tblMsg.getTableHeader().setPreferredSize(new Dimension(100, 40));
        tblMsg.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        tblMsg.getColumnModel().getColumn(0).setMaxWidth(40);
        tblMsg.getTableHeader().resizeAndRepaint();      
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMsg = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();

        m.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setLayout(new java.awt.CardLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setForeground(java.awt.SystemColor.textHighlight);
        jLabel7.setText("MESSAGE");

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.SystemColor.activeCaptionBorder);
        jLabel2.setText("Filter");

        tblMsg.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblMsg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblMsg.setGridColor(java.awt.SystemColor.textHighlight);
        tblMsg.setRowHeight(35);
        tblMsg.setRowMargin(0);
        tblMsg.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblMsg.setShowHorizontalLines(false);
        tblMsg.setShowVerticalLines(false);
        tblMsg.getTableHeader().setReorderingAllowed(false);
        tblMsg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblMsgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tblMsgFocusLost(evt);
            }
        });
        tblMsg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMsgMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblMsg);

        btnDelete.setBackground(new java.awt.Color(255, 0, 51));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/delete.png"))); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.setEnabled(false);
        btnDelete.setFocusable(false);
        btnDelete.setPreferredSize(new java.awt.Dimension(93, 40));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mLayout = new javax.swing.GroupLayout(m);
        m.setLayout(mLayout);
        mLayout.setHorizontalGroup(
            mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(mLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
            .addGroup(mLayout.createSequentialGroup()
                .addGap(716, 716, 716)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        mLayout.setVerticalGroup(
            mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 844, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 515, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int i = tblMsg.getSelectedRow();
        String msgId = tblMsg.getModel().getValueAt(i, 0).toString();
        String sql = "DELETE FROM message WHERE message_id = "+ msgId;
        model.query(sql);
        loadMessage("");
        btnDelete.setEnabled(false);
        JOptionPane.showMessageDialog(null, "Message deleted.");
        txtSearch.setFocusable(true);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblMsgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblMsgFocusLost
        btnDelete.setEnabled(false);
    }//GEN-LAST:event_tblMsgFocusLost

    private void tblMsgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblMsgFocusGained
        btnDelete.setEnabled(true);
    }//GEN-LAST:event_tblMsgFocusGained

    private void tblMsgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMsgMouseClicked
        int i = tblMsg.getSelectedRow();
        String msg = tblMsg.getModel().getValueAt(i, 1).toString();
        helper.dialogBuilder(parentFrame, new ViewMessage(msg), "Message", true);
    }//GEN-LAST:event_tblMsgMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel m;
    private javax.swing.JTable tblMsg;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
