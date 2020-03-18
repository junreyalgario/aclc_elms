package aclc_lms.employee;

import aclc_lms.Helper;
import aclc_lms.Model;
import aclc_lms.ReasonRemarkPanel;
import aclc_lms.UserModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;

public class ReportPanel extends javax.swing.JPanel {
    
    private final Model model = new Model();
    private final Helper helper;
    private UserModel user;
    public JFrame dialogParentFrame;
    
    public ReportPanel(UserModel user, JFrame jFrame) {
        initComponents();
        helper = new Helper(ReportPanel.class.getName());
        this.user = user;
        dialogParentFrame = jFrame;
        helper.cellRenderer(tblLeave);
        
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
        
        loadMessage("all");
    }
    
    public void loadMessage(String mode) {
        TableModel leaveTableModel;
        String andConditon = "";
        if (mode.equals("search")) { 
            String search = txtSearch.getText();
            andConditon = andConditon +" AND (employee.f_name LIKE '%"+ search +"%' OR employee.l_name LIKE '%"+ search +"%' )";
        }
        leaveTableModel = model.getEmployeeLeaveTableReportModel(andConditon);
        // Update start date and end date column 
        int[] colIndex = {2, 3};// Put the column index of the start_date and end_date column
        leaveTableModel = model.updateStartEndDateColumn(leaveTableModel, colIndex);
        
        tblLeave.setShowGrid(false);
        tblLeave.setIntercellSpacing(new Dimension(0,0));

        tblLeave.setModel(leaveTableModel);
        tblLeave.getColumnModel().getColumn(0).setHeaderValue("#");
        tblLeave.getColumnModel().getColumn(1).setHeaderValue("Leave Type");
        tblLeave.getColumnModel().getColumn(2).setHeaderValue("Start Date");
        tblLeave.getColumnModel().getColumn(3).setHeaderValue("End Date");
        tblLeave.getColumnModel().getColumn(4).setHeaderValue("Reason");
        tblLeave.getColumnModel().getColumn(5).setHeaderValue("Remark");
        tblLeave.getColumnModel().getColumn(6).setHeaderValue("Status");
        tblLeave.getTableHeader().setBackground(Color.DARK_GRAY);
        tblLeave.getTableHeader().setForeground(Color.WHITE);
        tblLeave.getTableHeader().setPreferredSize(new Dimension(100, 40));
        tblLeave.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        tblLeave.getColumnModel().getColumn(0).setMaxWidth(40);
        tblLeave.getTableHeader().resizeAndRepaint();
        
        lblEmpId.setText(user.getEmployeeId());
        lblName.setText(user.getFullName());
        lblDepartment.setText(user.getDepartment());
        lblNoRec.setText(String.valueOf(leaveTableModel.getRowCount()));
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblLeave = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblEmpId = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblDepartment = new javax.swing.JLabel();
        jlbl = new javax.swing.JLabel();
        lblNoRec = new javax.swing.JLabel();

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
        tblLeave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLeaveMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblLeave);

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.SystemColor.activeCaptionBorder);
        jLabel2.setText("Filter");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Employee ID:");

        lblEmpId.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEmpId.setForeground(new java.awt.Color(51, 51, 51));
        lblEmpId.setText("CL1001");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Name:");

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(51, 51, 51));
        lblName.setText("BILL GATES");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Number of Records:");

        lblDepartment.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDepartment.setForeground(new java.awt.Color(51, 51, 51));
        lblDepartment.setText("COLLEGE");

        jlbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlbl.setForeground(new java.awt.Color(102, 102, 102));
        jlbl.setText("Department:");

        lblNoRec.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNoRec.setForeground(new java.awt.Color(51, 51, 51));
        lblNoRec.setText("23");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmpId)
                .addGap(40, 40, 40)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblName)
                .addGap(42, 42, 42)
                .addComponent(jlbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDepartment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNoRec))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblEmpId)
                    .addComponent(jLabel5)
                    .addComponent(lblName)
                    .addComponent(jLabel8)
                    .addComponent(lblDepartment)
                    .addComponent(jlbl)
                    .addComponent(lblNoRec))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblLeaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLeaveMouseClicked
        int row = tblLeave.getSelectedRow();
        String reason = tblLeave.getModel().getValueAt(row, 4).toString();
        String remark = tblLeave.getModel().getValueAt(row, 5).toString();
        String selectedRow = tblLeave.getModel().getValueAt(row, 0).toString();
        helper.dialogBuilder(dialogParentFrame, new ReasonRemarkPanel(reason, remark), "#"+ selectedRow, true);
    }//GEN-LAST:event_tblLeaveMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel jlbl;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblEmpId;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNoRec;
    private javax.swing.JTable tblLeave;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
