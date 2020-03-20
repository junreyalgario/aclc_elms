package aclc_lms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class LoadingPanel extends javax.swing.JPanel {
    
    private final CallbackHandler callback;
    private final Helper helper;
    private String[] contact;
    private String mode, message;
    
    private int loadingPercent = 0;

    public LoadingPanel(UserModel user, String[] contact, String message, String mode, CallbackHandler callback) {
        initComponents();
        helper = new Helper(LoadingPanel.class.getName());
        pnlStatus.setVisible(false);
        
        this.callback = callback;
        this.contact = contact;
        this.message = message;
        
        
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        
        if (mode.equals("multiple")) {
            
        } else {
            // Contact[0] = contact number
            // Contact[1] = contact name            
            updateBar();
            lblLoading.setText("Sending SMS to "+ this.contact[1] +" ...");
            if (helper.sendSms(this.contact[0], message)) {
                loadingPercent = 100;
                lblStatus.setText("Sms notification successfully sent to "+ contact[1] +" ...");
                pnlLoading.setVisible(false);
                pnlStatus.setVisible(true);
            }
        }
    }
    
    private void updateBar() {
        if (loadingPercent < 90) {
            SwingUtilities.invokeLater(() -> {
                try {
                    progressBar.setValue(loadingPercent);
                    java.lang.Thread.sleep(50);
                    loadingPercent++;
                    updateBar();
                } catch (InterruptedException ex) {
                   Logger.getLogger(ProgressBarPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlStatus = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        pnlLoading = new javax.swing.JPanel();
        lblLoading = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        setBackground(new java.awt.Color(255, 255, 255));

        pnlStatus.setBackground(new java.awt.Color(255, 255, 255));

        lblStatus.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(51, 51, 51));
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText("Sms notification successfully sent to Junrey Algario.");
        lblStatus.setToolTipText("");

        javax.swing.GroupLayout pnlStatusLayout = new javax.swing.GroupLayout(pnlStatus);
        pnlStatus.setLayout(pnlStatusLayout);
        pnlStatusLayout.setHorizontalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
        );
        pnlStatusLayout.setVerticalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatusLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lblStatus)
                .addGap(51, 51, 51))
        );

        pnlLoading.setBackground(new java.awt.Color(255, 255, 255));

        lblLoading.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblLoading.setForeground(new java.awt.Color(51, 51, 51));
        lblLoading.setText("Sending SMS to Bill Gates ...");
        lblLoading.setToolTipText("");

        javax.swing.GroupLayout pnlLoadingLayout = new javax.swing.GroupLayout(pnlLoading);
        pnlLoading.setLayout(pnlLoadingLayout);
        pnlLoadingLayout.setHorizontalGroup(
            pnlLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoadingLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(lblLoading)
                .addContainerGap(263, Short.MAX_VALUE))
            .addGroup(pnlLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlLoadingLayout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(58, Short.MAX_VALUE)))
        );
        pnlLoadingLayout.setVerticalGroup(
            pnlLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoadingLayout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(lblLoading)
                .addGap(33, 33, 33))
            .addGroup(pnlLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlLoadingLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(50, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnlLoading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlLoading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel lblLoading;
    public javax.swing.JLabel lblStatus;
    private javax.swing.JPanel pnlLoading;
    private javax.swing.JPanel pnlStatus;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
