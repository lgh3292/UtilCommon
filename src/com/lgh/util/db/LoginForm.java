/*
 * LoginForm.java
 *
 * Created on 2008��1��7��, ����9:21
 */
package com.lgh.util.db;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author  tewang
 */
public class LoginForm extends javax.swing.JFrame {

    private String[] dbURLs = {
        "jdbc:sqlserver://host:port;dataBaseName=name", "jdbc:mysql:// host/name?characterEncoding=utf-8&user=",
        "jdbc:mysql:// host/name?characterEncoding=utf-8&user=", "jdbc:oracle:thin:@host:port:name",
        "jdbc:db2://host:port/name", "jdbc:sybase:Tds:host:port/name",
        "jdbc:informix-sqli://host:port/name", "jdbc:postgresql://host/name",
        "jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=name"
    ,
                        }   ;
    private String[] dbs = {
        "SqlServer", "mysql1", "mysql2", "oracle", "db2", "sybase", "informix", "post", "access"};
    private String[] drivers = {
        "com.microsoft.jdbc.sqlserver.SQLServerDriver", "com.mysql.jdbc.Driver",
        "org.gjt.mm.mysql.Driver", "oracle.jdbc.driver.OracleDriver",
        "com.ibm.db2.jdbc.app.DB2Driver", "com.sybase.jdbc.SybDriver",
        "com.informix.jdbc.IfxDriver", "org.postgresql.Driver",
        "sun.jdbc.odbc.JdbcOdbcDriver"
    };

    /** Creates new form LoginForm */
    public LoginForm() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setLookAndFeel(this);
        for (String s : dbs) {
            nameComboBox.addItem(s);
        }
        for (String s : drivers) {
            urlComboBox.addItem(s);
        }
        nameComboBox.setSelectedIndex(0);
        urlComboBox.setSelectedIndex(0);
        urlJTextField.setText(dbURLs[0]);
    }

    private void doLogin() {
        try {
            String url = urlJTextField.getText();
            String userName = jTextField1.getText();
            char[] password = jPasswordField1.getPassword();
            Class.forName((String) urlComboBox.getSelectedItem());
            Connection con = DriverManager.getConnection(url, userName, new String(password));
            JOptionPane.showMessageDialog(null, "��¼�ɹ�");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setLookAndFeel(Component... c) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            for (Component cc : c) {
                SwingUtilities.updateComponentTreeUI(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        urlComboBox = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        nameComboBox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        urlJTextField = new javax.swing.JTextField();
        sureButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("connect url");

        jLabel2.setText("username:");

        jLabel3.setText("password:");

        urlComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                urlComboBoxItemStateChanged(evt);
            }
        });
        urlComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlComboBoxActionPerformed(evt);
            }
        });

        jLabel4.setText("Database name:");

        nameComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                nameComboBoxItemStateChanged(evt);
            }
        });
        nameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameComboBoxActionPerformed(evt);
            }
        });

        jLabel5.setText("Driver:");

        urlJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlJTextFieldActionPerformed(evt);
            }
        });

        sureButton.setText("sure");
        sureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sureButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nameComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(urlComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(urlJTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sureButton)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPasswordField1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))))
                .addGap(51, 51, 51))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(nameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(urlComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(urlJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(sureButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void urlComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlComboBoxActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_urlComboBoxActionPerformed

    private void nameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameComboBoxActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_nameComboBoxActionPerformed

    private void nameComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_nameComboBoxItemStateChanged

        if (evt.getStateChange() == ItemEvent.SELECTED && nameComboBox.getItemCount() > 0 && urlComboBox.getItemCount() > 0) {
            int index = nameComboBox.getSelectedIndex();
            if (index >= 0) {
                urlComboBox.setSelectedIndex(index);
            }
            urlJTextField.setText(dbURLs[index]);
        }
    }//GEN-LAST:event_nameComboBoxItemStateChanged

    private void urlComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_urlComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED && nameComboBox.getItemCount() > 0 && urlComboBox.getItemCount() > 0) {
            int index = urlComboBox.getSelectedIndex();
            if (index >= 0) {
                nameComboBox.setSelectedIndex(index);
            }
            urlJTextField.setText(dbURLs[index]);
        }
    // TODO add your handling code here:
    }//GEN-LAST:event_urlComboBoxItemStateChanged

    private void urlJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlJTextFieldActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_urlJTextFieldActionPerformed

    private void sureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sureButtonActionPerformed
        this.doLogin();
}//GEN-LAST:event_sureButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JComboBox nameComboBox;
    private javax.swing.JButton sureButton;
    private javax.swing.JComboBox urlComboBox;
    private javax.swing.JTextField urlJTextField;
    // End of variables declaration//GEN-END:variables
}