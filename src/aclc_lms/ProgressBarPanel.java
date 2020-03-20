package aclc_lms;

import aclc_lms.checkbox.CheckBoxListItem;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

public class ProgressBarPanel extends javax.swing.JPanel {
    
    private DefaultListModel list = new DefaultListModel();

    public ProgressBarPanel(UserModel user, DefaultListModel list) {
        initComponents();
        
        this.list = list;
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        int size = list.getSize();
        sendMultipleSms(-1);
    }
    
    private void sendMultipleSms(int x) {
        x++;
        progressBar.setValue(0);
        if (x < list.getSize()) {
            CheckBoxListItem item = (CheckBoxListItem) list.getElementAt(x);
            final String to = item.toString();
            if (item.isSelected()) {
                lblLoading.setText("Sending SMS tp "+ to +" ...");
                updateBar(x, 0);
            } else {
                sendMultipleSms(x++);
            }
        }
    }
    
    private void updateBar(final int index, int percent) {
        percent++;
        final int finalPercent = percent;
        SwingUtilities.invokeLater(() -> {
            try {
                progressBar.setValue(finalPercent);
                java.lang.Thread.sleep(50);
                if (finalPercent == 100) {
                    sendMultipleSms(index);
                } else {
                    updateBar(index, finalPercent);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ProgressBarPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel26 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        lblLoading = new javax.swing.JLabel();

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(153, 153, 153));
        jLabel26.setText("Write Message");
        jLabel26.setToolTipText("");

        setBackground(new java.awt.Color(255, 255, 255));

        lblLoading.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblLoading.setForeground(new java.awt.Color(153, 153, 153));
        lblLoading.setText("Sending SMS to Junrey Algario ...");
        lblLoading.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLoading)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoading)
                .addContainerGap(32, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel26;
    public javax.swing.JLabel lblLoading;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
