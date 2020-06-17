package falconchat_client;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class Contact_Form extends javax.swing.JFrame {
    private String username;
    static ArrayList<String> friends = new ArrayList();
    static DefaultListModel friendsModel = new DefaultListModel();
    private int currentEntry;
    private int DeleteIndex;
    public Contact_Form(String username) {
        initComponents();
        btnAdd.setOpaque(false);
        btnAdd.setContentAreaFilled(false);
        btnAdd.setBorderPainted(false);
        btnRemove.setOpaque(false);
        btnRemove.setContentAreaFilled(false);
        btnRemove.setBorderPainted(false);
        this.username = username;
        loadData(); 
    }
    
    private void loadData() {
        try {
            File friendsFile = new File("db/friends_" + username);
            friendsFile.createNewFile();
            FileInputStream f = new FileInputStream(friendsFile);
            ObjectInputStream o = new ObjectInputStream(f);
            friends = (ArrayList<String>) o.readObject();
            friendsModel = new DefaultListModel();
            for (int i = 0; i < friends.size(); ++i) {
                friendsModel.add(i, friends.get(i));
            }
            jList1.setModel(friendsModel);
            o.close();
            f.close();
        } catch (EOFException eof) {
            System.out.print("EOF exception occur");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveData() {
        try {
            File friendsFile = new File("db/friends_" + username);
            friendsFile.createNewFile();
            FileOutputStream f = new FileOutputStream(friendsFile, false);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(friends);
            o.close();
            f.close();
        } catch (EOFException eof) {
            System.out.print("EOF exception occur");
            eof.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        btnRemove = new javax.swing.JButton();
        jID = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Friend Manage");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(129, 129, 129));

        jList1.setBackground(new java.awt.Color(129, 129, 129));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jList1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 47, 150, 231));

        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        getContentPane().add(btnRemove, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 158, 130, 20));

        jID.setBackground(new java.awt.Color(129, 129, 129));
        getContentPane().add(jID, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 69, 192, -1));

        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 120, 130, 20));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/friendwindow.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        friends.remove(currentEntry);
        friendsModel.remove(currentEntry);
        jList1.setModel(friendsModel);
        MainForm.friends.remove(currentEntry);
        MainForm.friendsModel.remove(currentEntry);
        MainForm.jList1.setModel(MainForm.friendsModel);
        saveData();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void jList1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MousePressed
        currentEntry = jList1.getSelectedIndex();
    }//GEN-LAST:event_jList1MousePressed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if(MainForm.username.equals(jID.getText()) == true)
        {
            JOptionPane.showMessageDialog(null, "You cannot add yourself as friend.");
        }
        else if(MainForm.username.equals(jID.getText()) == false)
        {
            if(friends.indexOf(jID.getText()) != -1)
            {
                JOptionPane.showMessageDialog(null, "That username is already friend.");
            }
            else if(friends.indexOf(jID.getText()) == -1)
            {
                friends.add(jID.getText());
                friendsModel.addElement(jID.getText());
                jList1.setModel(friendsModel);
                MainForm.friends.add(jID.getText());
                MainForm.friendsModel.addElement(jID.getText());
                MainForm.jList1.setModel(MainForm.friendsModel);
                saveData();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Internal Error. Please Contact Admin.");
        }
    }//GEN-LAST:event_btnAddActionPerformed
    public boolean findUser(String u)
    {
        for (int i = 0; i < friends.size(); ++i) 
        {
            if(friends.get(i).equals(u) == true)
            {
                return true; 
            }
        }
        return false; 
    }
    public void AddUser(String u)
    {
        friends.add(u);
        friendsModel.addElement(u);
        jList1.setModel(friendsModel);
        MainForm.friends.add(u);
        MainForm.friendsModel.addElement(u);
        MainForm.jList1.setModel(MainForm.friendsModel);
        saveData();
    }
    public void DeleteUser(String u)
    { 
        for(int i = 0; i < friends.size(); i++)
        {
            if(friends.get(i).equals(u)== true)
                DeleteIndex = i; 
        }
        friends.remove(DeleteIndex); 
        friendsModel.remove(DeleteIndex);
        jList1.setModel(friendsModel);
        MainForm.friends.remove(u);
        MainForm.friendsModel.removeElement(u);
        MainForm.jList1.setModel(MainForm.friendsModel);
        saveData();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnRemove;
    private javax.swing.JTextField jID;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
