/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package oopfinal;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author borao
 */
public class RegisterMenu extends javax.swing.JFrame {
    
    ArrayList<BankDetails> bankDetailsList;
    

    
    
    
    public RegisterMenu() {
        bankDetailsList = new ArrayList<BankDetails>();
        initComponents();
        populateBankDetailsList();
        
        
        
    }
    
    public void populateBankDetailsList(){
        try{
            FileInputStream file = new FileInputStream("BankDetails.dat");
            ObjectInputStream inputFile = new ObjectInputStream(file);
            
            boolean endOfFile = false;
            while (!endOfFile){
                try{
                    bankDetailsList.add((BankDetails) inputFile.readObject());
                }catch(EOFException e){
                    endOfFile = true;
                }catch(Exception f){
                                     
                }
            }
            inputFile.close();
        }catch(IOException e){
            
        }
    }
    
    
    public void saveBankDetailsToFile(){
        try{
            FileOutputStream file2 = new FileOutputStream("BankDetails.dat");
            ObjectOutputStream outputFile2 = new ObjectOutputStream(file2);
            
            for (int i = 0; i < bankDetailsList.size(); i++) {
                
                outputFile2.writeObject(bankDetailsList.get(i));
               
            }
            outputFile2.close();
            
            
           
            
        }catch(IOException e){
            
        }
    }
    
    private static String rndAccNo() {
    // It will generate 6 digit random Number.
    // from 0 to 999999
    Random rnd = new Random();
    int number = rnd.nextInt(999999);

    // this will convert any number sequence into 6 character.
    return String.format("%06d", number);
}
    
    public boolean containsOnlyLetters(String str) {
  // Use the matches() method of the String class to check if the string
  // matches the regular expression for alphabetical letters
  return str.matches("[a-zA-Z\\s']+");
}
    public boolean containsOnlyNumbers(String str) {
  // Use the matches() method of the String class to check if the string
  // matches the regular expression for integer numbers
  return str.matches("\\d+");
}



    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        registerName = new javax.swing.JTextField();
        registerPassword = new javax.swing.JPasswordField();
        registerButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        registerAge = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Abdülleziz Bank Register");

        jLabel1.setFont(new java.awt.Font("Anke", 0, 24)); // NOI18N
        jLabel1.setText("Register");

        registerButton.setText("Create New Account");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Return to Login");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Your Full Name");

        jLabel3.setText("Your Password");

        jLabel4.setText("Your Age");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(registerName, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(registerAge, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registerPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap(127, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(182, 182, 182))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(registerButton)
                        .addGap(153, 153, 153))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jButton2)
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerAge, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new LoginMenu().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        LoginMenu l = new LoginMenu();
        if (this.registerName.getText().isEmpty() || 
                this.registerPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You Have To Fill All The Details!");
        }
        else if(!l.isFourDigit(this.registerPassword.getText())){
            JOptionPane.showMessageDialog(null, "Password must be 4 digit long and cannot Contain any special characters!");
        }
        else if(!containsOnlyLetters(this.registerName.getText())){
            JOptionPane.showMessageDialog(null, "Your name must contain only aphabetical letters and cannot contain any special characters!");
        }
        else if(!containsOnlyNumbers(this.registerAge.getText())){
            JOptionPane.showMessageDialog(null, "Your Age must contain only integers!");
        }
        else{
            String registerName = this.registerName.getText().trim();
            String registerPassword = this.registerPassword.getText().trim();
            int registerAge = Integer.parseInt(this.registerAge.getText().trim());
            
            String tempAccNo = rndAccNo();

            BankDetails bankDetails = new BankDetails(tempAccNo, registerPassword, 0, registerName, registerAge);
            bankDetailsList.add(bankDetails);
            saveBankDetailsToFile();
            JOptionPane.showMessageDialog(null, "Succesfully Registered!\nHello " + registerName +" Your Account Number is " + tempAccNo + "\nPlease Remember Your Account Number!");
            System.out.println("bank details list: " + bankDetailsList.size() + "\nAdded User : " + registerName + "\npass: " + registerPassword + "\nage: " + registerAge +
                    "\naccno: " + tempAccNo);
    }//GEN-LAST:event_registerButtonActionPerformed
    }        
    
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
            java.util.logging.Logger.getLogger(RegisterMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField registerAge;
    private javax.swing.JButton registerButton;
    private javax.swing.JTextField registerName;
    private javax.swing.JPasswordField registerPassword;
    // End of variables declaration//GEN-END:variables
    }

