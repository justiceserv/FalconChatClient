/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package falconchat_client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

/**
 *
 * @author kingj
 */
public class Login_Form extends javax.swing.JFrame {
    public Login_Form() {        
        initComponents();
        btnLogin.setOpaque(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        ID_input = new javax.swing.JTextField();
        PW_input = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client Login");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ID_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ID_inputActionPerformed(evt);
            }
        });
        getContentPane().add(ID_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 155, 261, 24));
        getContentPane().add(PW_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 246, 261, 24));

        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 120, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/falconclient.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ID_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ID_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ID_inputActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String id = ID_input.getText();
        String password = getMd5(new String(PW_input.getPassword()));
        String msgout = "L#" + id + "#" + password + "#";
        String request = "L#" + id;
        int retry = 0;
        boolean isConnected = false;
        Socket socket = null;
        while (isConnected == false && retry < 10) {
            System.out.println("[client] login retry " + retry);
            try
            {
                btnLogin.setEnabled(false);
                socket = new Socket();
                SocketAddress socketAddress = new InetSocketAddress("localhost", 1000);
                socket.connect(socketAddress, 200);
                isConnected = true;
                System.out.println(socket);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                if(id != null || password != null)
                {
                    bw.write(msgout);
                    bw.newLine();
                    bw.flush();
                    System.out.println(msgout);
                    while (true) {
                        try {
                            String loginResponse = br.readLine();
                           
                                System.out.println(loginResponse);
                                if(loginResponse.startsWith(request + "#success#"))
                                {
                                    String[] loginResponseArguments = loginResponse.split("#");
                                    int portnumber = Integer.parseInt(loginResponseArguments[3]);
                                    this.setVisible(false);
                                    MainForm mf = new MainForm(id, portnumber);
                                    mf.setVisible(true);
                                    mf.start();
                                }
                                else if(loginResponse.equals(request + "#failed#"))
                                {
                                    JOptionPane.showMessageDialog(null, "Login Failed! Check your credential.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Server Error. Please check server.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } //check whether user input 
                else
                {
                    JOptionPane.showMessageDialog(null, "Plase input all your credentials!", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
                    btnLogin.setEnabled(true);
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                        System.out.println("[client] login socket closing!");
                    }   
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            retry++;
        }
        if(retry >= 10)
        {
            btnLogin.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Server hasn't responded for 10 attempts. Please try later.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
        }
        if (isConnected == false) {
            JOptionPane.showMessageDialog(null, "You wasn't connected with server!", "InfoBox: ERROR", JOptionPane.INFORMATION_MESSAGE);    
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login_Form().setVisible(true);
            }
        });
    }
public static String getMd5(String input) 
    { 
        try { 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            byte[] messageDigest = md.digest(input.getBytes()); 
            BigInteger no = new BigInteger(1, messageDigest); 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ID_input;
    private javax.swing.JPasswordField PW_input;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
