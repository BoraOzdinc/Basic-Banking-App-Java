package oopfinal;

import javax.swing.JOptionPane;

/**
 *
 * @author borao
 */
public class LoginMenu extends javax.swing.JFrame {

    RegisterMenu r = new RegisterMenu();

    public static String savedUserAccNo = "";

    public LoginMenu() {

        initComponents();
        r.populateBankDetailsList();
        accountNoLogin.setText(savedUserAccNo);

    }

    boolean numberOrNot(String input) {
        try {
            Integer.parseInt(input);
        }
        catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    public boolean isSixDigit(String accNo) {
        if (numberOrNot(accNo)) {
            if (String.valueOf(accNo).length() == 6) {
                return true;
            }
            else {
                return false;
            }

        }
        else {
            return false;
        }
    }

    public boolean isFourDigit(String accNo) {
        if (numberOrNot(accNo)) {
            if (String.valueOf(accNo).length() == 4) {
                return true;
            }
            else {
                return false;
            }

        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        RegisterMenu r = new RegisterMenu();

        String results = "";

        for (BankDetails d : r.bankDetailsList) {

            results += d.toString();
        }
        return results;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        accountNoLogin = new javax.swing.JTextField();
        passwordLogin = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bank");
        setBackground(new java.awt.Color(204, 204, 204));
        setFocusTraversalPolicyProvider(true);
        setMaximumSize(new java.awt.Dimension(291, 358));
        setMinimumSize(new java.awt.Dimension(291, 358));
        setResizable(false);

        accountNoLogin.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        passwordLogin.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        loginButton.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        registerButton.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        registerButton.setText("Register");
        registerButton.setToolTipText("");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Please Login");

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel3.setText("Password:");

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel4.setText("Account No:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(accountNoLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(passwordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(accountNoLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(307, 366));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // This function is called when the "login" button is clicked. It gets the account number and password from the text fields,
    // and checks if they are valid. If they are valid, it searches the bankDetailsList for a BankDetails object with a matching
    // account number. If a matching object is found, it checks if the password matches. If both the account number and password
    // are correct, it displays a success message and opens the MainBankMenu window. If either the account number or password is
    // incorrect, it displays an error message.

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // Create a new RegisterMenu object
        RegisterMenu r = new RegisterMenu();

        int userIndx = -1;

        // Check if the account number and password fields are empty
        if (this.accountNoLogin.getText().isEmpty() || this.passwordLogin.getText().isEmpty()) {
            // Display error message if either field is empty
            JOptionPane.showMessageDialog(null, "You Have To Fill All The Details!");
        }
        else if (!isSixDigit(this.accountNoLogin.getText())) {
            // Display error message if the account number is not a 6-digit string
            JOptionPane.showMessageDialog(null, "Account No must be 6 digit long and Cannot contain any special characters!");
        }
        else if (!isFourDigit(this.passwordLogin.getText())) {
            // Display error message if the password is not a 4-digit string
            JOptionPane.showMessageDialog(null, "Password must be 4 digit long and cannot Contain any special characters!");
        }
        else {
            // If both the account number and password are in the correct format, search for a matching account number in the bankDetailsList
            String accNoInput = this.accountNoLogin.getText().trim();
            int userIndex = -1;
            for (int i = 0; i < r.bankDetailsList.size(); i++) {
                BankDetails userDetails = r.bankDetailsList.get(i);
                // If a matching account number is found, store the index and retrieve the BankDetails object
                if (Integer.parseInt(userDetails.getAccNo()) == Integer.parseInt(accNoInput)) {
                    userIndex = i;
                    MainBankMenu.userBDAccNo = userDetails.getAccNo();

                    System.out.println("userindex: " + userIndex + "\naccno: " + userDetails.getAccNo() + "\nname: " + userDetails.getName() + "\npass: " + userDetails.getPassword()
                            + "\nage: " + userDetails.getAge());
                    break;
                }
            }

            // If the account number is not found, display an error message
            if (userIndex == -1) {
                JOptionPane.showMessageDialog(null, "Your Account Number or Password is invalid!");
            }
            else {
                // If the account number is found, retrieve the BankDetails object and check if the password matches
                BankDetails userDetails = r.bankDetailsList.get(userIndex);
                if (!userDetails.getPassword().equals(this.passwordLogin.getText().trim())) {
                    // Display error message if the password is incorrect
                    JOptionPane.showMessageDialog(null, "Your Account Number or Password is invalid!");
                }
                else {
                    // If the password is correct, display a success message and show the MainBankMenu form
                    JOptionPane.showMessageDialog(null, "Successfully logged in!\nHello " + userDetails.getName());

                    new MainBankMenu().setVisible(true);
                    dispose();

                }
            }
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        new RegisterMenu().setVisible(true);
        dispose();
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
            java.util.logging.Logger.getLogger(LoginMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accountNoLogin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordLogin;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables

}
