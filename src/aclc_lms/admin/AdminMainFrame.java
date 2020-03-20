package aclc_lms.admin;

import aclc_lms.Config;
import aclc_lms.Helper;
import aclc_lms.LoginFrame;
import aclc_lms.Message;
import aclc_lms.Model;
import aclc_lms.PanelChangePassword;
import aclc_lms.ProfilePanel;
import aclc_lms.ReasonRemarkPanel;
import aclc_lms.UserModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Junrey Algario
 */
public final class AdminMainFrame extends javax.swing.JFrame {
    
    private final Config config = new Config();
    private final Helper helper = new Helper(AdminMainFrame.class.getName());
    private final Model model = new Model();
    public  UserModel user;
    private int prevTabMainIndex = 0, tabmainSelectedTabIndex = 0;
    private JFrame frame;

    public AdminMainFrame(UserModel user) {
        initComponents();
        initAll();
        this.user = user;
        // Load initial data to table 
        loadAllData();
        frame = this;
        helper.addWindowClosingEvent(frame);
        
    } 
    
    private void initAll() {
        this.setIconImage(config.appIconImage);
        this.getContentPane().setBackground(config.formBgColor);
        this.setTitle(config.companyName +" "+ config.appName +" - Admin");
        
        // Tabpane
        tabMain.setTitleAt(0, "EMPLOYEE");
        tabMain.setTitleAt(1, "LEAVE REQUEST");
        tabMain.setTitleAt(2, "REPORT");
        tabMain.setTitleAt(3, "MESSAGE");
                
        tabMain.addChangeListener((ChangeEvent e) -> {
            prevTabMainIndex = tabmainSelectedTabIndex;
            tabmainSelectedTabIndex = tabMain.getSelectedIndex();
            if (tabmainSelectedTabIndex == 0) {
                loadEmployeeData("all");
            } else if (tabmainSelectedTabIndex == 1) {
                loadLeaveData("all");
            } else if (tabmainSelectedTabIndex == 2) {
                if (tabPaneReport.getSelectedIndex() == 0) {
                    loadEmployeeReportData("all");
                } else if (tabPaneReport.getSelectedIndex() == 1) {
                    loadLeaveReportData("all");
                }
            } else if (tabmainSelectedTabIndex == 3) {
                tabMain.setSelectedIndex(prevTabMainIndex);
                helper.dialogBuilder(this, new Message(user, frame), "Message", true);
            }
        });
        
        tabPaneReport.setTitleAt(0, "EMPLOYEE REPORT");
        tabPaneReport.setTitleAt(1, "EMPLOYEE LEAVE REPORT");
        
        tabPaneReport.addChangeListener((ChangeEvent e) -> {
            int tabPaneReportSelectedTabIndex = tabPaneReport.getSelectedIndex();
            if (tabPaneReportSelectedTabIndex == 0) {
                loadEmployeeReportData("all");
            } else if (tabPaneReportSelectedTabIndex == 1) {
                loadLeaveReportData("all");
            }
        });
        
        // Set Tabpane header back color
        tabPaneReport.setBackground(Color.WHITE);
        tabMain.setBackground(Color.WHITE);

        
        // Init all table design
        helper.cellRenderer(tblEmployee);
        helper.cellRenderer(tblLeave);
        helper.cellRenderer(tblEmployeeReport);
        helper.cellRenderer(tblLeaveReport);
        
        // ---------------------------------------------------------------------
        // ---------------------------------------------------------------------
        // ---------------------------EMPLOYEE MODULE---------------------------
        
        txtSearchEmployee.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadEmployeeData("search");
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                loadEmployeeData("search");
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
               
            }
        });
        
        btnUpdate.setEnabled(false);
        
        
        // Table employee report search bar
        txtSearchEmployeeReport.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadEmployeeReportData("search");
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                loadEmployeeReportData("search");
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
               
            }
        });
        
        // Employee leave report module
        txtSearchLeaveReport.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadLeaveReportData("search");
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                loadLeaveReportData("search");
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
               
            }
        });
        
        // --------------------------- EMPLOYEE MODULE -------------------------
        // ---------------------------------------------------------------------
        // ---------------------------- LEAVE MODULE----------------------------
        
        btnAccept.setEnabled(false);
        btnReject.setEnabled(false);
        
        txtSearchLeaveRequest.getDocument().addDocumentListener(new DocumentListener(){
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
        
        disabledLeaveRequestFormFields();
        setComboBoxModel();
    }
    
    public void loadAllData() {
        // Load employee data to employee table
        loadEmployeeData("all");
        loadEmployeeReportData("all");
        loadLeaveData("all");
        loadLeaveReportData("all");
    }
    
    // EMPLOYEE LEAVE MODULE
    public void loadLeaveReportData(String mode) {
        TableModel leaveTableModel;
        if (mode.equals("all")) {
            leaveTableModel = model.getLeaveTableReportModel("");
        } else {
            String search = txtSearchLeaveReport.getText();
            String sqlCondition = " AND (leave_request.leave_request_id LIKE '%"+ search +"%' OR employee.employee_id LIKE '%"+ search +"%' OR employee.f_name LIKE '%"+ search +"%' OR employee.l_name LIKE '%"+ search +"%' OR employee.department LIKE '%"+ search +"%' OR leave_request.start_date LIKE '%"+ search +"%' OR leave_request.end_date LIKE '%"+ search +"%' OR leave_type.leave_name LIKE '%"+ search +"%' OR leave_request.reason LIKE '%"+ search +"%' OR leave_request.command LIKE '%"+ search +"%' OR leave_request.status LIKE '%"+ search +"%')";
            leaveTableModel = model.getLeaveTableReportModel(sqlCondition);
        }
        
        // Update start date and end date column 
        int[] colIndex = {5, 6};// Put the column index of the start_date and end_date column
        leaveTableModel = model.updateStartEndDateColumn(leaveTableModel, colIndex);
        
        tblLeaveReport.setShowGrid(false);
        tblLeaveReport.setIntercellSpacing(new Dimension(0,0));

        tblLeaveReport.setModel(leaveTableModel);
        tblLeaveReport.getColumnModel().getColumn(0).setHeaderValue("#");
        tblLeaveReport.getColumnModel().getColumn(1).setHeaderValue("Employee ID");
        tblLeaveReport.getColumnModel().getColumn(2).setHeaderValue("Department");
        tblLeaveReport.getColumnModel().getColumn(3).setHeaderValue("Fullname");
        tblLeaveReport.getColumnModel().getColumn(4).setHeaderValue("Leave Type");
        tblLeaveReport.getColumnModel().getColumn(5).setHeaderValue("Start Date");
        tblLeaveReport.getColumnModel().getColumn(6).setHeaderValue("End Date");
        tblLeaveReport.getColumnModel().getColumn(7).setHeaderValue("Reason");
        tblLeaveReport.getColumnModel().getColumn(8).setHeaderValue("Remark");
        tblLeaveReport.getColumnModel().getColumn(9).setHeaderValue("Status");
        tblLeaveReport.getTableHeader().setBackground(Color.DARK_GRAY);
        tblLeaveReport.getTableHeader().setForeground(Color.WHITE);
        tblLeaveReport.getTableHeader().setPreferredSize(new Dimension(100, 40));
        tblLeaveReport.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        tblLeaveReport.getColumnModel().getColumn(0).setMaxWidth(40);
        tblLeaveReport.getTableHeader().resizeAndRepaint();
    }

    @SuppressWarnings("unchecked")
    //---------------------------EMPLOYEE MODULE---------------------------
    // ---------------------------------------------------------------------
    // ------------------------------ LEVE MODULE------------------------------
    public void loadLeaveData(String mode) {
        TableModel leaveTableModel;
        if (mode.equals("all")) {
            leaveTableModel = model.getLeaveTableModel("");
        } else {
            String search = txtSearchLeaveRequest.getText();
            String sqlCondition = " AND (leave_request.leave_request_id LIKE '%"+ search +"%' OR employee.employee_id LIKE '%"+ search +"%' OR employee.f_name LIKE '%"+ search +"%' OR employee.l_name LIKE '%"+ search +"%' OR employee.department LIKE '%"+ search +"%' OR leave_request.start_date LIKE '%"+ search +"%' OR leave_request.end_date LIKE '%"+ search +"%' OR leave_type.leave_name LIKE '%"+ search +"%')";
            leaveTableModel = model.getLeaveTableModel(sqlCondition);
        }
        
        // Update start date and end date column 
        int[] colIndex = {5, 6};// Put the column index of the start_date and end_date column
        leaveTableModel = model.updateStartEndDateColumn(leaveTableModel, colIndex);
        
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
        tblLeave.getTableHeader().setBackground(Color.DARK_GRAY);
        tblLeave.getTableHeader().setForeground(Color.WHITE);
        tblLeave.getTableHeader().setPreferredSize(new Dimension(100, 40));
        tblLeave.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        tblLeave.getColumnModel().getColumn(0).setMaxWidth(40);
        tblLeave.getTableHeader().resizeAndRepaint();
        
        clearLeaveRequestFormFields();
        disabledLeaveRequestFormFields();
    }
    
    private void setComboBoxModel(){
       txtLeaveType.setModel(new DefaultComboBoxModel(model.getLeaveTypeComboBoxModel()));
    }
    
    private void disabledLeaveRequestFormFields() {
        txtStartDate.setEnabled(false);
        txtEndDate.setEnabled(false);
        txtCommand.setEnabled(false);
    }
    
    private void enabledLeaveRequestFormFields() {
        txtStartDate.setEnabled(true);
        txtEndDate.setEnabled(true);
        txtCommand.setEnabled(true);
    }
    
    private void clearLeaveRequestFormFields() {
        txtEmpId.setText("");
        txtFullname.setText("");
        txtDepartmentLeave.setText("");
        txtLeaveType.setSelectedIndex(0);
        txtStartDate.setDate(null);
        txtEndDate.setDate(null);
        txtReason.setText("");
        txtCommand.setText("");
        btnAccept.setEnabled(false);
        btnReject.setEnabled(false);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        tabMain = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtFname = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtLname = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtMname = new javax.swing.JTextField();
        txtGender = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        txtDob = new com.toedter.calendar.JDateChooser();
        jLabel29 = new javax.swing.JLabel();
        txtContactNo = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtDepartment = new javax.swing.JComboBox();
        jLabel38 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel39 = new javax.swing.JLabel();
        txtCpassword = new javax.swing.JPasswordField();
        jLabel40 = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmployee = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtSearchEmployee = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnUpdate5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLeave = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtFullname = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtEmpId = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtLeaveType = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        txtDepartmentLeave = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtEndDate = new com.toedter.calendar.JDateChooser();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtCommand = new javax.swing.JTextArea();
        jLabel37 = new javax.swing.JLabel();
        txtStartDate = new com.toedter.calendar.JDateChooser();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtReason = new javax.swing.JTextArea();
        jLabel42 = new javax.swing.JLabel();
        btnAccept = new javax.swing.JButton();
        btnReject = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSearchLeaveRequest = new javax.swing.JTextField();
        btnAccept1 = new javax.swing.JButton();
        btnUpdate4 = new javax.swing.JButton();
        tabPaneReport = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblEmployeeReport = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        txtSearchEmployeeReport = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblLeaveReport = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        txtSearchLeaveReport = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        msgPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuAccount = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        changePasswordMenu = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jScrollPane1.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusable(false);

        tabMain.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setForeground(java.awt.SystemColor.textHighlight);
        jLabel4.setText("EMPLOYEE DETAILS");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.controlHighlight));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel25.setText("First Name");

        txtFname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel26.setText("Last Name");

        txtLname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel27.setText("Middle Name");

        txtMname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtGender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel28.setText("Gender");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel29.setText("Date of Birth");

        txtContactNo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel30.setText("Contact Number");

        txtAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel36.setText("Address");

        txtDepartment.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDepartment.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "College", "Senior High" }));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel38.setText("Department");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel39.setText("Password");

        txtCpassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel40.setText("Confirm Password");

        btnClear.setBackground(java.awt.Color.orange);
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/times.png"))); // NOI18N
        btnClear.setText("CLEAR");
        btnClear.setFocusable(false);
        btnClear.setPreferredSize(new java.awt.Dimension(93, 40));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(0, 176, 40));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/add.png"))); // NOI18N
        btnAdd.setText("ADD");
        btnAdd.setFocusable(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(93, 40));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

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

        btnUpdate.setBackground(java.awt.SystemColor.textHighlight);
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/update.png"))); // NOI18N
        btnUpdate.setText("UPDATE");
        btnUpdate.setFocusable(false);
        btnUpdate.setPreferredSize(new java.awt.Dimension(93, 40));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jLabel36)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel25)
                                .addComponent(jLabel27))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel28)
                                .addComponent(jLabel26)
                                .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDob, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .addComponent(txtMname)
                                .addComponent(jLabel29))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtGender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtContactNo)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(jLabel30)
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addComponent(txtAddress)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel39)
                            .addGap(117, 117, 117)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGender)
                    .addComponent(txtMname, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtContactNo, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(txtDob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addGap(8, 8, 8)
                .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tblEmployee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Department", "Full Name", "Gender", "Date of Birth", "Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmployee.setGridColor(java.awt.SystemColor.textHighlight);
        tblEmployee.setRowHeight(35);
        tblEmployee.setRowMargin(0);
        tblEmployee.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEmployee.setShowHorizontalLines(false);
        tblEmployee.setShowVerticalLines(false);
        tblEmployee.getTableHeader().setReorderingAllowed(false);
        tblEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmployeeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblEmployee);
        if (tblEmployee.getColumnModel().getColumnCount() > 0) {
            tblEmployee.getColumnModel().getColumn(0).setResizable(false);
            tblEmployee.getColumnModel().getColumn(1).setResizable(false);
            tblEmployee.getColumnModel().getColumn(2).setResizable(false);
            tblEmployee.getColumnModel().getColumn(3).setResizable(false);
            tblEmployee.getColumnModel().getColumn(3).setHeaderValue("Gender");
            tblEmployee.getColumnModel().getColumn(4).setHeaderValue("Date of Birth");
            tblEmployee.getColumnModel().getColumn(5).setResizable(false);
            tblEmployee.getColumnModel().getColumn(5).setHeaderValue("Address");
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel5.setForeground(java.awt.SystemColor.controlShadow);
        jLabel5.setText("Filter");

        txtSearchEmployee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel6.setForeground(java.awt.SystemColor.textHighlight);
        jLabel6.setText("ALL EMPLOYEE");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/filter-24.png"))); // NOI18N

        btnUpdate5.setBackground(java.awt.SystemColor.textHighlight);
        btnUpdate5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate5.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/refresh.png"))); // NOI18N
        btnUpdate5.setText("RFRESH");
        btnUpdate5.setFocusable(false);
        btnUpdate5.setPreferredSize(new java.awt.Dimension(93, 40));
        btnUpdate5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 237, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnUpdate5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        tabMain.addTab("tab1", jPanel3);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblLeave.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblLeave.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Department", "Full Name", "Start Date", "End Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
        jScrollPane3.setViewportView(tblLeave);
        if (tblLeave.getColumnModel().getColumnCount() > 0) {
            tblLeave.getColumnModel().getColumn(0).setResizable(false);
            tblLeave.getColumnModel().getColumn(1).setResizable(false);
            tblLeave.getColumnModel().getColumn(2).setResizable(false);
            tblLeave.getColumnModel().getColumn(3).setResizable(false);
            tblLeave.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setForeground(java.awt.SystemColor.textHighlight);
        jLabel7.setText("LEAVE REQUEST DETAILS");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.controlHighlight));

        txtFullname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFullname.setEnabled(false);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel31.setText("Empoyee ID");

        txtEmpId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmpId.setEnabled(false);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel32.setText("Full Name");

        txtLeaveType.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel33.setText("Department");

        txtDepartmentLeave.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDepartmentLeave.setEnabled(false);

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel34.setText("Leave Type");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel35.setText("End Date");

        txtCommand.setColumns(20);
        txtCommand.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCommand.setRows(5);
        jScrollPane4.setViewportView(txtCommand);

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel37.setText("Start Date");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel41.setText("Reason");

        txtReason.setColumns(20);
        txtReason.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtReason.setRows(5);
        txtReason.setEnabled(false);
        jScrollPane5.setViewportView(txtReason);

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel42.setText("Remark");

        btnAccept.setBackground(new java.awt.Color(0, 176, 40));
        btnAccept.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAccept.setForeground(new java.awt.Color(255, 255, 255));
        btnAccept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/check.png"))); // NOI18N
        btnAccept.setText("ACCEPT");
        btnAccept.setFocusable(false);
        btnAccept.setPreferredSize(new java.awt.Dimension(93, 40));
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        btnReject.setBackground(new java.awt.Color(255, 51, 51));
        btnReject.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReject.setForeground(new java.awt.Color(255, 255, 255));
        btnReject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/times.png"))); // NOI18N
        btnReject.setText("REJECT");
        btnReject.setFocusable(false);
        btnReject.setPreferredSize(new java.awt.Dimension(93, 40));
        btnReject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRejectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReject, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(74, 74, 74)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtStartDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDepartmentLeave, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmpId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtLeaveType, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmpId, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDepartmentLeave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLeaveType, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReject, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(88, 88, 88))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setForeground(java.awt.SystemColor.textHighlight);
        jLabel8.setText("ALL LEAVE REQUEST");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel9.setForeground(java.awt.SystemColor.controlShadow);
        jLabel9.setText("Filter");

        txtSearchLeaveRequest.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnAccept1.setBackground(new java.awt.Color(0, 176, 40));
        btnAccept1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAccept1.setForeground(new java.awt.Color(255, 255, 255));
        btnAccept1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/add-list.png"))); // NOI18N
        btnAccept1.setText("LEAVE TYPE");
        btnAccept1.setFocusable(false);
        btnAccept1.setPreferredSize(new java.awt.Dimension(93, 40));
        btnAccept1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccept1ActionPerformed(evt);
            }
        });

        btnUpdate4.setBackground(java.awt.SystemColor.textHighlight);
        btnUpdate4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate4.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/refresh.png"))); // NOI18N
        btnUpdate4.setText("RFRESH");
        btnUpdate4.setFocusable(false);
        btnUpdate4.setPreferredSize(new java.awt.Dimension(93, 40));
        btnUpdate4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearchLeaveRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAccept1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearchLeaveRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAccept1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdate4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 535, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabMain.addTab("tab2", jPanel1);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel11.setForeground(java.awt.SystemColor.textHighlight);
        jLabel11.setText("EMPLOYEE REPORT");

        tblEmployeeReport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblEmployeeReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Department", "Full Name", "Gender", "Date of Birth", "Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmployeeReport.setGridColor(java.awt.SystemColor.textHighlight);
        tblEmployeeReport.setRowHeight(35);
        tblEmployeeReport.setRowMargin(0);
        tblEmployeeReport.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEmployeeReport.setShowHorizontalLines(false);
        tblEmployeeReport.setShowVerticalLines(false);
        tblEmployeeReport.getTableHeader().setReorderingAllowed(false);
        tblEmployeeReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmployeeReportMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblEmployeeReport);
        if (tblEmployeeReport.getColumnModel().getColumnCount() > 0) {
            tblEmployeeReport.getColumnModel().getColumn(0).setResizable(false);
            tblEmployeeReport.getColumnModel().getColumn(1).setResizable(false);
            tblEmployeeReport.getColumnModel().getColumn(2).setResizable(false);
            tblEmployeeReport.getColumnModel().getColumn(3).setResizable(false);
            tblEmployeeReport.getColumnModel().getColumn(3).setHeaderValue("Gender");
            tblEmployeeReport.getColumnModel().getColumn(4).setHeaderValue("Date of Birth");
            tblEmployeeReport.getColumnModel().getColumn(5).setResizable(false);
            tblEmployeeReport.getColumnModel().getColumn(5).setHeaderValue("Address");
        }

        jButton1.setBackground(java.awt.SystemColor.textHighlight);
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/refresh.png"))); // NOI18N
        jButton1.setText("REFRESH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtSearchEmployeeReport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.SystemColor.activeCaptionBorder);
        jLabel2.setText("Filter");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchEmployeeReport, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1223, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearchEmployeeReport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPaneReport.addTab("tab3", jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel12.setForeground(java.awt.SystemColor.textHighlight);
        jLabel12.setText("EMPLOYEE LEAVE REPORT");

        tblLeaveReport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblLeaveReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Department", "Full Name", "Gender", "Date of Birth", "Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLeaveReport.setGridColor(java.awt.SystemColor.textHighlight);
        tblLeaveReport.setRowHeight(35);
        tblLeaveReport.setRowMargin(0);
        tblLeaveReport.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblLeaveReport.setShowHorizontalLines(false);
        tblLeaveReport.setShowVerticalLines(false);
        tblLeaveReport.getTableHeader().setReorderingAllowed(false);
        tblLeaveReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLeaveReportMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblLeaveReport);
        if (tblLeaveReport.getColumnModel().getColumnCount() > 0) {
            tblLeaveReport.getColumnModel().getColumn(0).setResizable(false);
            tblLeaveReport.getColumnModel().getColumn(1).setResizable(false);
            tblLeaveReport.getColumnModel().getColumn(2).setResizable(false);
            tblLeaveReport.getColumnModel().getColumn(3).setResizable(false);
            tblLeaveReport.getColumnModel().getColumn(3).setHeaderValue("Gender");
            tblLeaveReport.getColumnModel().getColumn(4).setHeaderValue("Date of Birth");
            tblLeaveReport.getColumnModel().getColumn(5).setResizable(false);
            tblLeaveReport.getColumnModel().getColumn(5).setHeaderValue("Address");
        }

        jButton2.setBackground(java.awt.SystemColor.textHighlight);
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/refresh.png"))); // NOI18N
        jButton2.setText("REFRESH");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtSearchLeaveReport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(java.awt.SystemColor.activeCaptionBorder);
        jLabel3.setText("Filter");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchLeaveReport, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1223, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearchLeaveReport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPaneReport.addTab("tab3", jPanel8);

        tabMain.addTab("tab4", tabPaneReport);

        msgPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout msgPanelLayout = new javax.swing.GroupLayout(msgPanel);
        msgPanel.setLayout(msgPanelLayout);
        msgPanelLayout.setHorizontalGroup(
            msgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1248, Short.MAX_VALUE)
        );
        msgPanelLayout.setVerticalGroup(
            msgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        tabMain.addTab("tab4", msgPanel);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        menuAccount.setToolTipText("Account");
        menuAccount.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuAccount.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        menuAccount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuAccount.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuAccount.setLabel("ADMIN - JUNREY ALGARIO");
        menuAccount.setPreferredSize(new java.awt.Dimension(300, 30));

        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem4.setText("Profile");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        menuAccount.add(jMenuItem4);

        changePasswordMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        changePasswordMenu.setText("Change Password");
        changePasswordMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasswordMenuActionPerformed(evt);
            }
        });
        menuAccount.add(changePasswordMenu);

        jMenuItem3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem3.setText("Logout");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menuAccount.add(jMenuItem3);

        jMenuBar1.add(menuAccount);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMain, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMain, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // ---------------------------LEAVE MODULE---------------------------
    // ---------------------------------------------------------------------
    // ---------------------------EMPLOYEE MODULE---------------------------
    
    public void loadEmployeeData(String mode) {
        TableModel employeeModel;
        if (mode.equals("all")) {
            employeeModel = model.getEmployeeTableModel("");
        } else {
            String search = txtSearchEmployee.getText();
            String sqlCondition = " WHERE p_id LIKE '%"+ search +"%' OR employee_id LIKE '%"+ search +"%' OR f_name LIKE '%"+ search +"%' OR l_name LIKE '%"+ search +"%' OR m_name LIKE '%"+ search +"%' OR gender LIKE '%"+ search +"%' OR dob LIKE '%"+ search +"%' OR contact_no LIKE '%"+ search +"%' OR address LIKE '%"+ search +"%' OR department LIKE '%"+ search +"%'";
            employeeModel = model.getEmployeeTableModel(sqlCondition);
        }
        
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
        txtPassword.setEnabled(true);
        txtCpassword.setEnabled(true);
        
        tblEmployee.setShowGrid(false);
        tblEmployee.setIntercellSpacing(new Dimension(0,0));

        tblEmployee.setModel(employeeModel);
        tblEmployee.getColumnModel().getColumn(0).setHeaderValue("#");
        tblEmployee.getColumnModel().getColumn(1).setHeaderValue("Employee ID");
        tblEmployee.getColumnModel().getColumn(2).setHeaderValue("Department");
        tblEmployee.getColumnModel().getColumn(3).setHeaderValue("Fullname");
        tblEmployee.getColumnModel().getColumn(4).setHeaderValue("Gender");
        tblEmployee.getColumnModel().getColumn(5).setHeaderValue("Date of Birth");
        tblEmployee.getColumnModel().getColumn(6).setHeaderValue("Contact No.");
        tblEmployee.getColumnModel().getColumn(7).setHeaderValue("Address");
        tblEmployee.getTableHeader().setBackground(Color.DARK_GRAY);
        tblEmployee.getTableHeader().setForeground(Color.WHITE);
        tblEmployee.getTableHeader().setPreferredSize(new Dimension(100, 40));
        tblEmployee.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        tblEmployee.getColumnModel().getColumn(0).setMaxWidth(40);
        tblEmployee.getTableHeader().resizeAndRepaint();
        
        clearEmployeeFields();
    }
    
    public void loadEmployeeReportData(String mode) {
        TableModel employeeModel;
        if (mode.equals("all")) {
            employeeModel = model.getEmployeeTableModel("");
        } else {
            String search = txtSearchEmployeeReport.getText();
            String sqlCondition = " WHERE p_id LIKE '%"+ search +"%' OR employee_id LIKE '%"+ search +"%' OR f_name LIKE '%"+ search +"%' OR l_name LIKE '%"+ search +"%' OR m_name LIKE '%"+ search +"%' OR gender LIKE '%"+ search +"%' OR dob LIKE '%"+ search +"%' OR contact_no LIKE '%"+ search +"%' OR address LIKE '%"+ search +"%' OR department LIKE '%"+ search +"%'";
            employeeModel = model.getEmployeeTableModel(sqlCondition);
        }
        
        
        // Table employee report table
        
        tblEmployeeReport.setShowGrid(false);
        tblEmployeeReport.setIntercellSpacing(new Dimension(0,0));

        tblEmployeeReport.setModel(employeeModel);
        tblEmployeeReport.getColumnModel().getColumn(0).setHeaderValue("#");
        tblEmployeeReport.getColumnModel().getColumn(1).setHeaderValue("Employee ID");
        tblEmployeeReport.getColumnModel().getColumn(2).setHeaderValue("Department");
        tblEmployeeReport.getColumnModel().getColumn(3).setHeaderValue("Fullname");
        tblEmployeeReport.getColumnModel().getColumn(4).setHeaderValue("Gender");
        tblEmployeeReport.getColumnModel().getColumn(5).setHeaderValue("Date of Birth");
        tblEmployeeReport.getColumnModel().getColumn(6).setHeaderValue("Contact No.");
        tblEmployeeReport.getColumnModel().getColumn(7).setHeaderValue("Address");
        tblEmployeeReport.getTableHeader().setBackground(Color.DARK_GRAY);
        tblEmployeeReport.getTableHeader().setForeground(Color.WHITE);
        tblEmployeeReport.getTableHeader().setPreferredSize(new Dimension(100, 40));
        tblEmployeeReport.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        tblEmployeeReport.getColumnModel().getColumn(0).setMaxWidth(40);
        tblEmployeeReport.getTableHeader().resizeAndRepaint();
        
    }
    
    private boolean txtFieldValidation() {
        if (!txtFname.getText().trim().equals("") && !txtLname.getText().trim().equals("") && !txtMname.getText().trim().equals("") && !txtContactNo.getText().trim().equals("") && !txtFname.getText().trim().equals("") && !txtAddress.getText().trim().equals("")) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "All fields cannot be empty!");
            return false;
        }
    }
    
    private boolean isPasswordMatch() {
        if (!txtPassword.getText().trim().equals("") && !txtCpassword.getText().trim().equals("")) {
            if (txtPassword.getText().equals(txtCpassword.getText())) {
               return true;
            } else {
                JOptionPane.showMessageDialog(null, "Password and confirm password does not match! Try Again.");
                txtCpassword.setText("");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a password.");
            return false;
        }
    }
    
    private void clearEmployeeFields() {
        
        txtAddress.setText("");
        txtContactNo.setText("");
        txtCpassword.setText("");
        txtDepartment.setSelectedIndex(0);
        txtDob.setDate(null);
        txtFname.setText("");
        txtGender.setSelectedIndex(0);
        txtLname.setText("");
        txtMname.setText("");
        txtPassword.setText("");
        btnUpdate.setEnabled(false);
        btnAdd.setEnabled(true);
    }
    
    // Employee btn update
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (txtFieldValidation()) {
                int row = tblEmployee.getSelectedRow();
                String employeeId = tblEmployee.getModel().getValueAt(row, 0).toString();
                Date dob = helper.dateConvert(txtDob.getDate());
                String sql = "UPDATE employee SET f_name = '"+ txtFname.getText() +"',l_name = '"+ txtLname.getText() +"', m_name = '"+ txtMname.getText() +"', gender = '"+ txtGender.getSelectedItem().toString() +"', dob = '"+ dob +"', contact_no = '"+ txtContactNo.getText() +"', address  = '"+ txtAddress.getText() +"', department = '"+ txtDepartment.getSelectedItem().toString()+"' WHERE p_id = "+ employeeId;
                model.query(sql);
                loadEmployeeData("all");
                clearEmployeeFields();
                JOptionPane.showMessageDialog(null, "Successfulley updated data.");
                btnUpdate.setEnabled(false);
                btnAdd.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "All fields cannot be empty!");
            }
    }//GEN-LAST:event_btnUpdateActionPerformed
    // Employee btn Delete
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        
    }//GEN-LAST:event_btnDeleteActionPerformed
    // Employee btn Add
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (txtFieldValidation()) {
            if (isPasswordMatch()) {
                try {
                    if (helper.getYearDiff(txtDob.getDate(), helper.getCurrentDate()) < 18) {
                        JOptionPane.showMessageDialog(null, "Employee must be 18 years old.");
                        return;
                    }
                    String empId = model.generateEmployeeId(txtDepartment.getSelectedItem().toString());
                    Date dob = helper.dateConvert(txtDob.getDate());
                    
                    String password = helper.password.getSaltedHash(txtPassword.getText());
                    
                    String sql = "INSERT INTO employee VALUES(p_id, '"+ empId +"', '"+ txtFname.getText() +"', '"+ txtLname.getText() +"','"+ txtMname.getText() +"', '"+ txtGender.getSelectedItem().toString() +"', '"+ dob +"', '"+ txtContactNo.getText() +"', '"+ txtAddress.getText() +"', '"+ txtDepartment.getSelectedItem().toString()+"', '"+ password +"', '"+ helper.getCurrentDateTime() +"')";
                    model.query(sql);
                    loadEmployeeData("all");
                    clearEmployeeFields();
                    JOptionPane.showMessageDialog(null, "New employee data successfully save.");
                } catch (Exception ex) {
                    Logger.getLogger(AdminMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed
    // Employee btn Clear
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearEmployeeFields();
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
        txtPassword.setEnabled(true);
        txtCpassword.setEnabled(true);
    }//GEN-LAST:event_btnClearActionPerformed
    // Table employee row clicked
    private void tblEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmployeeMouseClicked
        
        int row = tblEmployee.getSelectedRow();
        String employeeId = tblEmployee.getModel().getValueAt(row, 0).toString();
        ResultSet employee = model.getEmployee(employeeId);
        
        btnAdd.setEnabled(false);
        btnUpdate.setEnabled(true);
        txtPassword.setEnabled(false);
        txtCpassword.setEnabled(false);
        
        try {
            if (employee.next()) {
                int txtGenderIndex = employee.getString("gender").equals("Male") ? 0 : 1;
                // Change to if else if you want to add more department
                int txtDepartmentIndex = employee.getString("department").equals("College") ? 0 : 1;
                
                txtFname.setText(employee.getString("f_name"));
                txtLname.setText(employee.getString("l_name"));
                txtMname.setText(employee.getString("m_name"));
                txtGender.setSelectedIndex(txtGenderIndex);
                txtDob.setDate(Date.valueOf(employee.getString("dob")));
                txtContactNo.setText(employee.getString("contact_no"));
                txtAddress.setText(employee.getString("address"));
                txtDepartment.setSelectedIndex(txtDepartmentIndex);
            }
        } catch (SQLException ex) {
            helper.logException(ex.toString(), "Failed to load employee data. An error has occured.");
        }
        //JOptionPane.showMessageDialog(null, "CLCIKE");
    }//GEN-LAST:event_tblEmployeeMouseClicked
    // Table leave request row click
    private void tblLeaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLeaveMouseClicked
        int row = tblLeave.getSelectedRow();
        String leaveId = tblLeave.getModel().getValueAt(row, 0).toString();
        ResultSet leaveRequest = model.getLeaveRequest(leaveId);
        
        btnAccept.setEnabled(true);
        btnReject.setEnabled(true);
        
        try {
            if (leaveRequest.next()) {
                // Set leave type combo box selected index
                enabledLeaveRequestFormFields();
                int size = txtLeaveType.getModel().getSize();                
                for (int i=0; i<size; i++) {
                    if (txtLeaveType.getModel().getElementAt(i).equals(leaveRequest.getString("leave_name"))) {
                        txtLeaveType.setSelectedIndex(i);
                        break;
                    }
                }
                
                txtEmpId.setText(leaveRequest.getString("employee_id"));
                txtFullname.setText(leaveRequest.getString("fullname"));
                txtDepartmentLeave.setText(leaveRequest.getString("department"));
                txtStartDate.setDate(Date.valueOf(leaveRequest.getString("start_date")));
                txtEndDate.setDate(Date.valueOf(leaveRequest.getString("end_date")));
                txtReason.setText(leaveRequest.getString("reason"));
                
            }
        } catch (SQLException ex) {
            helper.logException(ex.toString(), "Failed to load employee data. An error has occured.");
        }
    }//GEN-LAST:event_tblLeaveMouseClicked
    // Leave request module btn accept
    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        long dateDiff = helper.getDateDiff(txtStartDate.getDate(), txtEndDate.getDate());
        if (dateDiff > 0) {
            String leaveId = tblLeave.getModel().getValueAt(tblLeave.getSelectedRow(), 0).toString();
            String employeeId = tblLeave.getModel().getValueAt(tblLeave.getSelectedRow(), 1).toString();
            String name = tblLeave.getModel().getValueAt(tblLeave.getSelectedRow(), 3).toString();
            Date startDate = helper.dateConvert(txtStartDate.getDate());
            Date endDate = helper.dateConvert(txtEndDate.getDate());
            if (txtCommand.getText().trim().equals("")) {
                txtCommand.setText("No remark");
            }
            
            String contactNo = model.getEmployeeContactNo(employeeId);
            if (contactNo.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Failed to send sms notification. Employee has no mobile contact number.");
            } else {
                boolean temp = false;
                String msg = "Your leave application has been approved!";
                while (!temp) {
                    if (helper.sendSms(contactNo, msg)) {  
                        JOptionPane.showMessageDialog(null, "Sms notification has been sent to "+ name +".");
                        temp = true;
                    } else {
                        if (JOptionPane.showConfirmDialog(null, "Failed to send sms. Would you like to resend sms notification?", "Select Option", JOptionPane.YES_NO_OPTION) != 0) {
                            temp = true;
                        }
                    }
                }
            }
            String sql = "UPDATE leave_request SET seen = 1, command = '"+ txtCommand.getText() +"', status = 'Accepted', start_date = '"+ startDate +"', end_date = '"+ endDate +"' WHERE leave_request_id = "+ leaveId;
            model.query(sql);
            JOptionPane.showMessageDialog(null, "Leave request accepted.");
            loadLeaveData("all");
        } else {
            JOptionPane.showMessageDialog(null, "Leave request date duration is not valid. End date must be the next day from start date or onwards.");
        }
    }//GEN-LAST:event_btnAcceptActionPerformed
    //Leave request module btn reject
    private void btnRejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRejectActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel leave request?") == 0) {
            String leaveId = tblLeave.getModel().getValueAt(tblLeave.getSelectedRow(), 0).toString();
            if (txtCommand.getText().trim().equals("")) {
                txtCommand.setText("No remark");
            }
            String employeeId = tblLeave.getModel().getValueAt(tblLeave.getSelectedRow(), 1).toString();
            String name = tblLeave.getModel().getValueAt(tblLeave.getSelectedRow(), 3).toString();
            String contactNo = model.getEmployeeContactNo(employeeId);
            if (contactNo.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Failed to send sms notification. Employee has no mobile contact number.");
            } else {
                boolean temp = false;
                String msg = "Your leave application has been approved!";
                while (!temp) {
                    if (helper.sendSms(contactNo, msg)) {  
                        JOptionPane.showMessageDialog(null, "Sms notification has been sent to "+ name +".");
                        temp = true;
                    } else {
                        if (JOptionPane.showConfirmDialog(null, "Failed to send sms. Would you like to resend sms notification?", "Select Option", JOptionPane.YES_NO_OPTION) != 0) {
                            temp = true;
                        }
                    }
                }
            }
            String sql = "UPDATE leave_request SET status = 'Rejected', seen = 1, command = '"+ txtCommand.getText() +"' WHERE leave_request_id = "+ leaveId;
            model.query(sql);
            JOptionPane.showMessageDialog(null, "Leave request rejected.");
            loadLeaveData("all");
        }
    }//GEN-LAST:event_btnRejectActionPerformed

    private void btnAccept1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccept1ActionPerformed
        String input = JOptionPane.showInputDialog(null, "Enter new leave type ");
        if (input != null) {
            if (!input.trim().equals("")) {
                String sql = "INSERT INTO leave_type VALUES(leave_type_id, '"+ input +"')";
                model.query(sql);
                JOptionPane.showMessageDialog(null, "New leave type added.");
                loadLeaveData("all");
                setComboBoxModel();
            }
        }
    }//GEN-LAST:event_btnAccept1ActionPerformed

    private void btnUpdate4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate4ActionPerformed
        loadLeaveData("all");
    }//GEN-LAST:event_btnUpdate4ActionPerformed

    private void btnUpdate5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate5ActionPerformed
        loadEmployeeData("all");
    }//GEN-LAST:event_btnUpdate5ActionPerformed

    private void tblEmployeeReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmployeeReportMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblEmployeeReportMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadEmployeeReportData("all");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblLeaveReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLeaveReportMouseClicked
        int row = tblLeaveReport.getSelectedRow();
        String reason = tblLeaveReport.getModel().getValueAt(row, 7).toString();
        String remark = tblLeaveReport.getModel().getValueAt(row, 8).toString();
        String selectedRow = tblLeaveReport.getModel().getValueAt(row, 0).toString();
        helper.dialogBuilder(this, new ReasonRemarkPanel(reason, remark), "#"+ selectedRow, true);
    }//GEN-LAST:event_tblLeaveReportMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadLeaveReportData("all");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void changePasswordMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordMenuActionPerformed
        helper.dialogBuilder(this, new PanelChangePassword(user), "Change Password", true);
    }//GEN-LAST:event_changePasswordMenuActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to logout your account?", "Logout Account?", JOptionPane.YES_NO_OPTION) == 0) {
            user = new UserModel();
            this.dispose();
            LoginFrame loginForm = new LoginFrame();
            loginForm.setLocationRelativeTo(null);
            loginForm.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
       helper.dialogBuilder(this, new ProfilePanel(user), "Profile", true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new AdminMainFrame(new UserModel()).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnAccept1;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReject;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdate4;
    private javax.swing.JButton btnUpdate5;
    private javax.swing.JMenuItem changePasswordMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JMenu menuAccount;
    private javax.swing.JPanel msgPanel;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JTabbedPane tabPaneReport;
    private javax.swing.JTable tblEmployee;
    private javax.swing.JTable tblEmployeeReport;
    private javax.swing.JTable tblLeave;
    private javax.swing.JTable tblLeaveReport;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextArea txtCommand;
    private javax.swing.JTextField txtContactNo;
    private javax.swing.JPasswordField txtCpassword;
    private javax.swing.JComboBox txtDepartment;
    private javax.swing.JTextField txtDepartmentLeave;
    private com.toedter.calendar.JDateChooser txtDob;
    private javax.swing.JTextField txtEmpId;
    private com.toedter.calendar.JDateChooser txtEndDate;
    private javax.swing.JTextField txtFname;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JComboBox txtGender;
    private javax.swing.JComboBox txtLeaveType;
    private javax.swing.JTextField txtLname;
    private javax.swing.JTextField txtMname;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextArea txtReason;
    private javax.swing.JTextField txtSearchEmployee;
    private javax.swing.JTextField txtSearchEmployeeReport;
    private javax.swing.JTextField txtSearchLeaveReport;
    private javax.swing.JTextField txtSearchLeaveRequest;
    private com.toedter.calendar.JDateChooser txtStartDate;
    // End of variables declaration//GEN-END:variables
}
