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
        // Initialize the bankDetailsList as an ArrayList of BankDetails objects
        bankDetailsList = new ArrayList<BankDetails>();

        // Initialize the GUI components
        initComponents();

        // Populate the bankDetailsList with data from the BankDetails.dat file
        populateBankDetailsList();
    }

    public void populateBankDetailsList() {
        try {
            // Create a FileInputStream to read from the BankDetails.dat file
            FileInputStream file = new FileInputStream("BankDetails.dat");
            // Create an ObjectInputStream using the FileInputStream
            ObjectInputStream inputFile = new ObjectInputStream(file);

            // Flag to indicate whether the end of the file has been reached
            boolean endOfFile = false;
            // Loop until the end of the file is reached
            while (!endOfFile) {
                try {
                    // Read an object from the ObjectInputStream and add it to the bankDetailsList
                    bankDetailsList.add((BankDetails) inputFile.readObject());
                }
                catch (EOFException e) {
                    // If an EOFException is thrown, set the endOfFile flag to true to exit the loop
                    endOfFile = true;
                }
                catch (Exception f) {
                    // Catch any other exceptions and ignore them
                }
            }
            // Close the ObjectInputStream
            inputFile.close();
        }
        catch (IOException e) {
            // Catch any IOExceptions and ignore them
        }
    }

    public void saveBankDetailsToFile() {
        try {
            // Create a FileOutputStream to write to the BankDetails.dat file
            FileOutputStream file2 = new FileOutputStream("BankDetails.dat");
            // Create an ObjectOutputStream using the FileOutputStream
            ObjectOutputStream outputFile2 = new ObjectOutputStream(file2);

            // Loop through the bankDetailsList and write each object to the ObjectOutputStream
            for (int i = 0; i < bankDetailsList.size(); i++) {
                outputFile2.writeObject(bankDetailsList.get(i));
            }
            // Close the ObjectOutputStream
            outputFile2.close();
        }
        catch (IOException e) {
            // Catch any IOExceptions and ignore them
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
        setMaximumSize(new java.awt.Dimension(459, 442));
        setMinimumSize(new java.awt.Dimension(459, 442));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Poppins", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Register");

        registerButton.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        registerButton.setText("Create New Account");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton2.setText("Return to Login");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel2.setText("Your Full Name");

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel3.setText("Your Password");

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
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
                            .addComponent(registerPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap(127, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(registerButton)
                .addGap(149, 149, 149))
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
                .addContainerGap(70, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(475, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new LoginMenu().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        // Create a LoginMenu object to access the isFourDigit function
        LoginMenu l = new LoginMenu();
        MainBankMenu mb = new MainBankMenu();
        // Get the name, password, and age from the text fields
        String name = this.registerName.getText().trim();
        String password = this.registerPassword.getText().trim();
        String ageStr = this.registerAge.getText().trim();

        // Check if any of the fields are empty
        if (name.isEmpty() || password.isEmpty() || ageStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have to fill all the details!");
            return;
        }

        // Check if the password is 4 digits long and contains only digits
        if (!l.isFourDigit(password)) {
            JOptionPane.showMessageDialog(null, "Password must be 4 digits long and cannot contain any special characters!");
            return;
        }

        // Check if the name contains only letters
        if (!containsOnlyLetters(name)) {
            JOptionPane.showMessageDialog(null, "Your name must contain only alphabetical letters and cannot contain any special characters!");
            return;
        }

        // Check if the age contains only digits
        if (!containsOnlyNumbers(ageStr)) {
            JOptionPane.showMessageDialog(null, "Your age must contain only integers!");
            return;
        }

        // If all the checks pass, create a BankDetails object with the entered data and add it to the bankDetailsList
        int age = Integer.parseInt(ageStr);
        String accNo = rndAccNo();
        BankDetails bankDetails = new BankDetails(accNo, password, name, age);
        Accounts mainAccount = new Accounts(accNo, 1, 0, "Main Account");
        mb.accounts.add(mainAccount);
        bankDetailsList.add(bankDetails);

        // Save the bankDetailsList to the BankDetails.dat file
        saveBankDetailsToFile();
        mb.saveAccountsFile();

        // Show a success message with the account number
        JOptionPane.showMessageDialog(null, "Successfully Registered!\nHello " + name + " Your Account Number is " + accNo + "\nPlease remember your account number!");
    }//GEN-LAST:event_registerButtonActionPerformed

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
        }
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegisterMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
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
