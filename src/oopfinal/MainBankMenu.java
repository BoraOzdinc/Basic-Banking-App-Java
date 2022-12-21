package oopfinal;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author borao
 */
public class MainBankMenu extends javax.swing.JFrame {
    RegisterMenu r = new RegisterMenu();
    LoginMenu l = new LoginMenu();
    public String userAccNo = "999999"; //l.userAccNo;
    ArrayList<Transactions> transactions;
    
    ArrayList<Accounts> accounts;
    
    public MainBankMenu() {
        
        
        transactions = new ArrayList<Transactions>();
        accounts = new ArrayList<Accounts>();
        initComponents();
        accountDashboard();
        
    }
    
    public void accountDashboard(){
        Accounts newAccount = new Accounts(userAccNo, 1, 5000,"Savings Account");
        Accounts newAccount2 = new Accounts(userAccNo, 2, 8000,"Main Account");
        Accounts newAccount3 = new Accounts(userAccNo, 3, 500,"Bills");
        Accounts newAccount4 = new Accounts(userAccNo, 4, 50000,"Salary");
        accounts.add(newAccount);
        accounts.add(newAccount2);
        accounts.add(newAccount3);
        accounts.add(newAccount4);
        
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        if(userAccounts.size() == 0){
            dashboardAccounts.setVisible(false);
        }
        else if(userAccounts.size() == 1){
            dashboardAccounts.setVisible(true);
            accNo2.setVisible(false);
            accNo2Balance.setVisible(false);
            accNo2Label.setVisible(false);
            accNo3.setVisible(false);
            accNo3Balance.setVisible(false);
            accNo3Label.setVisible(false);
            accNo4.setVisible(false);
            accNo4Balance.setVisible(false);
            accNo4Label.setVisible(false);
            accNo1Label.setText(userAccounts.get(0).getAccountName());
            accNo1Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
        }
        else if(userAccounts.size() == 2){
            dashboardAccounts.setVisible(true);           
            accNo3.setVisible(false);
            accNo3Balance.setVisible(false);
            accNo3Label.setVisible(false);
            accNo4.setVisible(false);
            accNo4Balance.setVisible(false);
            accNo4Label.setVisible(false);
            accNo1Label.setText(userAccounts.get(0).getAccountName());
            accNo1Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo2Label.setText(userAccounts.get(1).getAccountName());
            accNo2Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
        }
        else if(userAccounts.size() == 3){
            dashboardAccounts.setVisible(true);           
            accNo4.setVisible(false);
            accNo4Balance.setVisible(false);
            accNo4Label.setVisible(false);
            accNo1Label.setText(userAccounts.get(0).getAccountName());
            accNo1Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo2Label.setText(userAccounts.get(1).getAccountName());
            accNo2Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
            accNo3Label.setText(userAccounts.get(2).getAccountName());
            accNo3Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(2).getBalanceOfAccount()));
        }
        else if(userAccounts.size() == 4){
            dashboardAccounts.setVisible(true);           
            accNo1Label.setText(userAccounts.get(0).getAccountName());
            accNo1Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo2Label.setText(userAccounts.get(1).getAccountName());
            accNo2Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
            accNo3Label.setText(userAccounts.get(2).getAccountName());
            accNo3Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(2).getBalanceOfAccount()));
            accNo4Label.setText(userAccounts.get(3).getAccountName());
            accNo4Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(3).getBalanceOfAccount()));
        }
    }
    
    
    public static ArrayList<Accounts> findAccountsByAccNo(ArrayList<Accounts> accounts, String AccNo) {
    ArrayList<Accounts> matchingAccounts = new ArrayList<>();
    for (Accounts account : accounts) {
      if (AccNo.equals(account.getAccNo())) {
        matchingAccounts.add(account);
      }
    }
    return matchingAccounts;
  }
    
    
    
    public void populateTransactionHistory(){
    try{
        // Create a FileInputStream to read from the TransactionHistory.dat file
        FileInputStream file = new FileInputStream("TransactionHistory.dat");
        // Create an ObjectInputStream using the FileInputStream
        ObjectInputStream inputFile = new ObjectInputStream(file);
        
        // Flag to indicate whether the end of the file has been reached
        boolean endOfFile = false;
        // Loop until the end of the file is reached
        while (!endOfFile){
            try{
                // Read an object from the ObjectInputStream and add it to the transactions
                transactions.add((Transactions) inputFile.readObject());
            } catch(EOFException e){
                // If an EOFException is thrown, set the endOfFile flag to true to exit the loop
                endOfFile = true;
            } catch(Exception f){
                // Catch any other exceptions and ignore them
            }
        }
        // Close the ObjectInputStream
        inputFile.close();
    } catch(IOException e){
        // Catch any IOExceptions and ignore them
    }
}

    
    
    public void saveTransactionHistoryToFile(){
    try{
        // Create a FileOutputStream to write to the TransactionHistory.dat file
        FileOutputStream file2 = new FileOutputStream("TransactionHistory.dat");
        // Create an ObjectOutputStream using the FileOutputStream
        ObjectOutputStream outputFile2 = new ObjectOutputStream(file2);
        
        // Loop through the transactions and write each object to the ObjectOutputStream
        for (int i = 0; i < transactions.size(); i++) {
            outputFile2.writeObject(transactions.get(i));
        }
        // Close the ObjectOutputStream
        outputFile2.close();
    } catch(IOException e){
        // Catch any IOExceptions and ignore them
    }
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabMenu = new javax.swing.JPanel();
        dashboardTab = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        accountsTab = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        transactionsTab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        paymentsTab = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        settingsTab = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        logoutButton = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Menus = new javax.swing.JPanel();
        dashboardMenu = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        dashboardAccounts = new javax.swing.JPanel();
        accNo3 = new javax.swing.JLabel();
        accNo1 = new javax.swing.JLabel();
        accNo2 = new javax.swing.JLabel();
        accNo4 = new javax.swing.JLabel();
        accNo1Label = new javax.swing.JLabel();
        accNo2Label = new javax.swing.JLabel();
        accNo3Label = new javax.swing.JLabel();
        accNo4Label = new javax.swing.JLabel();
        accNo1Balance = new javax.swing.JLabel();
        accNo2Balance = new javax.swing.JLabel();
        accNo3Balance = new javax.swing.JLabel();
        accNo4Balance = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TabMenu.setBackground(new java.awt.Color(255, 255, 255));

        dashboardTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Dashboard");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout dashboardTabLayout = new javax.swing.GroupLayout(dashboardTab);
        dashboardTab.setLayout(dashboardTabLayout);
        dashboardTabLayout.setHorizontalGroup(
            dashboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardTabLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashboardTabLayout.setVerticalGroup(
            dashboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        accountsTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Accounts");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout accountsTabLayout = new javax.swing.GroupLayout(accountsTab);
        accountsTab.setLayout(accountsTabLayout);
        accountsTabLayout.setHorizontalGroup(
            accountsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountsTabLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        accountsTabLayout.setVerticalGroup(
            accountsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accountsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        transactionsTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Transactions");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout transactionsTabLayout = new javax.swing.GroupLayout(transactionsTab);
        transactionsTab.setLayout(transactionsTabLayout);
        transactionsTabLayout.setHorizontalGroup(
            transactionsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionsTabLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        transactionsTabLayout.setVerticalGroup(
            transactionsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transactionsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        paymentsTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Payments");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout paymentsTabLayout = new javax.swing.GroupLayout(paymentsTab);
        paymentsTab.setLayout(paymentsTabLayout);
        paymentsTabLayout.setHorizontalGroup(
            paymentsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentsTabLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        paymentsTabLayout.setVerticalGroup(
            paymentsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paymentsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        settingsTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        settingsTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsTabMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Settings");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout settingsTabLayout = new javax.swing.GroupLayout(settingsTab);
        settingsTab.setLayout(settingsTabLayout);
        settingsTabLayout.setHorizontalGroup(
            settingsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsTabLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        settingsTabLayout.setVerticalGroup(
            settingsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Log out");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout logoutButtonLayout = new javax.swing.GroupLayout(logoutButton);
        logoutButton.setLayout(logoutButtonLayout);
        logoutButtonLayout.setHorizontalGroup(
            logoutButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutButtonLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        logoutButtonLayout.setVerticalGroup(
            logoutButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoutButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout TabMenuLayout = new javax.swing.GroupLayout(TabMenu);
        TabMenu.setLayout(TabMenuLayout);
        TabMenuLayout.setHorizontalGroup(
            TabMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(accountsTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(transactionsTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(paymentsTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TabMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TabMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logoutButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(settingsTab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        TabMenuLayout.setVerticalGroup(
            TabMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabMenuLayout.createSequentialGroup()
                .addContainerGap(219, Short.MAX_VALUE)
                .addComponent(dashboardTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(accountsTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transactionsTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paymentsTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170)
                .addComponent(settingsTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Menus.setBackground(new java.awt.Color(232, 232, 232));

        dashboardMenu.setBackground(new java.awt.Color(232, 232, 232));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Total Balance");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel8.setText("$ 999,999,999.99");

        jLabel9.setText("Recent Transactions");

        jLabel10.setText("Accounts");

        dashboardAccounts.setBackground(new java.awt.Color(232, 232, 232));
        dashboardAccounts.setLayout(null);

        accNo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts.add(accNo3);
        accNo3.setBounds(6, 258, 250, 172);

        accNo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts.add(accNo1);
        accNo1.setBounds(6, 43, 250, 172);

        accNo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts.add(accNo2);
        accNo2.setBounds(303, 43, 250, 172);

        accNo4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts.add(accNo4);
        accNo4.setBounds(303, 258, 250, 172);

        accNo1Label.setText("Acc 1");
        dashboardAccounts.add(accNo1Label);
        accNo1Label.setBounds(6, 21, 160, 16);

        accNo2Label.setText("Acc 2");
        dashboardAccounts.add(accNo2Label);
        accNo2Label.setBounds(303, 21, 170, 16);

        accNo3Label.setText("Acc 3");
        dashboardAccounts.add(accNo3Label);
        accNo3Label.setBounds(6, 236, 150, 16);

        accNo4Label.setText("Acc 4");
        dashboardAccounts.add(accNo4Label);
        accNo4Label.setBounds(303, 236, 170, 16);

        accNo1Balance.setText("Acc Balance :");
        dashboardAccounts.add(accNo1Balance);
        accNo1Balance.setBounds(40, 110, 170, 90);

        accNo2Balance.setText("Acc Balance :");
        dashboardAccounts.add(accNo2Balance);
        accNo2Balance.setBounds(330, 110, 190, 90);

        accNo3Balance.setText("Acc Balance :");
        dashboardAccounts.add(accNo3Balance);
        accNo3Balance.setBounds(30, 330, 200, 90);

        accNo4Balance.setText("Acc Balance :");
        dashboardAccounts.add(accNo4Balance);
        accNo4Balance.setBounds(330, 330, 200, 90);

        javax.swing.GroupLayout dashboardMenuLayout = new javax.swing.GroupLayout(dashboardMenu);
        dashboardMenu.setLayout(dashboardMenuLayout);
        dashboardMenuLayout.setHorizontalGroup(
            dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardMenuLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashboardAccounts, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(164, 164, 164))
        );
        dashboardMenuLayout.setVerticalGroup(
            dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardMenuLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardMenuLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(57, 57, 57)
                        .addComponent(jLabel9))
                    .addComponent(dashboardAccounts, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MenusLayout = new javax.swing.GroupLayout(Menus);
        Menus.setLayout(MenusLayout);
        MenusLayout.setHorizontalGroup(
            MenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MenusLayout.setVerticalGroup(
            MenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(TabMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Menus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Menus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void settingsTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsTabMouseClicked
        
    }//GEN-LAST:event_settingsTabMouseClicked

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
            java.util.logging.Logger.getLogger(MainBankMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainBankMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainBankMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainBankMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainBankMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Menus;
    private javax.swing.JPanel TabMenu;
    private javax.swing.JLabel accNo1;
    private javax.swing.JLabel accNo1Balance;
    private javax.swing.JLabel accNo1Label;
    private javax.swing.JLabel accNo2;
    private javax.swing.JLabel accNo2Balance;
    private javax.swing.JLabel accNo2Label;
    private javax.swing.JLabel accNo3;
    private javax.swing.JLabel accNo3Balance;
    private javax.swing.JLabel accNo3Label;
    private javax.swing.JLabel accNo4;
    private javax.swing.JLabel accNo4Balance;
    private javax.swing.JLabel accNo4Label;
    private javax.swing.JPanel accountsTab;
    private javax.swing.JPanel dashboardAccounts;
    private javax.swing.JPanel dashboardMenu;
    private javax.swing.JPanel dashboardTab;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel logoutButton;
    private javax.swing.JPanel paymentsTab;
    private javax.swing.JPanel settingsTab;
    private javax.swing.JPanel transactionsTab;
    // End of variables declaration//GEN-END:variables
}
