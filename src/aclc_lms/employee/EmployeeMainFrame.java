package aclc_lms.employee;

import aclc_lms.Config;
import aclc_lms.Helper;
import aclc_lms.LoginFrame;
import aclc_lms.PanelChangePassword;
import aclc_lms.ProfilePanel;
import aclc_lms.UserModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EmployeeMainFrame extends javax.swing.JFrame {

    private final Config config = new Config();
    private final Helper helper;
    public UserModel user;
    private final JFrame parentFrame;
    
    public EmployeeMainFrame(UserModel user) {
        initComponents();
        
        this.user = user;
        parentFrame = this;
        
        this.setIconImage(config.appIconImage);
        this.getContentPane().setBackground(config.formBgColor);
        this.setTitle(config.companyName +" "+ config.appName +" - Staff");
        helper = new Helper(EmployeeMainFrame.class.getName());
        lblUser.setText("Welcome "+ user.getFullName() +"!");
        
        JFrame frame = this;
        helper.addWindowClosingEvent(frame);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblCompanyName = new javax.swing.JLabel();
        lblLoginTitle1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuAccount = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        changePasswordMenu = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar2.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar2.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aclc_lms/asset/icon.png"))); // NOI18N

        lblUser.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblUser.setText("Welcome  User!");

        lblCompanyName.setFont(new java.awt.Font("Yu Gothic", 1, 48)); // NOI18N
        lblCompanyName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCompanyName.setText("ACLC");

        lblLoginTitle1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblLoginTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoginTitle1.setText("Employee Leave Monitoring System");
        lblLoginTitle1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenu4.setText("MESSAGE");
        jMenu4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu4);

        jMenu3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jMenu3.setLabel("LEAVE");

        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem2.setText("Apply");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem4.setText("Status");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu5.setText("REPORT");
        jMenu5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu5);

        menuAccount.setText("ACCOUNT");
        menuAccount.setToolTipText("Account");
        menuAccount.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuAccount.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        menuAccount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuAccount.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuAccount.setPreferredSize(new java.awt.Dimension(77, 30));

        jMenuItem6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem6.setText("Profile");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        menuAccount.add(jMenuItem6);

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
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblLoginTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, 948, Short.MAX_VALUE)
            .addComponent(lblCompanyName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoginTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        helper.dialogBuilder(this, new ApplyPanel(user), "Leave Apply", true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    // Show leave status panel
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        helper.dialogBuilder(this, new StatusPanel(user), "Leave Status", true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
        helper.dialogBuilder(this, new ReportPanel(user, this), "Leave Report", true);
    }//GEN-LAST:event_jMenu5MouseClicked

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        helper.dialogBuilder(this, new ProfilePanel(user), "Profile", true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
       helper.dialogBuilder(this, new MessagePanel(user, parentFrame), "Message", true);
    }//GEN-LAST:event_jMenu4MouseClicked


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new EmployeeMainFrame(new UserModel()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem changePasswordMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JLabel lblCompanyName;
    private javax.swing.JLabel lblLoginTitle1;
    private javax.swing.JLabel lblUser;
    private javax.swing.JMenu menuAccount;
    // End of variables declaration//GEN-END:variables
}
