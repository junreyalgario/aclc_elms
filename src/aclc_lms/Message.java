package aclc_lms;

import aclc_lms.admin.MessagePanel;
import aclc_lms.employee.ReportPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;

public class Message extends javax.swing.JPanel {

    private final Model model = new Model();
    private final Helper helper;
    private final UserModel user;
    private final JFrame parentFrame;
    
    public Message(UserModel user, JFrame jframe) {
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
        String andConditon = "";
        if (mode.equals("search")) { 
            String search = txtSearch.getText(); // message.message_id, message.message, message.sent_date, CONCAT(employee.f_name, ' ', employee.l_name) as fullname
            andConditon = andConditon +" WHERE (message_id LIKE '%"+ search +"%' OR message LIKE '%"+ search +"%' OR sent_date LIKE '%"+ search +"%' OR f_name LIKE '%"+ search +"%' OR l_name LIKE '%"+ search +"%')";
        }
        msgTblModel = model.getMessageTblModel(andConditon);
        // Update start date and end date column 
        int[] colIndex = {2};// Put the column index of the start_date and end_date column
        msgTblModel = model.updateStartEndDateColumn(msgTblModel, colIndex);
        
        tblMsg.setShowGrid(false);
        tblMsg.setIntercellSpacing(new Dimension(0,0));

        tblMsg.setModel(msgTblModel);
        tblMsg.getColumnModel().getColumn(0).setHeaderValue("#");
        tblMsg.getColumnModel().getColumn(1).setHeaderValue("Message");
        tblMsg.getColumnModel().getColumn(2).setHeaderValue("Date Sent");
        tblMsg.getColumnModel().getColumn(3).setHeaderValue("To");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnAccept2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMsg = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setLayout(new java.awt.CardLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setForeground(java.awt.SystemColor.textHighlight);
        jLabel7.setText("MESSAGE");

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.SystemColor.activeCaptionBorder);
        jLabel2.setText("Filter");

        btnAccept2.setBackground(new java.awt.Color(0, 176, 40));
        btnAccept2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAccept2.setForeground(new java.awt.Color(255, 255, 255));
        btnAccept2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/add-list.png"))); // NOI18N
        btnAccept2.setText("NEW MESSAGE");
        btnAccept2.setFocusable(false);
        btnAccept2.setPreferredSize(new java.awt.Dimension(93, 40));
        btnAccept2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccept2ActionPerformed(evt);
            }
        });

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
        btnDelete.setFocusable(false);
        btnDelete.setPreferredSize(new java.awt.Dimension(93, 40));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
            .addGroup(layout.createSequentialGroup()
                .addGap(716, 716, 716)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAccept2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAccept2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAccept2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccept2ActionPerformed
        helper.dialogBuilder(parentFrame, new MessagePanel(user), "Message", true);
    }//GEN-LAST:event_btnAccept2ActionPerformed

    private void tblMsgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMsgMouseClicked
        
    }//GEN-LAST:event_tblMsgMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int i = tblMsg.getSelectedRow();
        String msgId = tblMsg.getModel().getValueAt(i, 0).toString();
        String sql = "DELETE FROM message WHERE message_id = "+ msgId;
        model.query(sql);
        loadMessage("");
        JOptionPane.showMessageDialog(null, "Message deleted.");
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept2;
    private javax.swing.JButton btnDelete;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblMsg;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
