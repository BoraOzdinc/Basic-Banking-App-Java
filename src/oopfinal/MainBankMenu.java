package oopfinal;


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author borao
 */
public class MainBankMenu extends javax.swing.JFrame {
    RegisterMenu r = new RegisterMenu();
    LoginMenu l = new LoginMenu();
    
    public static int userIndex;
    BankDetails bankDetails = r.bankDetailsList.get(userIndex);
    
    ArrayList<Transactions> transactions;
    
    ArrayList<Accounts> accounts;
    
    String userAccNo = bankDetails.getAccNo();
    
    
    public MainBankMenu() {     
        initComponents();
        accounts = new ArrayList<Accounts>();
        
        
        r.populateBankDetailsList();
        populateAccounts();
        
        accountDashboard();        
    }
    
    public void saveAccountsFile(){
    try{
        // Create a FileOutputStream to write to the BankDetails.dat file
        FileOutputStream file2 = new FileOutputStream("Accounts.dat");
        // Create an ObjectOutputStream using the FileOutputStream
        ObjectOutputStream outputFile2 = new ObjectOutputStream(file2);
        
        // Loop through the bankDetailsList and write each object to the ObjectOutputStream
        for (int i = 0; i < accounts.size(); i++) {
            outputFile2.writeObject(accounts.get(i));
        }
        // Close the ObjectOutputStream
        outputFile2.close();
    } catch(IOException e){
        // Catch any IOExceptions and ignore them
    }
}

  public void populateAccounts(){
    try{
        // Create a FileInputStream to read from the BankDetails.dat file
        FileInputStream file = new FileInputStream("Accounts.dat");
        // Create an ObjectInputStream using the FileInputStream
        ObjectInputStream inputFile = new ObjectInputStream(file);
        
        // Flag to indicate whether the end of the file has been reached
        boolean endOfFile = false;
        // Loop until the end of the file is reached
        while (!endOfFile){
            try{
                // Read an object from the ObjectInputStream and add it to the bankDetailsList
                accounts.add((Accounts) inputFile.readObject());
            } catch(EOFException e){
                // If an EOFException is thrown, set the endOfFile flag to true to exit the loop
                endOfFile = true;
            } catch(Exception f){
                JOptionPane.showMessageDialog(null,f);
            }
        }
        // Close the ObjectInputStream
        inputFile.close();
    } catch(IOException e){
        JOptionPane.showMessageDialog(null,e);
    }
}
    public void accountDashboard(){
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        
        welcomeLabel.setText(bankDetails.getName());
        double d = bankDetails.getBalance();
        DecimalFormat df = new DecimalFormat("#.00");
        String s = df.format(d);
        if(bankDetails.getBalance() == 0.0){
            totalBalance.setText("$ 0.00");
        }
        else{
            totalBalance.setText("$ "+s);
        }
        
        if(userAccounts.size() == 0){
            dashboardAccounts.setVisible(false);
            dashboardAccounts1.setVisible(false);
            
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
            
            accNo6Balance.setVisible(false);
            accNo6.setVisible(false);
            accNo6Label.setVisible(false);
            accNo6Trash.setVisible(false);
            accNo7Balance.setVisible(false);
            accNo7.setVisible(false);
            accNo7Label.setVisible(false);
            accNo7Trash.setVisible(false);
            accNo8.setVisible(false);
            accNo8Label.setVisible(false);
            accNo8Balance.setVisible(false);
            accNo8Trash.setVisible(false);
            accNo1Label.setText(userAccounts.get(0).getAccountName());
            accNo1Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo5Label.setText(userAccounts.get(0).getAccountName());
            accNo5Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
        }
        else if(userAccounts.size() == 2){
            dashboardAccounts.setVisible(true);           
            accNo3.setVisible(false);
            accNo3Balance.setVisible(false);
            accNo3Label.setVisible(false);
            accNo4.setVisible(false);
            accNo4Balance.setVisible(false);
            accNo4Label.setVisible(false);
            accNo7Balance.setVisible(false);
            accNo7.setVisible(false);
            accNo7Label.setVisible(false);
            accNo7Trash.setVisible(false);
            accNo8.setVisible(false);
            accNo8Label.setVisible(false);
            accNo8Balance.setVisible(false);
            accNo8Trash.setVisible(false);
            accNo1Label.setText(userAccounts.get(0).getAccountName());
            accNo1Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo2Label.setText(userAccounts.get(1).getAccountName());
            accNo2Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
            accNo5Label.setText(userAccounts.get(0).getAccountName());
            accNo5Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo6Label.setText(userAccounts.get(1).getAccountName());
            accNo6Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
        }
        else if(userAccounts.size() == 3){
            dashboardAccounts.setVisible(true);           
            accNo4.setVisible(false);
            accNo4Balance.setVisible(false);
            accNo4Label.setVisible(false);
            accNo8.setVisible(false);
            accNo8Label.setVisible(false);
            accNo8Balance.setVisible(false);
            accNo8Trash.setVisible(false);
            accNo1Label.setText(userAccounts.get(0).getAccountName());
            accNo1Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo2Label.setText(userAccounts.get(1).getAccountName());
            accNo2Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
            accNo3Label.setText(userAccounts.get(2).getAccountName());
            accNo3Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(2).getBalanceOfAccount()));
            accNo5Label.setText(userAccounts.get(0).getAccountName());
            accNo5Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo6Label.setText(userAccounts.get(1).getAccountName());
            accNo6Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
            accNo7Label.setText(userAccounts.get(2).getAccountName());
            accNo7Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(2).getBalanceOfAccount()));
            
        }
        else if(userAccounts.size() == 4){
            dashboardAccounts.setVisible(true);
            dashboardAccounts1.setVisible(true);
            accNo1Label.setText(userAccounts.get(0).getAccountName());
            accNo1Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo2Label.setText(userAccounts.get(1).getAccountName());
            accNo2Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
            accNo3Label.setText(userAccounts.get(2).getAccountName());
            accNo3Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(2).getBalanceOfAccount()));
            accNo4Label.setText(userAccounts.get(3).getAccountName());
            accNo4Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(3).getBalanceOfAccount()));
            accNo5Label.setText(userAccounts.get(0).getAccountName());
            accNo5Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo6Label.setText(userAccounts.get(1).getAccountName());
            accNo6Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
            accNo7Label.setText(userAccounts.get(2).getAccountName());
            accNo7Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(2).getBalanceOfAccount()));
            accNo8Label.setText(userAccounts.get(3).getAccountName());
            accNo8Balance.setText(" Balance : \n" + String.valueOf(userAccounts.get(3).getBalanceOfAccount()));
        }
    }
    
    
    /**
 * Finds all accounts in the given list that match the given account number.
 *
 * @param accounts the list of accounts to search
 * @param AccNo the account number to search for
 * @return a list of matching accounts
 */
public static ArrayList<Accounts> findAccountsByAccNo(ArrayList<Accounts> accounts, String AccNo) {
    // create an empty list to store the matching accounts
    ArrayList<Accounts> matchingAccounts = new ArrayList<>();

    // iterate over the accounts in the list
    for (Accounts account : accounts) {
      // if the account number of the current account matches the search query, add it to the list of matching accounts
      if (AccNo.equals(account.getAccNo())) {
        matchingAccounts.add(account);
      }
    }

    // return the list of matching accounts
    return matchingAccounts;
  }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabMenu = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        welcomeLabel1 = new javax.swing.JLabel();
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
        totalBalance = new javax.swing.JLabel();
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
        accountsMenu = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        dashboardAccounts1 = new javax.swing.JPanel();
        accNo7 = new javax.swing.JLabel();
        accNo5 = new javax.swing.JLabel();
        accNo6 = new javax.swing.JLabel();
        accNo8 = new javax.swing.JLabel();
        accNo5Label = new javax.swing.JLabel();
        accNo6Label = new javax.swing.JLabel();
        accNo7Label = new javax.swing.JLabel();
        accNo8Label = new javax.swing.JLabel();
        accNo5Balance = new javax.swing.JLabel();
        accNo6Balance = new javax.swing.JLabel();
        accNo7Balance = new javax.swing.JLabel();
        accNo8Balance = new javax.swing.JLabel();
        accNo5Trash = new javax.swing.JLabel();
        accNo6Trash = new javax.swing.JLabel();
        accNo7Trash = new javax.swing.JLabel();
        accNo8Trash = new javax.swing.JLabel();
        createAccountButtonPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        paymentsMenu = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        sendAccount = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        sendPerson = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        senAccPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1200, 695));
        setMinimumSize(new java.awt.Dimension(1200, 695));
        setResizable(false);

        TabMenu.setBackground(new java.awt.Color(255, 255, 255));

        welcomeLabel.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        welcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        welcomeLabel1.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        welcomeLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeLabel1.setText("Welcome");

        dashboardTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dashboardTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardTabMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
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
        accountsTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountsTabMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
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

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
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

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
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

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
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
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutButtonMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
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
                .addGroup(TabMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(welcomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(welcomeLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logoutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(settingsTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        TabMenuLayout.setVerticalGroup(
            TabMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabMenuLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(welcomeLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(dashboardTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(accountsTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transactionsTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paymentsTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107)
                .addComponent(settingsTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );

        Menus.setBackground(new java.awt.Color(232, 232, 232));
        Menus.setLayout(null);

        dashboardMenu.setBackground(new java.awt.Color(232, 232, 232));
        dashboardMenu.setMaximumSize(new java.awt.Dimension(994, 695));
        dashboardMenu.setMinimumSize(new java.awt.Dimension(994, 695));

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel7.setText("Total Balance");

        totalBalance.setFont(new java.awt.Font("Poppins", 0, 24)); // NOI18N
        totalBalance.setText("$ 999,999,999.99");

        jLabel9.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel9.setText("Recent Transactions");

        jLabel10.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel10.setText("Accounts");

        dashboardAccounts.setBackground(new java.awt.Color(232, 232, 232));
        dashboardAccounts.setLayout(null);

        accNo3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts.add(accNo3);
        accNo3.setBounds(6, 258, 250, 172);

        accNo1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts.add(accNo1);
        accNo1.setBounds(6, 43, 250, 172);

        accNo2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts.add(accNo2);
        accNo2.setBounds(303, 43, 250, 172);

        accNo4.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts.add(accNo4);
        accNo4.setBounds(303, 258, 250, 172);

        accNo1Label.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo1Label.setText("Acc 1");
        dashboardAccounts.add(accNo1Label);
        accNo1Label.setBounds(6, 21, 160, 22);

        accNo2Label.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo2Label.setText("Acc 2");
        dashboardAccounts.add(accNo2Label);
        accNo2Label.setBounds(303, 21, 170, 22);

        accNo3Label.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo3Label.setText("Acc 3");
        dashboardAccounts.add(accNo3Label);
        accNo3Label.setBounds(6, 236, 150, 22);

        accNo4Label.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo4Label.setText("Acc 4");
        dashboardAccounts.add(accNo4Label);
        accNo4Label.setBounds(303, 236, 170, 22);

        accNo1Balance.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo1Balance.setText("Acc Balance :");
        dashboardAccounts.add(accNo1Balance);
        accNo1Balance.setBounds(40, 110, 170, 90);

        accNo2Balance.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo2Balance.setText("Acc Balance :");
        dashboardAccounts.add(accNo2Balance);
        accNo2Balance.setBounds(330, 110, 190, 90);

        accNo3Balance.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo3Balance.setText("Acc Balance :");
        dashboardAccounts.add(accNo3Balance);
        accNo3Balance.setBounds(30, 330, 200, 90);

        accNo4Balance.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo4Balance.setText("Acc Balance :");
        dashboardAccounts.add(accNo4Balance);
        accNo4Balance.setBounds(330, 330, 200, 90);

        javax.swing.GroupLayout dashboardMenuLayout = new javax.swing.GroupLayout(dashboardMenu);
        dashboardMenu.setLayout(dashboardMenuLayout);
        dashboardMenuLayout.setHorizontalGroup(
            dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardMenuLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalBalance)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addGap(31, 31, 31)
                .addGroup(dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashboardAccounts, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(149, Short.MAX_VALUE))
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
                        .addComponent(totalBalance)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9))
                    .addComponent(dashboardAccounts, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(180, Short.MAX_VALUE))
        );

        Menus.add(dashboardMenu);
        dashboardMenu.setBounds(0, 0, 994, 695);

        accountsMenu.setBackground(new java.awt.Color(232, 232, 232));
        accountsMenu.setForeground(new java.awt.Color(102, 102, 102));
        accountsMenu.setMaximumSize(new java.awt.Dimension(994, 695));
        accountsMenu.setMinimumSize(new java.awt.Dimension(994, 695));
        accountsMenu.setPreferredSize(new java.awt.Dimension(994, 695));
        accountsMenu.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ACCOUNTS");
        accountsMenu.add(jLabel8);
        jLabel8.setBounds(6, 16, 980, 55);

        dashboardAccounts1.setBackground(new java.awt.Color(232, 232, 232));
        dashboardAccounts1.setLayout(null);

        accNo7.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts1.add(accNo7);
        accNo7.setBounds(6, 258, 250, 172);

        accNo5.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts1.add(accNo5);
        accNo5.setBounds(6, 43, 250, 172);

        accNo6.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts1.add(accNo6);
        accNo6.setBounds(303, 43, 250, 172);

        accNo8.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/credit_card.png"))); // NOI18N
        dashboardAccounts1.add(accNo8);
        accNo8.setBounds(303, 258, 250, 172);

        accNo5Label.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo5Label.setText("Acc 1");
        dashboardAccounts1.add(accNo5Label);
        accNo5Label.setBounds(6, 21, 160, 22);

        accNo6Label.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo6Label.setText("Acc 2");
        dashboardAccounts1.add(accNo6Label);
        accNo6Label.setBounds(303, 21, 170, 22);

        accNo7Label.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo7Label.setText("Acc 3");
        dashboardAccounts1.add(accNo7Label);
        accNo7Label.setBounds(6, 236, 150, 22);

        accNo8Label.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo8Label.setText("Acc 4");
        dashboardAccounts1.add(accNo8Label);
        accNo8Label.setBounds(303, 236, 170, 22);

        accNo5Balance.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo5Balance.setText("Acc Balance :");
        dashboardAccounts1.add(accNo5Balance);
        accNo5Balance.setBounds(40, 110, 170, 90);

        accNo6Balance.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo6Balance.setText("Acc Balance :");
        dashboardAccounts1.add(accNo6Balance);
        accNo6Balance.setBounds(330, 110, 190, 90);

        accNo7Balance.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo7Balance.setText("Acc Balance :");
        dashboardAccounts1.add(accNo7Balance);
        accNo7Balance.setBounds(30, 330, 200, 90);

        accNo8Balance.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNo8Balance.setText("Acc Balance :");
        dashboardAccounts1.add(accNo8Balance);
        accNo8Balance.setBounds(330, 330, 200, 90);

        accNo5Trash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/trash45x45.png"))); // NOI18N
        accNo5Trash.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        accNo5Trash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accNo5TrashMouseClicked(evt);
            }
        });
        dashboardAccounts1.add(accNo5Trash);
        accNo5Trash.setBounds(190, 150, 50, 50);

        accNo6Trash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/trash45x45.png"))); // NOI18N
        accNo6Trash.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        accNo6Trash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accNo6TrashMouseClicked(evt);
            }
        });
        dashboardAccounts1.add(accNo6Trash);
        accNo6Trash.setBounds(490, 150, 50, 50);

        accNo7Trash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/trash45x45.png"))); // NOI18N
        accNo7Trash.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        accNo7Trash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accNo7TrashMouseClicked(evt);
            }
        });
        dashboardAccounts1.add(accNo7Trash);
        accNo7Trash.setBounds(190, 360, 50, 60);

        accNo8Trash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/trash45x45.png"))); // NOI18N
        accNo8Trash.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        accNo8Trash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accNo8TrashMouseClicked(evt);
            }
        });
        dashboardAccounts1.add(accNo8Trash);
        accNo8Trash.setBounds(490, 360, 50, 60);

        accountsMenu.add(dashboardAccounts1);
        dashboardAccounts1.setBounds(220, 80, 560, 457);

        createAccountButtonPanel.setForeground(new java.awt.Color(60, 63, 65));
        createAccountButtonPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        createAccountButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createAccountButtonPanelMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Create New Account");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout createAccountButtonPanelLayout = new javax.swing.GroupLayout(createAccountButtonPanel);
        createAccountButtonPanel.setLayout(createAccountButtonPanelLayout);
        createAccountButtonPanelLayout.setHorizontalGroup(
            createAccountButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createAccountButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        createAccountButtonPanelLayout.setVerticalGroup(
            createAccountButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createAccountButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addContainerGap())
        );

        accountsMenu.add(createAccountButtonPanel);
        createAccountButtonPanel.setBounds(310, 570, 400, 80);

        Menus.add(accountsMenu);
        accountsMenu.setBounds(-2, -3, 1140, 700);

        paymentsMenu.setBackground(new java.awt.Color(232, 232, 232));
        paymentsMenu.setMaximumSize(new java.awt.Dimension(994, 695));
        paymentsMenu.setMinimumSize(new java.awt.Dimension(994, 695));
        paymentsMenu.setPreferredSize(new java.awt.Dimension(994, 695));

        jLabel12.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("PAYMENTS");

        sendAccount.setMaximumSize(new java.awt.Dimension(210, 60));
        sendAccount.setMinimumSize(new java.awt.Dimension(210, 60));

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Send Between Accounts");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout sendAccountLayout = new javax.swing.GroupLayout(sendAccount);
        sendAccount.setLayout(sendAccountLayout);
        sendAccountLayout.setHorizontalGroup(
            sendAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sendAccountLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
        );
        sendAccountLayout.setVerticalGroup(
            sendAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sendAccountLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel14.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Send to Person");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout sendPersonLayout = new javax.swing.GroupLayout(sendPerson);
        sendPerson.setLayout(sendPersonLayout);
        sendPersonLayout.setHorizontalGroup(
            sendPersonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sendPersonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
        );
        sendPersonLayout.setVerticalGroup(
            sendPersonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sendPersonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        senAccPanel.setBackground(new java.awt.Color(232, 232, 232));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout senAccPanelLayout = new javax.swing.GroupLayout(senAccPanel);
        senAccPanel.setLayout(senAccPanelLayout);
        senAccPanelLayout.setHorizontalGroup(
            senAccPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(senAccPanelLayout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(373, Short.MAX_VALUE))
        );
        senAccPanelLayout.setVerticalGroup(
            senAccPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, senAccPanelLayout.createSequentialGroup()
                .addContainerGap(394, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        javax.swing.GroupLayout paymentsMenuLayout = new javax.swing.GroupLayout(paymentsMenu);
        paymentsMenu.setLayout(paymentsMenuLayout);
        paymentsMenuLayout.setHorizontalGroup(
            paymentsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentsMenuLayout.createSequentialGroup()
                .addGroup(paymentsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 990, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paymentsMenuLayout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(sendAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(278, 278, 278)
                        .addComponent(sendPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(senAccPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        paymentsMenuLayout.setVerticalGroup(
            paymentsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentsMenuLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel12)
                .addGap(7, 7, 7)
                .addGroup(paymentsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(senAccPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Menus.add(paymentsMenu);
        paymentsMenu.setBounds(0, 0, 940, 700);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(TabMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Menus, javax.swing.GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Menus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1216, 703));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void settingsTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsTabMouseClicked
        
    }//GEN-LAST:event_settingsTabMouseClicked

    private void logoutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMouseClicked
        int result = JOptionPane.showConfirmDialog(null,"Sure? You want to log out?", "Log Out?",
               JOptionPane.YES_NO_OPTION);
        if(result == 0){
            this.dispose();
            new LoginMenu().setVisible(true);
        }
    }//GEN-LAST:event_logoutButtonMouseClicked

    private void accountsTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountsTabMouseClicked
        dashboardMenu.setVisible(false);
        accountsMenu.setVisible(true);
    }//GEN-LAST:event_accountsTabMouseClicked

    private void dashboardTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardTabMouseClicked
        dashboardMenu.setVisible(true);
        accountsMenu.setVisible(false);
    }//GEN-LAST:event_dashboardTabMouseClicked

    private void createAccountButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createAccountButtonPanelMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        if (userAccounts.size() == 4){
            JOptionPane.showMessageDialog(null,"You can't create more than 4 accounts!", "Warning!", JOptionPane.ERROR_MESSAGE);
        }
        else{
            String input = JOptionPane.showInputDialog("Please Enter Your Account Name.");
        Accounts newAccount = new Accounts(userAccNo, userAccounts.size()+1, 0, input);
        accounts.add(newAccount);
        
        JOptionPane.showMessageDialog(null,"Your new account saved.");
        saveAccountsFile();
        dispose();
        new MainBankMenu().setVisible(true);
        }      
    }//GEN-LAST:event_createAccountButtonPanelMouseClicked

    private void accNo5TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo5TrashMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        int x = JOptionPane.showConfirmDialog(null,
             "You are about to delete the account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);
        if(x == 0){
            Accounts accountToRemove = userAccounts.get(0);
            accounts.remove(accountToRemove);
            
            saveAccountsFile();
            
            dispose();
            new MainBankMenu().setVisible(true);
            
        }
    }//GEN-LAST:event_accNo5TrashMouseClicked

    private void accNo6TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo6TrashMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        int x = JOptionPane.showConfirmDialog(null,
             "You are about to delete the account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);
        if(x == 0){
            Accounts accountToRemove = userAccounts.get(1);
            accounts.remove(accountToRemove);
            
            saveAccountsFile();
            
            dispose();
            new MainBankMenu().setVisible(true);
            
        }
    }//GEN-LAST:event_accNo6TrashMouseClicked

    private void accNo7TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo7TrashMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        int x = JOptionPane.showConfirmDialog(null,
             "You are about to delete the account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);
        if(x == 0){
            Accounts accountToRemove = userAccounts.get(2);
            accounts.remove(accountToRemove);
            
            saveAccountsFile();
            
            dispose();
            new MainBankMenu().setVisible(true);
            
        }
    }//GEN-LAST:event_accNo7TrashMouseClicked

    private void accNo8TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo8TrashMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        int x = JOptionPane.showConfirmDialog(null,
             "You are about to delete the account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);
        if(x == 0){
            Accounts accountToRemove = userAccounts.get(3);
            accounts.remove(accountToRemove);
            
            saveAccountsFile();
            
            dispose();
            new MainBankMenu().setVisible(true);
            
        }
    }//GEN-LAST:event_accNo8TrashMouseClicked

   
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
    private javax.swing.JLabel accNo5;
    private javax.swing.JLabel accNo5Balance;
    private javax.swing.JLabel accNo5Label;
    private javax.swing.JLabel accNo5Trash;
    private javax.swing.JLabel accNo6;
    private javax.swing.JLabel accNo6Balance;
    private javax.swing.JLabel accNo6Label;
    private javax.swing.JLabel accNo6Trash;
    private javax.swing.JLabel accNo7;
    private javax.swing.JLabel accNo7Balance;
    private javax.swing.JLabel accNo7Label;
    private javax.swing.JLabel accNo7Trash;
    private javax.swing.JLabel accNo8;
    private javax.swing.JLabel accNo8Balance;
    private javax.swing.JLabel accNo8Label;
    private javax.swing.JLabel accNo8Trash;
    private javax.swing.JPanel accountsMenu;
    private javax.swing.JPanel accountsTab;
    private javax.swing.JPanel createAccountButtonPanel;
    private javax.swing.JPanel dashboardAccounts;
    private javax.swing.JPanel dashboardAccounts1;
    private javax.swing.JPanel dashboardMenu;
    private javax.swing.JPanel dashboardTab;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel logoutButton;
    private javax.swing.JPanel paymentsMenu;
    private javax.swing.JPanel paymentsTab;
    private javax.swing.JPanel senAccPanel;
    private javax.swing.JPanel sendAccount;
    private javax.swing.JPanel sendPerson;
    private javax.swing.JPanel settingsTab;
    private javax.swing.JLabel totalBalance;
    private javax.swing.JPanel transactionsTab;
    private javax.swing.JLabel welcomeLabel;
    private javax.swing.JLabel welcomeLabel1;
    // End of variables declaration//GEN-END:variables
}
