package oopfinal;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author borao
 */
public class MainBankMenu extends javax.swing.JFrame {

    static RegisterMenu r = new RegisterMenu();
    static LoginMenu l = new LoginMenu();
    public String systemDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    public static int userIndex;
    public static int accNumberCounter = 1;
    public static int lastPage = 1;
    BankDetails bankDetails = r.bankDetailsList.get(userIndex);

    ArrayList<Transactions> transactions;

    ArrayList<Accounts> accounts;

    String userAccNo = bankDetails.getAccNo();

    public MainBankMenu() {
        accounts = new ArrayList<Accounts>();
        transactions = new ArrayList<Transactions>();

        initComponents();
        r.populateBankDetailsList();
        populateAccounts();
        populateTransactions();
        accountDashboard();
        lastPage(lastPage);

        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        ArrayList<Transactions> userTransactions = transactionsByAccNo(transactions, userAccNo);

        String[] userAccountArray = new String[userAccounts.size()];
        for (int i = 0; i < userAccounts.size(); i++) {
            userAccountArray[i] = userAccounts.get(i).getAccountName();
        }
        accFromComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(userAccountArray));
        accFromComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(userAccountArray));
        accToComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(userAccountArray));

        String[] userTransactionArray = new String[userTransactions.size()];
        for (int i = 0; i < userTransactions.size(); i++) {

            String senderName = r.bankDetailsList.get(findBankDetailsByAccNo(userTransactions.get(i).getSenderAccNo())).getName();
            String receiverName = r.bankDetailsList.get(findBankDetailsByAccNo(userTransactions.get(i).getReceiverAccNo())).getName();

            userTransactionArray[i] = senderName + "    " + "    " + "    " + "    "
                    + receiverName + "    " + "    " + "    " + "    "
                    + userTransactions.get(i).getTransactionDate() + "    " + "    " + "    " + "    "
                    + userTransactions.get(i).getAmount() + "    " + "   " + "    " + "    "
                    + userTransactions.get(i).getPaymentSource();
        }

        transactionList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = userTransactionArray;

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });

        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabel();
            }
        };
        accNoInput.getDocument().addDocumentListener(listener);
    }

    private void updateLabel() {
        // get the text from the text field
        String accNo = accNoInput.getText();
        int bankDetailsIndex = findBankDetailsByAccNo(accNo);
        try {
            int no = Integer.parseInt(accNo);
        }
        catch (Exception e) {
            accOwnerName.setText("You must enter a 6 digit account number.");
        }
        if (bankDetailsIndex == -1) {
            accOwnerName.setText("This account cannot be found.");
        }
        else if (bankDetails.getAccNo() == r.bankDetailsList.get(bankDetailsIndex).getName()) {
            accOwnerName.setText("You cannot transfer to your own account!");
        }
        else {
            String name = r.bankDetailsList.get(bankDetailsIndex).getName();
            accOwnerName.setText(nameMask(name, 5));
        }
    }

    public static String nameMask(String name, int numStars) {
        // split the name string into an array of words
        String[] words = name.split(" ");

        // create a new string builder
        StringBuilder sb = new StringBuilder();

        // loop through the words
        for (String word : words) {
            // add the first two letters of the word to the string builder
            sb.append(word.substring(0, 2));

            // add asterisks for the remaining letters of the word
            for (int i = 0; i < numStars; i++) {
                sb.append("*");
            }

            // add a space between words
            sb.append(" ");
        }

        // return the masked name
        return sb.toString().trim();
    }

    public void lastPage(int x) {
        switch (x) {
            case 1 -> {
                paymentsMenu.setVisible(false);
                dashboardMenu.setVisible(true);
                accountsMenu.setVisible(false);
                sendAccPanel.setVisible(false);
                sendOtherAccPanel.setVisible(false);
                transactionMenu.setVisible(false);
            }
            case 2 -> {
                paymentsMenu.setVisible(false);
                dashboardMenu.setVisible(false);
                accountsMenu.setVisible(true);
                transactionMenu.setVisible(false);
            }
            case 3 -> {
                paymentsMenu.setVisible(true);
                dashboardMenu.setVisible(false);
                accountsMenu.setVisible(false);
                transactionMenu.setVisible(false);
            }
            case 4 -> {
                paymentsMenu.setVisible(false);
                dashboardMenu.setVisible(false);
                accountsMenu.setVisible(false);
                transactionMenu.setVisible(true);
            }
            default ->
                throw new AssertionError();
        }
    }

    public void saveTransactionsFile() {
        try {
            // Create a FileOutputStream to write to the BankDetails.dat file
            FileOutputStream file2 = new FileOutputStream("Transactions.dat");
            // Create an ObjectOutputStream using the FileOutputStream
            ObjectOutputStream outputFile2 = new ObjectOutputStream(file2);

            // Loop through the bankDetailsList and write each object to the ObjectOutputStream
            for (int i = 0; i < transactions.size(); i++) {
                outputFile2.writeObject(transactions.get(i));
            }
            // Close the ObjectOutputStream
            outputFile2.close();
        }
        catch (IOException e) {
            // Catch any IOExceptions and ignore them
        }
    }

    public void saveAccountsFile() {
        try {
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
        }
        catch (IOException e) {
            // Catch any IOExceptions and ignore them
        }
    }

    public void populateTransactions() {
        try {
            // Create a FileInputStream to read from the BankDetails.dat file
            FileInputStream file = new FileInputStream("Transactions.dat");
            // Create an ObjectInputStream using the FileInputStream
            ObjectInputStream inputFile = new ObjectInputStream(file);

            // Flag to indicate whether the end of the file has been reached
            boolean endOfFile = false;
            // Loop until the end of the file is reached
            while (!endOfFile) {
                try {
                    // Read an object from the ObjectInputStream and add it to the bankDetailsList
                    transactions.add((Transactions) inputFile.readObject());
                }
                catch (EOFException e) {
                    // If an EOFException is thrown, set the endOfFile flag to true to exit the loop
                    endOfFile = true;
                }
                catch (Exception f) {
                    JOptionPane.showMessageDialog(null, f);
                }
            }
            // Close the ObjectInputStream
            inputFile.close();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void populateAccounts() {
        try {
            // Create a FileInputStream to read from the BankDetails.dat file
            FileInputStream file = new FileInputStream("Accounts.dat");
            // Create an ObjectInputStream using the FileInputStream
            ObjectInputStream inputFile = new ObjectInputStream(file);

            // Flag to indicate whether the end of the file has been reached
            boolean endOfFile = false;
            // Loop until the end of the file is reached
            while (!endOfFile) {
                try {
                    // Read an object from the ObjectInputStream and add it to the bankDetailsList
                    accounts.add((Accounts) inputFile.readObject());
                }
                catch (EOFException e) {
                    // If an EOFException is thrown, set the endOfFile flag to true to exit the loop
                    endOfFile = true;
                }
                catch (Exception f) {
                    JOptionPane.showMessageDialog(null, f);
                }
            }
            // Close the ObjectInputStream
            inputFile.close();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void accountDashboard() {
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        welcomeLabel.setText(bankDetails.getName());

        double x = 0;
        for (int i = 0; i < userAccounts.size(); i++) {
            x += userAccounts.get(i).getBalanceOfAccount();
        }

        DecimalFormat df = new DecimalFormat("#.00");
        String s = df.format(x);
        if (x == 0.0) {
            totalBalance.setText("$ 0.00");
        }
        else {
            totalBalance.setText("$ " + s);
        }

        if (userAccounts.size() == 0) {
            dashboardAccounts.setVisible(false);
            dashboardAccounts1.setVisible(false);

        }
        else if (userAccounts.size() == 1) {
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
            accNo1Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo5Label.setText(userAccounts.get(0).getAccountName());
            accNo5Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
        }
        else if (userAccounts.size() == 2) {
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
            accNo1Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo2Label.setText(userAccounts.get(1).getAccountName());
            accNo2Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
            accNo5Label.setText(userAccounts.get(0).getAccountName());
            accNo5Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo6Label.setText(userAccounts.get(1).getAccountName());
            accNo6Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
        }
        else if (userAccounts.size() == 3) {
            dashboardAccounts.setVisible(true);
            accNo4.setVisible(false);
            accNo4Balance.setVisible(false);
            accNo4Label.setVisible(false);
            accNo8.setVisible(false);
            accNo8Label.setVisible(false);
            accNo8Balance.setVisible(false);
            accNo8Trash.setVisible(false);
            accNo1Label.setText(userAccounts.get(0).getAccountName());
            accNo1Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo2Label.setText(userAccounts.get(1).getAccountName());
            accNo2Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
            accNo3Label.setText(userAccounts.get(2).getAccountName());
            accNo3Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(2).getBalanceOfAccount()));
            accNo5Label.setText(userAccounts.get(0).getAccountName());
            accNo5Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(0).getBalanceOfAccount()));
            accNo6Label.setText(userAccounts.get(1).getAccountName());
            accNo6Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(1).getBalanceOfAccount()));
            accNo7Label.setText(userAccounts.get(2).getAccountName());
            accNo7Balance.setText(" Balance : $ \n" + String.valueOf(userAccounts.get(2).getBalanceOfAccount()));

        }
        else if (userAccounts.size() == 4) {
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

    public static int findBankDetailsByAccNo(String str) {
        // loop through the list
        for (int i = 0; i < r.bankDetailsList.size(); i++) {
            // get the current array
            BankDetails array = r.bankDetailsList.get(i);

            // if the first element of the array equals the search string, return the index
            if (array.getAccNo().equals(str)) {
                return i;
            }
        }

        // if the search string was not found, return -1
        return -1;
    }

    public static ArrayList<Transactions> transactionsByAccNo(ArrayList<Transactions> transactions, String AccNo) {
        // create an empty list to store the matching accounts
        ArrayList<Transactions> matchingAccounts = new ArrayList<>();

        // iterate over the accounts in the list
        for (Transactions transaction : transactions) {
            // if the account number of the current account matches the search query, add it to the list of matching accounts
            if (AccNo.equals(transaction.getSenderAccNo())) {
                matchingAccounts.add(transaction);
            }
        }

        // return the list of matching accounts
        return matchingAccounts;
    }

    /**
     * Finds all accounts in the given list that match the given account number.
     *
     * @param accounts the list of accounts to search
     * @param AccNo    the account number to search for
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
        transactionMenu = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        transactionHistoryButton = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        depositButton = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        withdrawButton = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        transactionsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transactionList = new javax.swing.JList<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        paymentsMenu = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        sendAccount = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        sendPerson = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        sendOtherAccPanel = new javax.swing.JPanel();
        accFromComboBox1 = new javax.swing.JComboBox<>();
        accNoInput = new javax.swing.JTextField();
        accOwnerName = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        confirmOtherAccButton = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        sendAccPanel = new javax.swing.JPanel();
        confirmAccButton = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        accFromComboBox = new javax.swing.JComboBox<>();
        accToComboBox = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        sendAccAmount = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();

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
        transactionsTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transactionsTabMouseClicked(evt);
            }
        });

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
        paymentsTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paymentsTabMouseClicked(evt);
            }
        });

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
                    .addComponent(logoutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(159, 159, 159)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashboardAccounts, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(119, 119, 119))
        );
        dashboardMenuLayout.setVerticalGroup(
            dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardMenuLayout.createSequentialGroup()
                .addGap(106, 106, 106)
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
                .addContainerGap(98, Short.MAX_VALUE))
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

        transactionMenu.setBackground(new java.awt.Color(232, 232, 232));

        jLabel20.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("TRANSACTIONS");

        jLabel5.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Transactions");

        javax.swing.GroupLayout transactionHistoryButtonLayout = new javax.swing.GroupLayout(transactionHistoryButton);
        transactionHistoryButton.setLayout(transactionHistoryButtonLayout);
        transactionHistoryButtonLayout.setHorizontalGroup(
            transactionHistoryButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionHistoryButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );
        transactionHistoryButtonLayout.setVerticalGroup(
            transactionHistoryButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionHistoryButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel25.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Deposit");

        javax.swing.GroupLayout depositButtonLayout = new javax.swing.GroupLayout(depositButton);
        depositButton.setLayout(depositButtonLayout);
        depositButtonLayout.setHorizontalGroup(
            depositButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depositButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );
        depositButtonLayout.setVerticalGroup(
            depositButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depositButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel26.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Withdraw");

        javax.swing.GroupLayout withdrawButtonLayout = new javax.swing.GroupLayout(withdrawButton);
        withdrawButton.setLayout(withdrawButtonLayout);
        withdrawButtonLayout.setHorizontalGroup(
            withdrawButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );
        withdrawButtonLayout.setVerticalGroup(
            withdrawButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        transactionsPanel.setBackground(new java.awt.Color(232, 232, 232));

        jScrollPane1.setBackground(new java.awt.Color(232, 232, 232));

        transactionList.setBackground(new java.awt.Color(232, 232, 232));
        transactionList.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        transactionList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "No Transactions!" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        transactionList.setToolTipText("");
        jScrollPane1.setViewportView(transactionList);

        jLabel27.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel27.setText("Sender");

        jLabel28.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel28.setText("Receivent");

        jLabel29.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel29.setText("Date and Time");

        jLabel30.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel30.setText("Amount");

        jLabel31.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel31.setText("Payment Source");

        javax.swing.GroupLayout transactionsPanelLayout = new javax.swing.GroupLayout(transactionsPanel);
        transactionsPanel.setLayout(transactionsPanelLayout);
        transactionsPanelLayout.setHorizontalGroup(
            transactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionsPanelLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(transactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(transactionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(91, 91, 91)
                        .addComponent(jLabel28)
                        .addGap(123, 123, 123)
                        .addComponent(jLabel29)
                        .addGap(106, 106, 106)
                        .addComponent(jLabel30)
                        .addGap(68, 68, 68)
                        .addComponent(jLabel31))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        transactionsPanelLayout.setVerticalGroup(
            transactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionsPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(transactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout transactionMenuLayout = new javax.swing.GroupLayout(transactionMenu);
        transactionMenu.setLayout(transactionMenuLayout);
        transactionMenuLayout.setHorizontalGroup(
            transactionMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionMenuLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(transactionHistoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165)
                .addComponent(depositButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(withdrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
            .addGroup(transactionMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
            .addComponent(transactionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        transactionMenuLayout.setVerticalGroup(
            transactionMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionMenuLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(transactionMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(transactionHistoryButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(depositButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(withdrawButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(transactionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Menus.add(transactionMenu);
        transactionMenu.setBounds(0, 0, 940, 700);

        paymentsMenu.setBackground(new java.awt.Color(232, 232, 232));
        paymentsMenu.setForeground(new java.awt.Color(51, 51, 51));
        paymentsMenu.setMaximumSize(new java.awt.Dimension(994, 695));
        paymentsMenu.setMinimumSize(new java.awt.Dimension(994, 695));
        paymentsMenu.setPreferredSize(new java.awt.Dimension(994, 695));
        paymentsMenu.setLayout(null);

        jLabel12.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("PAYMENTS");
        paymentsMenu.add(jLabel12);
        jLabel12.setBounds(0, 30, 937, 55);

        sendAccount.setForeground(new java.awt.Color(51, 51, 51));
        sendAccount.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sendAccount.setMaximumSize(new java.awt.Dimension(210, 60));
        sendAccount.setMinimumSize(new java.awt.Dimension(210, 60));
        sendAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendAccountMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("transfer to my own account");
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

        paymentsMenu.add(sendAccount);
        sendAccount.setBounds(116, 92, 210, 60);

        sendPerson.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sendPerson.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendPersonMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("transfer to another account");
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

        paymentsMenu.add(sendPerson);
        sendPerson.setBounds(604, 92, 210, 60);

        sendOtherAccPanel.setBackground(new java.awt.Color(232, 232, 232));
        sendOtherAccPanel.setMaximumSize(new java.awt.Dimension(994, 531));
        sendOtherAccPanel.setMinimumSize(new java.awt.Dimension(994, 531));
        sendOtherAccPanel.setPreferredSize(new java.awt.Dimension(994, 531));

        accFromComboBox1.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        accFromComboBox1.setMaximumRowCount(4);

        accNoInput.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        accNoInput.setToolTipText("");
        accNoInput.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                accNoInputİnputMethodTextChanged(evt);
            }
        });

        accOwnerName.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        accOwnerName.setText("Account could not found!");

        jTextField2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        confirmOtherAccButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmOtherAccButtonMouseClicked(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Confirm");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout confirmOtherAccButtonLayout = new javax.swing.GroupLayout(confirmOtherAccButton);
        confirmOtherAccButton.setLayout(confirmOtherAccButtonLayout);
        confirmOtherAccButtonLayout.setHorizontalGroup(
            confirmOtherAccButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confirmOtherAccButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        confirmOtherAccButtonLayout.setVerticalGroup(
            confirmOtherAccButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confirmOtherAccButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel21.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel21.setText("Amount");

        jLabel22.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel22.setText("Account to send");

        jLabel24.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel24.setText("Receivents account number");

        javax.swing.GroupLayout sendOtherAccPanelLayout = new javax.swing.GroupLayout(sendOtherAccPanel);
        sendOtherAccPanel.setLayout(sendOtherAccPanelLayout);
        sendOtherAccPanelLayout.setHorizontalGroup(
            sendOtherAccPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sendOtherAccPanelLayout.createSequentialGroup()
                .addGap(360, 360, 360)
                .addGroup(sendOtherAccPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel24)
                    .addComponent(jLabel21)
                    .addGroup(sendOtherAccPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(accFromComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(accNoInput)
                        .addComponent(accOwnerName, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                        .addComponent(jTextField2)
                        .addComponent(confirmOtherAccButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(394, Short.MAX_VALUE))
        );
        sendOtherAccPanelLayout.setVerticalGroup(
            sendOtherAccPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sendOtherAccPanelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(accFromComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(accNoInput, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(accOwnerName)
                .addGap(13, 13, 13)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(confirmOtherAccButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        paymentsMenu.add(sendOtherAccPanel);
        sendOtherAccPanel.setBounds(0, 170, 1000, 530);

        sendAccPanel.setBackground(new java.awt.Color(232, 232, 232));
        sendAccPanel.setMaximumSize(new java.awt.Dimension(994, 531));
        sendAccPanel.setLayout(null);

        confirmAccButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confirmAccButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmAccButtonMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Confirm");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout confirmAccButtonLayout = new javax.swing.GroupLayout(confirmAccButton);
        confirmAccButton.setLayout(confirmAccButtonLayout);
        confirmAccButtonLayout.setHorizontalGroup(
            confirmAccButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confirmAccButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        confirmAccButtonLayout.setVerticalGroup(
            confirmAccButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confirmAccButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addContainerGap())
        );

        sendAccPanel.add(confirmAccButton);
        confirmAccButton.setBounds(337, 406, 250, 65);

        accFromComboBox.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        accFromComboBox.setMaximumRowCount(4);
        sendAccPanel.add(accFromComboBox);
        accFromComboBox.setBounds(340, 60, 250, 65);

        accToComboBox.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        accToComboBox.setMaximumRowCount(4);
        sendAccPanel.add(accToComboBox);
        accToComboBox.setBounds(340, 200, 250, 65);

        jLabel17.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel17.setText("From");
        sendAccPanel.add(jLabel17);
        jLabel17.setBounds(337, 39, 35, 22);

        jLabel18.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel18.setText("Amout");
        sendAccPanel.add(jLabel18);
        jLabel18.setBounds(340, 290, 60, 22);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/switch_icon.png"))); // NOI18N
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        sendAccPanel.add(jLabel16);
        jLabel16.setBounds(430, 140, 48, 48);

        sendAccAmount.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        sendAccPanel.add(sendAccAmount);
        sendAccAmount.setBounds(340, 310, 250, 60);

        jLabel19.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel19.setText("To");
        sendAccPanel.add(jLabel19);
        jLabel19.setBounds(340, 180, 17, 22);

        paymentsMenu.add(sendAccPanel);
        sendAccPanel.setBounds(0, 164, 994, 531);

        Menus.add(paymentsMenu);
        paymentsMenu.setBounds(0, 0, 930, 690);

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

    private void logoutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMouseClicked
        int result = JOptionPane.showConfirmDialog(null, "Sure? You want to log out?", "Log Out?",
                JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            lastPage = 1;
            this.dispose();
            new LoginMenu().setVisible(true);
        }
    }//GEN-LAST:event_logoutButtonMouseClicked

    private void accountsTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountsTabMouseClicked
        lastPage = 2;
        paymentsMenu.setVisible(false);
        dashboardMenu.setVisible(false);
        accountsMenu.setVisible(true);
        sendAccPanel.setVisible(true);
        transactionMenu.setVisible(false);
    }//GEN-LAST:event_accountsTabMouseClicked

    private void dashboardTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardTabMouseClicked
        lastPage = 1;
        paymentsMenu.setVisible(false);
        dashboardMenu.setVisible(true);
        accountsMenu.setVisible(false);
        transactionMenu.setVisible(false);
    }//GEN-LAST:event_dashboardTabMouseClicked

    private void createAccountButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createAccountButtonPanelMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        if (userAccounts.size() == 4) {
            JOptionPane.showMessageDialog(null, "You can't create more than 4 accounts!", "Warning!", JOptionPane.ERROR_MESSAGE);
        }
        else {
            String input = JOptionPane.showInputDialog("Please Enter Your Account Name.");
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You have to enter a name to your account!");
            }
            else {
                Accounts newAccount = new Accounts(userAccNo, accNumberCounter, 0, input);

                accounts.add(newAccount);

                JOptionPane.showMessageDialog(null, "Your new account saved.");
                saveAccountsFile();
                accNumberCounter++;
                dispose();
                new MainBankMenu().setVisible(true);
            }
        }
    }//GEN-LAST:event_createAccountButtonPanelMouseClicked

    private void accNo5TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo5TrashMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        int x = JOptionPane.showConfirmDialog(null,
                "You are about to delete the account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            Accounts accountToRemove = userAccounts.get(0);
            accounts.remove(accountToRemove);

            saveAccountsFile();
            accNumberCounter--;
            dispose();
            new MainBankMenu().setVisible(true);

        }
    }//GEN-LAST:event_accNo5TrashMouseClicked

    private void accNo6TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo6TrashMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        int x = JOptionPane.showConfirmDialog(null,
                "You are about to delete the account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            Accounts accountToRemove = userAccounts.get(1);
            accounts.remove(accountToRemove);

            saveAccountsFile();
            accNumberCounter--;
            dispose();
            new MainBankMenu().setVisible(true);

        }
    }//GEN-LAST:event_accNo6TrashMouseClicked

    private void accNo7TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo7TrashMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        int x = JOptionPane.showConfirmDialog(null,
                "You are about to delete the account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            Accounts accountToRemove = userAccounts.get(2);
            accounts.remove(accountToRemove);

            saveAccountsFile();
            accNumberCounter--;
            dispose();
            new MainBankMenu().setVisible(true);

        }
    }//GEN-LAST:event_accNo7TrashMouseClicked

    private void accNo8TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo8TrashMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        int x = JOptionPane.showConfirmDialog(null,
                "You are about to delete the account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);
        if (x == 0) {
            Accounts accountToRemove = userAccounts.get(3);
            accounts.remove(accountToRemove);

            saveAccountsFile();
            accNumberCounter--;
            dispose();
            new MainBankMenu().setVisible(true);

        }
    }//GEN-LAST:event_accNo8TrashMouseClicked

    private void paymentsTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paymentsTabMouseClicked
        lastPage = 3;
        paymentsMenu.setVisible(true);
        dashboardMenu.setVisible(false);
        accountsMenu.setVisible(false);
        transactionMenu.setVisible(false);
    }//GEN-LAST:event_paymentsTabMouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        int senderAccIndex = accFromComboBox.getSelectedIndex();
        int receverAccIndex = accToComboBox.getSelectedIndex();
        accFromComboBox.setSelectedIndex(receverAccIndex);
        accToComboBox.setSelectedIndex(senderAccIndex);
    }//GEN-LAST:event_jLabel16MouseClicked

    private void confirmAccButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmAccButtonMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        int senderAccIndex = accFromComboBox.getSelectedIndex();
        int receiverAccIndex = accToComboBox.getSelectedIndex();
        double senderBalance = userAccounts.get(senderAccIndex).getBalanceOfAccount();
        double amountInput;

        if (senderAccIndex == receiverAccIndex) {
            JOptionPane.showMessageDialog(null, "You cannot transfer money to the same account!", "Error!", JOptionPane.WARNING_MESSAGE);
        }
        else if (senderAccIndex == -1 || receiverAccIndex == -1) {
            int x = JOptionPane.showConfirmDialog(null, "You don't have any account.\nDo you want to open a new account?", "No Account!", JOptionPane.YES_NO_OPTION);
            if (x == 0) {
                accountsTabMouseClicked(evt);
                createAccountButtonPanelMouseClicked(evt);
            }
        }
        else {
            try {
                amountInput = Double.parseDouble(sendAccAmount.getText().trim());
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "your amount can only consist of numbers!", "Warning!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (amountInput > senderBalance) {
                JOptionPane.showMessageDialog(null, "Insufficient balance!", "Warning!", JOptionPane.WARNING_MESSAGE);
            }
            else {
                Accounts senderAccount = userAccounts.get(senderAccIndex);
                Accounts receiverAccount = userAccounts.get(receiverAccIndex);

                senderAccount.setBalanceOfAccount(senderAccount.getBalanceOfAccount() - amountInput);
                receiverAccount.setBalanceOfAccount(receiverAccount.getBalanceOfAccount() + amountInput);

                accounts.set(accounts.indexOf(userAccounts.get(senderAccIndex)), senderAccount);
                accounts.set(accounts.indexOf(userAccounts.get(receiverAccIndex)), receiverAccount);
                saveAccountsFile();
                JOptionPane.showMessageDialog(null, "The transfer was successful!");
                dispose();
                new MainBankMenu().setVisible(true);
            }
        }
    }//GEN-LAST:event_confirmAccButtonMouseClicked

    private void sendPersonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendPersonMouseClicked
        sendOtherAccPanel.setVisible(true);
        sendAccPanel.setVisible(false);
    }//GEN-LAST:event_sendPersonMouseClicked

    private void sendAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendAccountMouseClicked
        sendOtherAccPanel.setVisible(false);
        sendAccPanel.setVisible(true);
    }//GEN-LAST:event_sendAccountMouseClicked

    private void confirmOtherAccButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmOtherAccButtonMouseClicked
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        String receiventAccNo = accNoInput.getText().trim();
        int senderAccIndex = accFromComboBox1.getSelectedIndex();
        Accounts senderAcc = userAccounts.get(senderAccIndex);
        int bankDetailIndex = findBankDetailsByAccNo(receiventAccNo);
        double amountInput;
        if (bankDetailIndex == -1) {
            JOptionPane.showMessageDialog(null, "The account you are trying to send does not exist!", "Warning!", JOptionPane.WARNING_MESSAGE);
        }
        else if (jTextField2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the details!", "Warning!", JOptionPane.WARNING_MESSAGE);
        }
        try {
            amountInput = Double.parseDouble(jTextField2.getText().trim());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "your amount can only consist of numbers!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (senderAcc.getBalanceOfAccount() < amountInput) {
            JOptionPane.showMessageDialog(null, "insufficient balance!", "Warning!", JOptionPane.WARNING_MESSAGE);
        }
        else {
            ArrayList<Accounts> receiventsAccounts = findAccountsByAccNo(accounts, receiventAccNo);
            Accounts senderAccount = userAccounts.get(senderAccIndex);
            Accounts receiverAccount = receiventsAccounts.get(0);

            senderAccount.setBalanceOfAccount(senderAccount.getBalanceOfAccount() - amountInput);
            receiverAccount.setBalanceOfAccount(receiverAccount.getBalanceOfAccount() + amountInput);

            accounts.set(accounts.indexOf(senderAccount), senderAccount);
            accounts.set(accounts.indexOf(receiverAccount), receiverAccount);

            Transactions newTransaction = new Transactions(userAccNo, receiventAccNo, systemDate, amountInput, senderAccount.getAccountName());

            transactions.add(newTransaction);

            saveTransactionsFile();

            saveAccountsFile();
            JOptionPane.showMessageDialog(null, "The transfer was successful!");
            dispose();
            new MainBankMenu().setVisible(true);
        }
    }//GEN-LAST:event_confirmOtherAccButtonMouseClicked

    private void accNoInputİnputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_accNoInputİnputMethodTextChanged

    }//GEN-LAST:event_accNoInputİnputMethodTextChanged

    private void transactionsTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transactionsTabMouseClicked
        lastPage = 4;
        paymentsMenu.setVisible(false);
        dashboardMenu.setVisible(false);
        accountsMenu.setVisible(false);
        sendAccPanel.setVisible(false);
        transactionMenu.setVisible(true);
    }//GEN-LAST:event_transactionsTabMouseClicked

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
            java.util.logging.Logger.getLogger(MainBankMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainBankMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainBankMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
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
    private javax.swing.JComboBox<String> accFromComboBox;
    private javax.swing.JComboBox<String> accFromComboBox1;
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
    private javax.swing.JTextField accNoInput;
    private javax.swing.JLabel accOwnerName;
    private javax.swing.JComboBox<String> accToComboBox;
    private javax.swing.JPanel accountsMenu;
    private javax.swing.JPanel accountsTab;
    private javax.swing.JPanel confirmAccButton;
    private javax.swing.JPanel confirmOtherAccButton;
    private javax.swing.JPanel createAccountButtonPanel;
    private javax.swing.JPanel dashboardAccounts;
    private javax.swing.JPanel dashboardAccounts1;
    private javax.swing.JPanel dashboardMenu;
    private javax.swing.JPanel dashboardTab;
    private javax.swing.JPanel depositButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel logoutButton;
    private javax.swing.JPanel paymentsMenu;
    private javax.swing.JPanel paymentsTab;
    private javax.swing.JTextField sendAccAmount;
    private javax.swing.JPanel sendAccPanel;
    private javax.swing.JPanel sendAccount;
    private javax.swing.JPanel sendOtherAccPanel;
    private javax.swing.JPanel sendPerson;
    private javax.swing.JLabel totalBalance;
    private javax.swing.JPanel transactionHistoryButton;
    private javax.swing.JList<String> transactionList;
    private javax.swing.JPanel transactionMenu;
    private javax.swing.JPanel transactionsPanel;
    private javax.swing.JPanel transactionsTab;
    private javax.swing.JLabel welcomeLabel;
    private javax.swing.JLabel welcomeLabel1;
    private javax.swing.JPanel withdrawButton;
    // End of variables declaration//GEN-END:variables
}
