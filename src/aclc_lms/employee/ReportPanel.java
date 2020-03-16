package aclc_lms.employee;

import aclc_lms.Helper;
import aclc_lms.Model;
import aclc_lms.UserModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;

public class ReportPanel extends javax.swing.JPanel {
    
    private final Model model = new Model();
    private final Helper helper;
    private UserModel user;
    
    public ReportPanel(UserModel user) {
        initComponents();
        helper = new Helper(ReportPanel.class.getName());
        this.user = user;
        helper.cellRenderer(tblLeave);
        
        txtSearch.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadLeaveData("search");
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                loadLeaveData("search");
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
               
            }
        });
        
        loadLeaveData("all");
    }
    
    public void loadLeaveData(String mode) {
        TableModel leaveTableModel;
        String andConditon = "AND leave_request.p_id = "+ user.getpId();
        if (mode.equals("search")) { 
            String search = txtSearch.getText();
            andConditon = andConditon +" AND (leave_request.leave_request_id LIKE '%"+ search +"%' OR employee.employee_id LIKE '%"+ search +"%' OR employee.f_name LIKE '%"+ search +"%' OR employee.l_name LIKE '%"+ search +"%' OR employee.department LIKE '%"+ search +"%' OR leave_request.start_date LIKE '%"+ search +"%' OR leave_request.end_date LIKE '%"+ search +"%' OR leave_type.leave_name LIKE '%"+ search +"%')";
        }
        leaveTableModel = model.getLeaveTableReportModel(andConditon);
        
        tblLeave.setShowGrid(false);
        tblLeave.setIntercellSpacing(new Dimension(0,0));

        tblLeave.setModel(leaveTableModel);
        tblLeave.getColumnModel().getColumn(0).setHeaderValue("#");
        tblLeave.getColumnModel().getColumn(1).setHeaderValue("Employee ID");
        tblLeave.getColumnModel().getColumn(2).setHeaderValue("Department");
        tblLeave.getColumnModel().getColumn(3).setHeaderValue("Fullname");
        tblLeave.getColumnModel().getColumn(4).setHeaderValue("Leave Type");
        tblLeave.getColumnModel().getColumn(5).setHeaderValue("Start Date");
        tblLeave.getColumnModel().getColumn(6).setHeaderValue("End Date");
        tblLeave.getColumnModel().getColumn(7).setHeaderValue("Reason");
        tblLeave.getColumnModel().getColumn(8).setHeaderValue("Remark");
        tblLeave.getColumnModel().getColumn(9).setHeaderValue("Status");
        tblLeave.getTableHeader().setBackground(Color.DARK_GRAY);
        tblLeave.getTableHeader().setForeground(Color.WHITE);
        tblLeave.getTableHeader().setPreferredSize(new Dimension(100, 40));
        tblLeave.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        tblLeave.getColumnModel().getColumn(0).setMaxWidth(40);
        tblLeave.getTableHeader().resizeAndRepaint();
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblLeave = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setForeground(java.awt.SystemColor.textHighlight);
        jLabel7.setText("LEAVE REPORT");

        tblLeave.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblLeave.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Department", "Full Name", "Gender", "Date of Birth", "Address", "Reason", "Remark", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLeave.setGridColor(java.awt.SystemColor.textHighlight);
        tblLeave.setRowHeight(35);
        tblLeave.setRowMargin(0);
        tblLeave.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblLeave.setShowHorizontalLines(false);
        tblLeave.setShowVerticalLines(false);
        tblLeave.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tblLeave);

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.SystemColor.activeCaptionBorder);
        jLabel2.setText("Filter");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane8)
                .addGap(22, 22, 22))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable tblLeave;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
