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

    public String systemDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    public static String userAccNo; //get user's account number from login menu.

    public static int accNumberCounter = 2; //because first account created from register counter starts from 2.

    public static int lastPage = 1; //depending of the lastpage int when form re-opens it can continue...

    //getting user's bank details with user account number
    public static BankDetails getBankDetails() {
        return RegisterMenu.bankDetailsList.get(findBankDetailsByAccNo(userAccNo));
    }

    static ArrayList<Transactions> transactions = new ArrayList<>();
    static ArrayList<Accounts> accounts = new ArrayList<>();

    public MainBankMenu() {
        initComponents();
        accounts.clear();
        transactions.clear();
        RegisterMenu.bankDetailsList.clear();
        RegisterMenu.populateBankDetailsList();
        populateTransactions();

        populateAccounts();
        accountDashboard();
        lastPage(lastPage);

        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        ArrayList<Transactions> userTransactions = transactionsByAccNo(transactions, userAccNo);

        String[] userAccountArray = new String[userAccounts.size()];
        for (int i = 0; i < userAccounts.size(); i++) {
            userAccountArray[i] = userAccounts.get(i).getAccountName();
        }

        getTransactionHistory(userTransactions);

        accFromComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(userAccountArray));
        accFromComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(userAccountArray));
        accToComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(userAccountArray));
        depositAccComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(userAccountArray));
        withdrawAccComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(userAccountArray));

        /*
        This code adds the DocumentListener object to the document of the accNoInput text field,
        so that the DocumentListener can listen for changes to the text in the text field.
        When a change is detected, the updateLabel method is called.
         */
        // Create a new DocumentListener object
        DocumentListener listener = new DocumentListener() {
            // This method is called when text is inserted into the document
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabel();
            }
            // This method is called when text is removed from the document

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabel();
            }
            // This method is called when the document is changed in some other way (e.g. text style is changed)

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabel();
            }

        };
        // Add the DocumentListener to the document of the accNoInput text field
        accNoInput.getDocument().addDocumentListener(listener);

    }

    private void getTransactionHistory(ArrayList<Transactions> userTransactions) {
        if (!userTransactions.isEmpty()) {
            final String[] userTransactionSenderNameArray = userTransactionSenderNameConstructer();
            final String[] userTransactionReceiventNameArray = userTransactionRecieventNameConstructer();
            final String[] userTransactionDateArray = userTransactionDateConstructer(userTransactions);
            final String[] userTransactionAmountArray = userTransactionAmountConstructer(userTransactions);
            final String[] userTransactionSourceArray = userTransactionSourceConstructer(userTransactions);

            SenderNameTransactionList.setModel(new javax.swing.AbstractListModel<String>() {
                String[] strings = userTransactionSenderNameArray;

                @Override
                public int getSize() {
                    return strings.length;
                }

                @Override
                public String getElementAt(int i) {
                    return strings[i];
                }

            });
            ReceiventNameTransactionList.setModel(new javax.swing.AbstractListModel<String>() {
                String[] strings = userTransactionReceiventNameArray;

                @Override
                public int getSize() {
                    return strings.length;
                }

                @Override
                public String getElementAt(int i) {
                    return strings[i];
                }

            });
            DateTransactionList.setModel(new javax.swing.AbstractListModel<String>() {
                String[] strings = userTransactionDateArray;

                @Override
                public int getSize() {
                    return strings.length;
                }

                @Override
                public String getElementAt(int i) {
                    return strings[i];
                }

            });
            AmountTransactionList.setModel(new javax.swing.AbstractListModel<String>() {
                String[] strings = userTransactionAmountArray;

                @Override
                public int getSize() {
                    return strings.length;
                }

                @Override
                public String getElementAt(int i) {
                    return strings[i];
                }

            });
            SourceTransactionList.setModel(new javax.swing.AbstractListModel<String>() {
                String[] strings = userTransactionSourceArray;

                @Override
                public int getSize() {
                    return strings.length;
                }

                @Override
                public String getElementAt(int i) {
                    return strings[i];
                }

            });
        }
    }

    private static String[] userTransactionSenderNameConstructer() {
        // Get the transactions associated with the user's account number
        ArrayList<Transactions> userTransactions = transactionsByAccNo(transactions, userAccNo);

        // Create a new string array with the same size as the user's transactions list
        String[] userTransactionArray = new String[userTransactions.size()];

        // Iterate through the user's transactions list
        for (int i = 0; i < userTransactions.size(); i++) {

            // Set the element at the current index to the sender account name of the current transaction
            userTransactionArray[i] = userTransactions.get(i).getSenderAcc();
        }
        // Return the completed string array
        return userTransactionArray;
    }

    private static String[] userTransactionRecieventNameConstructer() {
        // Get the transactions associated with the user's account number
        ArrayList<Transactions> userTransactions = transactionsByAccNo(transactions, userAccNo);

        // Create a new string array with the same size as the user's transactions list
        String[] userTransactionArray = new String[userTransactions.size()];

        // Iterate through the user's transactions list
        for (int i = 0; i < userTransactions.size(); i++) {

            // Set the element at the current index to the receiver account name of the current transaction
            userTransactionArray[i] = userTransactions.get(i).getReceiverAcc();
        }
        // Return the completed string array
        return userTransactionArray;
    }

    public static String[] userTransactionDateConstructer(ArrayList<Transactions> userTransactions) {
        // Create a new string array with the same size as the user's transactions list
        String[] userTransactionArray = new String[userTransactions.size()];

        // Iterate through the user's transactions list
        for (int i = 0; i < userTransactions.size(); i++) {
            // Set the element at the current index to the date of the current transaction
            userTransactionArray[i] = userTransactions.get(i).getTransactionDate();
        }
        // Return the completed string array
        return userTransactionArray;
    }

    public static String[] userTransactionAmountConstructer(ArrayList<Transactions> userTransactions) {
        // Create a new string array with the same size as the user's transactions list
        String[] userTransactionArray = new String[userTransactions.size()];

        // Iterate through the user's transactions list
        for (int i = 0; i < userTransactions.size(); i++) {
            // Set the element at the current index to the amount of the current transaction, formatted as a dollar amount
            userTransactionArray[i] = "$" + String.valueOf(userTransactions.get(i).getAmount());
        }
        // Return the completed string array
        return userTransactionArray;
    }

    public static String[] userTransactionSourceConstructer(ArrayList<Transactions> userTransactions) {
        // Create a new string array with the same size as the user's transactions list
        String[] userTransactionArray = new String[userTransactions.size()];

        // Iterate through the user's transactions list
        for (int i = 0; i < userTransactions.size(); i++) {
            // Set the element at the current index to the payment source of the current transaction
            userTransactionArray[i] = userTransactions.get(i).getPaymentSource();
        }
        // Return the completed string array
        return userTransactionArray;
    }

    private void updateLabel() {

        // Get the account number from the text field
        String accNo = accNoInput.getText();

        // Find the bank details associated with the account number
        int bankDetailsIndex = findBankDetailsByAccNo(accNo);

        // Try to parse the account number as an integer
        try {
            Integer.parseInt(accNo);
        }
        // If the parsing fails, set the account owner name label to an error message
        catch (NumberFormatException e) {
            accOwnerName.setText("You must enter a 6 digit account number.");
        }

        // If the bank details cannot be found, set the account owner name label to an error message
        if (bankDetailsIndex == -1) {
            accOwnerName.setText("This account cannot be found.");
        }
        // If the user is trying to transfer to their own account, set the account owner name label to an error message
        else if (getBankDetails().getAccNo().equals(getBankDetails().getName())) {
            accOwnerName.setText("You cannot transfer to your own account!");
        }
        // Otherwise, set the account owner name label to the masked name of the account owner
        else {
            String name = RegisterMenu.bankDetailsList.get(findBankDetailsByAccNo(accNo)).getName();
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

    public final void lastPage(int x) {
        switch (x) {
            case 1 -> {
                paymentsMenu.setVisible(false);
                dashboardMenu.setVisible(true);
                accountsMenu.setVisible(false);
                transactionMenu.setVisible(false);

                sendAccPanel.setVisible(false);
                sendOtherAccPanel.setVisible(false);
                depositPanel.setVisible(false);
                withdrawPanel.setVisible(false);
            }
            case 2 -> {
                paymentsMenu.setVisible(false);
                dashboardMenu.setVisible(false);
                accountsMenu.setVisible(true);
                transactionMenu.setVisible(false);

                sendAccPanel.setVisible(false);
                sendOtherAccPanel.setVisible(false);
                depositPanel.setVisible(false);
                withdrawPanel.setVisible(false);
            }
            case 3 -> {
                paymentsMenu.setVisible(true);
                dashboardMenu.setVisible(false);
                accountsMenu.setVisible(false);
                transactionMenu.setVisible(false);

                sendAccPanel.setVisible(false);
                sendOtherAccPanel.setVisible(false);
                depositPanel.setVisible(false);
                withdrawPanel.setVisible(false);
            }
            case 4 -> {
                paymentsMenu.setVisible(false);
                dashboardMenu.setVisible(false);
                accountsMenu.setVisible(false);
                transactionMenu.setVisible(true);

                sendAccPanel.setVisible(false);
                sendOtherAccPanel.setVisible(false);
                depositPanel.setVisible(false);
                withdrawPanel.setVisible(false);
            }
            default ->
                throw new AssertionError();
        }
    }

    public static void saveTransactionsFile() {
        try {
            // Create a FileOutputStream to write to the Transactions.dat file
            FileOutputStream file2 = new FileOutputStream("Transactions.dat");
            // Create an ObjectOutputStream using the FileOutputStream
            ObjectOutputStream outputFile2 = new ObjectOutputStream(file2);

            // Loop through the transactions and write each object to the ObjectOutputStream
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

    public static void saveAccountsFile() {
        try {
            // Create a FileOutputStream to write to the Accounts.dat file
            FileOutputStream file2 = new FileOutputStream("Accounts.dat");
            // Create an ObjectOutputStream using the FileOutputStream
            ObjectOutputStream outputFile2 = new ObjectOutputStream(file2);

            // Loop through the accounts and write each object to the ObjectOutputStream
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

    public static final void populateTransactions() {
        try {
            // Create a FileInputStream to read from the Transactions.dat file
            FileInputStream file = new FileInputStream("Transactions.dat");
            // Create an ObjectInputStream using the FileInputStream
            ObjectInputStream inputFile = new ObjectInputStream(file);

            // Flag to indicate whether the end of the file has been reached
            boolean endOfFile = false;
            // Loop until the end of the file is reached
            while (!endOfFile) {
                try {
                    // Read an object from the ObjectInputStream and add it to the transactions
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

    public static final void populateAccounts() {
        try {
            // Create a FileInputStream to read from the Accounts.dat file
            FileInputStream file = new FileInputStream("Accounts.dat");
            // Create an ObjectInputStream using the FileInputStream
            ObjectInputStream inputFile = new ObjectInputStream(file);

            // Flag to indicate whether the end of the file has been reached
            boolean endOfFile = false;
            // Loop until the end of the file is reached
            while (!endOfFile) {
                try {
                    // Read an object from the ObjectInputStream and add it to the accounts
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

    //this function used to only show certain pages in main menu.
    public final void accountDashboard() {
        // Get the user's accounts
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        // Set the welcome label to the user's name
        welcomeLabel.setText(getBankDetails().getName());

        // Initialize a variable to store the total balance
        double x = 0;

        // Iterate through the user's accounts
        for (int i = 0; i < userAccounts.size(); i++) {
            // Add the balance of the current account to the total balance
            x += userAccounts.get(i).getBalanceOfAccount();
        }

        // Create a DecimalFormat object to format the total balance as a dollar amount
        DecimalFormat df = new DecimalFormat("#0.00");

        // Format the total balance as a string
        String s = df.format(x);

        // If the total balance is zero, set the total balance label to "$ 0.00"
        if (x == 0.0) {
            totalBalance.setText("$ 0.00");
        }
        // Otherwise, set the total balance label to the formatted total balance
        else {
            totalBalance.setText("$ " + s);
        }
        String acc1bal;
        String acc2bal;
        String acc3bal;
        String acc4bal;
        //rest of the code is only just showing and hiding account card images and setting the balances.
        switch (userAccounts.size()) {
            case 0:
                dashboardAccounts.setVisible(false);
                dashboardAccounts1.setVisible(false);
                break;
            case 1:
                acc1bal = df.format(userAccounts.get(0).getBalanceOfAccount());
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
                accNo1Balance.setText(" Balance : $ \n" + acc1bal);
                accNo5Label.setText(userAccounts.get(0).getAccountName());
                accNo5Balance.setText(" Balance : $ \n" + acc1bal);
                break;
            case 2:
                acc1bal = df.format(userAccounts.get(0).getBalanceOfAccount());
                acc2bal = df.format(userAccounts.get(1).getBalanceOfAccount());
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
                accNo1Balance.setText(" Balance : $ \n" + acc1bal);
                accNo2Label.setText(userAccounts.get(1).getAccountName());
                accNo2Balance.setText(" Balance : $ \n" + acc2bal);
                accNo5Label.setText(userAccounts.get(0).getAccountName());
                accNo5Balance.setText(" Balance : $ \n" + acc1bal);
                accNo6Label.setText(userAccounts.get(1).getAccountName());
                accNo6Balance.setText(" Balance : $ \n" + acc2bal);
                break;
            case 3:
                acc1bal = df.format(userAccounts.get(0).getBalanceOfAccount());
                acc2bal = df.format(userAccounts.get(1).getBalanceOfAccount());
                acc3bal = df.format(userAccounts.get(2).getBalanceOfAccount());
                dashboardAccounts.setVisible(true);
                accNo4.setVisible(false);
                accNo4Balance.setVisible(false);
                accNo4Label.setVisible(false);
                accNo8.setVisible(false);
                accNo8Label.setVisible(false);
                accNo8Balance.setVisible(false);
                accNo8Trash.setVisible(false);
                accNo1Label.setText(userAccounts.get(0).getAccountName());
                accNo1Balance.setText(" Balance : $ \n" + acc1bal);
                accNo2Label.setText(userAccounts.get(1).getAccountName());
                accNo2Balance.setText(" Balance : $ \n" + acc2bal);
                accNo3Label.setText(userAccounts.get(2).getAccountName());
                accNo3Balance.setText(" Balance : $ \n" + acc3bal);
                accNo5Label.setText(userAccounts.get(0).getAccountName());
                accNo5Balance.setText(" Balance : $ \n" + acc1bal);
                accNo6Label.setText(userAccounts.get(1).getAccountName());
                accNo6Balance.setText(" Balance : $ \n" + acc2bal);
                accNo7Label.setText(userAccounts.get(2).getAccountName());
                accNo7Balance.setText(" Balance : $ \n" + acc3bal);
                break;
            case 4:
                acc1bal = df.format(userAccounts.get(0).getBalanceOfAccount());
                acc2bal = df.format(userAccounts.get(1).getBalanceOfAccount());
                acc3bal = df.format(userAccounts.get(2).getBalanceOfAccount());
                acc4bal = df.format(userAccounts.get(3).getBalanceOfAccount());
                dashboardAccounts.setVisible(true);
                dashboardAccounts1.setVisible(true);
                accNo1Label.setText(userAccounts.get(0).getAccountName());
                accNo1Balance.setText(" Balance : \n" + acc1bal);
                accNo2Label.setText(userAccounts.get(1).getAccountName());
                accNo2Balance.setText(" Balance : \n" + acc2bal);
                accNo3Label.setText(userAccounts.get(2).getAccountName());
                accNo3Balance.setText(" Balance : \n" + acc3bal);
                accNo4Label.setText(userAccounts.get(3).getAccountName());
                accNo4Balance.setText(" Balance : \n" + acc4bal);
                accNo5Label.setText(userAccounts.get(0).getAccountName());
                accNo5Balance.setText(" Balance : \n" + acc1bal);
                accNo6Label.setText(userAccounts.get(1).getAccountName());
                accNo6Balance.setText(" Balance : \n" + acc2bal);
                accNo7Label.setText(userAccounts.get(2).getAccountName());
                accNo7Balance.setText(" Balance : \n" + acc3bal);
                accNo8Label.setText(userAccounts.get(3).getAccountName());
                accNo8Balance.setText(" Balance : \n" + acc4bal);
                break;
            default:
                break;
        }
    }

    /**
     * Finds bank details that match the given account number.
     *
     * @param str the account number to search
     * @return index of bank details
     */
    public static int findBankDetailsByAccNo(String str) {
        // loop through the list
        for (int i = 0; i < RegisterMenu.bankDetailsList.size(); i++) {
            // get the current array
            BankDetails array = RegisterMenu.bankDetailsList.get(i);

            // if the first element of the array equals the search string, return the index
            if (array.getAccNo().equals(str)) {
                return i;
            }
        }

        // if the search string was not found, return -1
        return -1;
    }

    /**
     * Finds all transactions in the given list that match the given account
     * number.
     *
     * @param transactions the list of transactions to search
     * @param AccNo        the account number to search for
     * @return a list of matching accounts
     */
    public static ArrayList<Transactions> transactionsByAccNo(ArrayList<Transactions> transactions, String AccNo) {
        // create an empty list to store the matching accounts
        ArrayList<Transactions> matchingAccounts = new ArrayList<>();

        // iterate over the transactions in the list
        for (Transactions transaction : transactions) {
            // if the account number of the current transactions matches the search query, add it to the list of matching transactions
            if (AccNo.equals(transaction.getUserAccNo())) {
                matchingAccounts.add(transaction);
            }
        }

        // return the list of matching transactions
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
        withdrawButtn = new javax.swing.JLabel();
        transactionsPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        SourceTransactionList = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        AmountTransactionList = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        DateTransactionList = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        ReceiventNameTransactionList = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        SenderNameTransactionList = new javax.swing.JList<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        depositPanel = new javax.swing.JPanel();
        depositAccComboBox = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        depositAccButton = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        withdrawPanel = new javax.swing.JPanel();
        withdrawAccComboBox = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        withdrawAccButton = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
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
        switchButton = new javax.swing.JLabel();
        sendAccAmount = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Bank Menu");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        Menus.setLayout(new javax.swing.OverlayLayout(Menus));

        dashboardMenu.setBackground(new java.awt.Color(232, 232, 232));
        dashboardMenu.setMaximumSize(new java.awt.Dimension(994, 695));
        dashboardMenu.setMinimumSize(new java.awt.Dimension(994, 695));
        dashboardMenu.setName(""); // NOI18N

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel7.setText("Total Balance");

        totalBalance.setFont(new java.awt.Font("Poppins", 0, 24)); // NOI18N
        totalBalance.setText("$ 999,999,999.99");

        jLabel10.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
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
                    .addComponent(dashboardAccounts, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalBalance)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(381, Short.MAX_VALUE))
        );
        dashboardMenuLayout.setVerticalGroup(
            dashboardMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardMenuLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalBalance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboardAccounts, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        Menus.add(dashboardMenu);

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

        createAccountButtonPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
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
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addContainerGap())
        );
        createAccountButtonPanelLayout.setVerticalGroup(
            createAccountButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createAccountButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addContainerGap())
        );

        accountsMenu.add(createAccountButtonPanel);
        createAccountButtonPanel.setBounds(310, 570, 400, 80);

        Menus.add(accountsMenu);

        transactionMenu.setBackground(new java.awt.Color(232, 232, 232));
        transactionMenu.setMaximumSize(new java.awt.Dimension(994, 695));
        transactionMenu.setMinimumSize(new java.awt.Dimension(994, 695));
        transactionMenu.setPreferredSize(new java.awt.Dimension(994, 695));
        transactionMenu.setLayout(null);

        jLabel20.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("TRANSACTIONS");
        transactionMenu.add(jLabel20);
        jLabel20.setBounds(6, 24, 907, 55);

        transactionHistoryButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        transactionHistoryButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        transactionHistoryButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transactionHistoryButtonMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Transactions");

        javax.swing.GroupLayout transactionHistoryButtonLayout = new javax.swing.GroupLayout(transactionHistoryButton);
        transactionHistoryButton.setLayout(transactionHistoryButtonLayout);
        transactionHistoryButtonLayout.setHorizontalGroup(
            transactionHistoryButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionHistoryButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addContainerGap())
        );
        transactionHistoryButtonLayout.setVerticalGroup(
            transactionHistoryButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionHistoryButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        transactionMenu.add(transactionHistoryButton);
        transactionHistoryButton.setBounds(120, 80, 150, 50);

        depositButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        depositButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        depositButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                depositButtonMouseClicked(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Deposit");

        javax.swing.GroupLayout depositButtonLayout = new javax.swing.GroupLayout(depositButton);
        depositButton.setLayout(depositButtonLayout);
        depositButtonLayout.setHorizontalGroup(
            depositButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depositButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addContainerGap())
        );
        depositButtonLayout.setVerticalGroup(
            depositButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depositButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        transactionMenu.add(depositButton);
        depositButton.setBounds(390, 80, 150, 50);

        withdrawButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        withdrawButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        withdrawButtn.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        withdrawButtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        withdrawButtn.setText("Withdraw");
        withdrawButtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                withdrawButtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout withdrawButtonLayout = new javax.swing.GroupLayout(withdrawButton);
        withdrawButton.setLayout(withdrawButtonLayout);
        withdrawButtonLayout.setHorizontalGroup(
            withdrawButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(withdrawButtn, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addContainerGap())
        );
        withdrawButtonLayout.setVerticalGroup(
            withdrawButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(withdrawButtn, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        transactionMenu.add(withdrawButton);
        withdrawButton.setBounds(660, 80, 150, 50);

        transactionsPanel.setBackground(new java.awt.Color(232, 232, 232));

        jScrollPane5.setBackground(new java.awt.Color(232, 232, 232));
        jScrollPane5.setBorder(null);

        SourceTransactionList.setBackground(new java.awt.Color(232, 232, 232));
        SourceTransactionList.setBorder(null);
        SourceTransactionList.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        SourceTransactionList.setToolTipText("");
        jScrollPane5.setViewportView(SourceTransactionList);

        jScrollPane4.setBackground(new java.awt.Color(232, 232, 232));
        jScrollPane4.setBorder(null);

        AmountTransactionList.setBackground(new java.awt.Color(232, 232, 232));
        AmountTransactionList.setBorder(null);
        AmountTransactionList.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        AmountTransactionList.setToolTipText("");
        jScrollPane4.setViewportView(AmountTransactionList);

        jScrollPane3.setBackground(new java.awt.Color(232, 232, 232));
        jScrollPane3.setBorder(null);

        DateTransactionList.setBackground(new java.awt.Color(232, 232, 232));
        DateTransactionList.setBorder(null);
        DateTransactionList.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        DateTransactionList.setToolTipText("");
        jScrollPane3.setViewportView(DateTransactionList);

        jScrollPane2.setBackground(new java.awt.Color(232, 232, 232));
        jScrollPane2.setBorder(null);

        ReceiventNameTransactionList.setBackground(new java.awt.Color(232, 232, 232));
        ReceiventNameTransactionList.setBorder(null);
        ReceiventNameTransactionList.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        ReceiventNameTransactionList.setToolTipText("");
        jScrollPane2.setViewportView(ReceiventNameTransactionList);

        jScrollPane1.setBackground(new java.awt.Color(232, 232, 232));
        jScrollPane1.setBorder(null);

        SenderNameTransactionList.setBackground(new java.awt.Color(232, 232, 232));
        SenderNameTransactionList.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        SenderNameTransactionList.setToolTipText("");
        jScrollPane1.setViewportView(SenderNameTransactionList);

        jLabel27.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel27.setText("Sender");

        jLabel28.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel28.setText("Receivent");

        jLabel29.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel29.setText("Date and Time");

        jLabel30.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel30.setText("Amount");

        jLabel31.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel31.setText("Payment Source");

        javax.swing.GroupLayout transactionsPanelLayout = new javax.swing.GroupLayout(transactionsPanel);
        transactionsPanel.setLayout(transactionsPanelLayout);
        transactionsPanelLayout.setHorizontalGroup(
            transactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionsPanelLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(transactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(transactionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(123, 123, 123)
                        .addComponent(jLabel28)
                        .addGap(72, 72, 72)
                        .addComponent(jLabel29)
                        .addGap(32, 32, 32))
                    .addGroup(transactionsPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addGroup(transactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(transactionsPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel30)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel31))
                    .addGroup(transactionsPanelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(143, Short.MAX_VALUE))
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
                .addGroup(transactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        transactionMenu.add(transactionsPanel);
        transactionsPanel.setBounds(0, 147, 1002, 548);

        depositPanel.setBackground(new java.awt.Color(232, 232, 232));

        depositAccComboBox.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        depositAccComboBox.setMaximumRowCount(4);

        jTextField1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel9.setText("Amount");

        depositAccButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        depositAccButton.setForeground(new java.awt.Color(60, 63, 65));
        depositAccButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        depositAccButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                depositAccButtonMouseClicked(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Deposit");

        javax.swing.GroupLayout depositAccButtonLayout = new javax.swing.GroupLayout(depositAccButton);
        depositAccButton.setLayout(depositAccButtonLayout);
        depositAccButtonLayout.setHorizontalGroup(
            depositAccButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depositAccButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        depositAccButtonLayout.setVerticalGroup(
            depositAccButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depositAccButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel33.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel33.setText("Select Account to Deposit");

        javax.swing.GroupLayout depositPanelLayout = new javax.swing.GroupLayout(depositPanel);
        depositPanel.setLayout(depositPanelLayout);
        depositPanelLayout.setHorizontalGroup(
            depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depositPanelLayout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addGroup(depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, depositPanelLayout.createSequentialGroup()
                        .addGroup(depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(depositAccButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1)
                            .addComponent(depositAccComboBox, 0, 246, Short.MAX_VALUE))
                        .addGap(397, 397, 397))
                    .addGroup(depositPanelLayout.createSequentialGroup()
                        .addGroup(depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        depositPanelLayout.setVerticalGroup(
            depositPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depositPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(depositAccComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(depositAccButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );

        transactionMenu.add(depositPanel);
        depositPanel.setBounds(0, 147, 1002, 548);

        withdrawPanel.setBackground(new java.awt.Color(232, 232, 232));

        withdrawAccComboBox.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        withdrawAccComboBox.setMaximumRowCount(4);

        jTextField3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel16.setText("Amount");

        withdrawAccButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        withdrawAccButton.setForeground(new java.awt.Color(60, 63, 65));
        withdrawAccButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        withdrawAccButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                withdrawAccButtonMouseClicked(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Withdraw");

        javax.swing.GroupLayout withdrawAccButtonLayout = new javax.swing.GroupLayout(withdrawAccButton);
        withdrawAccButton.setLayout(withdrawAccButtonLayout);
        withdrawAccButtonLayout.setHorizontalGroup(
            withdrawAccButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawAccButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addContainerGap())
        );
        withdrawAccButtonLayout.setVerticalGroup(
            withdrawAccButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawAccButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel35.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel35.setText("Select Account to Withdraw");

        javax.swing.GroupLayout withdrawPanelLayout = new javax.swing.GroupLayout(withdrawPanel);
        withdrawPanel.setLayout(withdrawPanelLayout);
        withdrawPanelLayout.setHorizontalGroup(
            withdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawPanelLayout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addGroup(withdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, withdrawPanelLayout.createSequentialGroup()
                        .addGroup(withdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(withdrawAccButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(withdrawAccComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(397, 397, 397))
                    .addGroup(withdrawPanelLayout.createSequentialGroup()
                        .addGroup(withdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        withdrawPanelLayout.setVerticalGroup(
            withdrawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(withdrawAccComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(withdrawAccButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );

        transactionMenu.add(withdrawPanel);
        withdrawPanel.setBounds(0, 147, 1002, 548);

        Menus.add(transactionMenu);

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

        sendAccount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
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
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
        );
        sendAccountLayout.setVerticalGroup(
            sendAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        );

        paymentsMenu.add(sendAccount);
        sendAccount.setBounds(116, 92, 210, 60);

        sendPerson.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
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
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
        );
        sendPersonLayout.setVerticalGroup(
            sendPersonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
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

        confirmOtherAccButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
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
                .addContainerGap(115, Short.MAX_VALUE))
        );

        paymentsMenu.add(sendOtherAccPanel);
        sendOtherAccPanel.setBounds(0, 170, 1000, 530);

        sendAccPanel.setBackground(new java.awt.Color(232, 232, 232));
        sendAccPanel.setMaximumSize(new java.awt.Dimension(994, 531));
        sendAccPanel.setLayout(null);

        confirmAccButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
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
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
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

        switchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/oopfinal/switch_icon.png"))); // NOI18N
        switchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        switchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                switchButtonMouseClicked(evt);
            }
        });
        sendAccPanel.add(switchButton);
        switchButton.setBounds(430, 140, 48, 48);

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

        setSize(new java.awt.Dimension(1216, 703));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMouseClicked
        // Display confirm dialog asking user if they want to log out
        int result = JOptionPane.showConfirmDialog(null, "Sure? You want to log out?", "Log Out?",
                JOptionPane.YES_NO_OPTION);

        // If user clicks "Yes," close current window and open new login window
        if (result == 0) {
            // Set lastPage to 1 and save userAccNo in savedUserAccNo
            lastPage = 1;
            LoginMenu.savedUserAccNo = userAccNo;

            // Close current window and open new login window
            this.dispose();
            new LoginMenu().setVisible(true);
        }
    }//GEN-LAST:event_logoutButtonMouseClicked

    //this function used to only show certain pages in main menu.
    private void accountsTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountsTabMouseClicked
        lastPage = 2;
        paymentsMenu.setVisible(false);
        dashboardMenu.setVisible(false);
        accountsMenu.setVisible(true);
        sendAccPanel.setVisible(true);
        transactionMenu.setVisible(false);
    }//GEN-LAST:event_accountsTabMouseClicked

    //this function used to only show certain pages in main menu.
    private void dashboardTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardTabMouseClicked
        lastPage = 1;
        paymentsMenu.setVisible(false);
        dashboardMenu.setVisible(true);
        accountsMenu.setVisible(false);
        transactionMenu.setVisible(false);
    }//GEN-LAST:event_dashboardTabMouseClicked

    private void createAccountButtonPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createAccountButtonPanelMouseClicked
        // Find all accounts belonging to the current user
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        // If the user has 4 accounts already, display error message
        if (userAccounts.size() == 4) {
            JOptionPane.showMessageDialog(null, "You can't create more than 4 accounts!", "Warning!", JOptionPane.ERROR_MESSAGE);
        }
        else {
            // Prompt user to enter a name for the new account
            String input = JOptionPane.showInputDialog("Please Enter Your Account Name.");

            // If user does not enter a name, display error message
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You have to enter a name to your account!");
            }
            else {
                // Create new account object and add it to the list of accounts
                Accounts newAccount = new Accounts(userAccNo, accNumberCounter, 0, input);
                accounts.add(newAccount);

                // Save the updated list of accounts to file and display success message
                saveAccountsFile();
                JOptionPane.showMessageDialog(null, "Your new account saved.");

                // Increment the account number counter and open the main bank menu
                accNumberCounter++;
                dispose();
                new MainBankMenu().setVisible(true);
            }
        }
    }//GEN-LAST:event_createAccountButtonPanelMouseClicked

    private void accNo5TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo5TrashMouseClicked
        // Find all accounts belonging to the current user
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        // If user has only one account, display error message
        if (userAccounts.size() <= 1) {
            JOptionPane.showMessageDialog(null, "You must have at least 1 account.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }
        else {
            // Display confirm dialog asking if the user wants to delete the account
            int result = JOptionPane.showConfirmDialog(null,
                    "You are about to delete the account. Your balance in this account will be transferred to your other account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);

            // If the user clicks "Yes," delete the account and update the list of accounts
            if (result == 0) {
                Accounts accountToRemove = userAccounts.get(0);
                double bal = accountToRemove.getBalanceOfAccount();
                userAccounts.get(1).setBalanceOfAccount(userAccounts.get(1).getBalanceOfAccount() + bal);
                accounts.remove(accountToRemove);

                saveAccountsFile();
                accNumberCounter--;
                dispose();
                new MainBankMenu().setVisible(true);

            }
        }

    }//GEN-LAST:event_accNo5TrashMouseClicked

    private void accNo6TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo6TrashMouseClicked
        // Find all accounts belonging to the current user
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        // Display confirm dialog asking if the user wants to delete the account
        int result = JOptionPane.showConfirmDialog(null,
                "You are about to delete the account. Your balance in this account will be transferred to your other account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);

        // If the user clicks "Yes," delete the account and update the list of accounts
        if (result == 0) {
            Accounts accountToRemove = userAccounts.get(1);
            double bal = accountToRemove.getBalanceOfAccount();
            userAccounts.get(0).setBalanceOfAccount(userAccounts.get(0).getBalanceOfAccount() + bal);
            accounts.remove(accountToRemove);

            saveAccountsFile();
            accNumberCounter--;
            dispose();
            new MainBankMenu().setVisible(true);

        }
    }//GEN-LAST:event_accNo6TrashMouseClicked

    private void accNo7TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo7TrashMouseClicked
        // Find all accounts belonging to the current user
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        // Display confirm dialog asking if the user wants to delete the account
        int result = JOptionPane.showConfirmDialog(null,
                "You are about to delete the account. Your balance in this account will be transferred to your other account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);

        // If the user clicks "Yes," delete the account and update the list of accounts
        if (result == 0) {
            Accounts accountToRemove = userAccounts.get(2);
            double bal = accountToRemove.getBalanceOfAccount();
            userAccounts.get(0).setBalanceOfAccount(userAccounts.get(0).getBalanceOfAccount() + bal);
            accounts.remove(accountToRemove);

            saveAccountsFile();
            accNumberCounter--;
            dispose();
            new MainBankMenu().setVisible(true);

        }
    }//GEN-LAST:event_accNo7TrashMouseClicked

    private void accNo8TrashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accNo8TrashMouseClicked
        // Find all accounts belonging to the current user
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        // Display confirm dialog asking if the user wants to delete the account
        int result = JOptionPane.showConfirmDialog(null,
                "You are about to delete the account. Your balance in this account will be transferred to your other account. Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);

        // If the user clicks "Yes," delete the account and update the list of accounts
        if (result == 0) {
            Accounts accountToRemove = userAccounts.get(3);
            double bal = accountToRemove.getBalanceOfAccount();
            userAccounts.get(0).setBalanceOfAccount(userAccounts.get(0).getBalanceOfAccount() + bal);
            accounts.remove(accountToRemove);

            saveAccountsFile();
            accNumberCounter--;
            dispose();
            new MainBankMenu().setVisible(true);

        }
    }//GEN-LAST:event_accNo8TrashMouseClicked

    //this function used to only show certain pages in main menu.
    private void paymentsTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paymentsTabMouseClicked
        lastPage = 3;
        paymentsMenu.setVisible(true);
        dashboardMenu.setVisible(false);
        accountsMenu.setVisible(false);
        transactionMenu.setVisible(false);
    }//GEN-LAST:event_paymentsTabMouseClicked

    private void switchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_switchButtonMouseClicked
        // Get the index of the selected item in each ComboBox
        int senderAccIndex = accFromComboBox.getSelectedIndex();
        int receverAccIndex = accToComboBox.getSelectedIndex();

        // Swap the selected item in each ComboBox
        accFromComboBox.setSelectedIndex(receverAccIndex);
        accToComboBox.setSelectedIndex(senderAccIndex);
    }//GEN-LAST:event_switchButtonMouseClicked

    private void confirmAccButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmAccButtonMouseClicked
        // Find all accounts belonging to the current user
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        // Get the index of the selected item in each ComboBox and the balance of the sender account
        int senderAccIndex = accFromComboBox.getSelectedIndex();
        int receiverAccIndex = accToComboBox.getSelectedIndex();
        double senderBalance = userAccounts.get(senderAccIndex).getBalanceOfAccount();
        double amountInput;

        // If the sender and receiver accounts are the same, display error message
        if (senderAccIndex == receiverAccIndex) {
            JOptionPane.showMessageDialog(null, "You cannot transfer money to the same account!", "Error!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Try to parse the input amount as a double
        try {
            amountInput = Double.parseDouble(sendAccAmount.getText().trim());
        }
        // If the text cannot be parsed as a double, display a warning message and return
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "your amount can only consist of numbers!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the sender does not have sufficient balance, display error message
        if (amountInput > senderBalance) {
            JOptionPane.showMessageDialog(null, "Insufficient balance!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the input amount is negative, display error message
        if (amountInput < 0) {
            JOptionPane.showMessageDialog(null, "amount cannot be less than zero!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the amount is more than 100.000, display a warning message and return
        if (amountInput > 100000) {
            JOptionPane.showMessageDialog(null, "You cannot deposit more than $100,000 at a time.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If all tests pass get sender and receivent accounts
        Accounts senderAccount = userAccounts.get(senderAccIndex);
        Accounts receiverAccount = userAccounts.get(receiverAccIndex);

        // transfer the money between the sender and receiver accounts
        senderAccount.setBalanceOfAccount(senderAccount.getBalanceOfAccount() - amountInput);
        receiverAccount.setBalanceOfAccount(receiverAccount.getBalanceOfAccount() + amountInput);

        //edit the accounts
        accounts.set(accounts.indexOf(userAccounts.get(senderAccIndex)), senderAccount);
        accounts.set(accounts.indexOf(userAccounts.get(receiverAccIndex)), receiverAccount);

        // Create a new transaction and add it to the list of transactions
        Transactions newTransaction = new Transactions(userAccNo, senderAccount.getAccountName(), receiverAccount.getAccountName(), systemDate, amountInput, senderAccount.getAccountName());
        transactions.add(newTransaction);

        // Save the updated list of transactions and accounts to file and display success message
        saveTransactionsFile();
        saveAccountsFile();
        JOptionPane.showMessageDialog(null, "The transfer was successful!");

        // Close the current window and open the main bank menu to reload the details
        dispose();
        new MainBankMenu().setVisible(true);


    }//GEN-LAST:event_confirmAccButtonMouseClicked

    //this function used to only show certain pages in main menu.
    private void sendPersonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendPersonMouseClicked
        sendOtherAccPanel.setVisible(true);
        sendAccPanel.setVisible(false);
    }//GEN-LAST:event_sendPersonMouseClicked

    //this function used to only show certain pages in main menu.
    private void sendAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendAccountMouseClicked
        sendOtherAccPanel.setVisible(false);
        sendAccPanel.setVisible(true);
    }//GEN-LAST:event_sendAccountMouseClicked

    private void confirmOtherAccButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmOtherAccButtonMouseClicked
        // Find all accounts belonging to the current user and the bank details of the recipient
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);
        String receiventAccNo = accNoInput.getText().trim();
        int senderAccIndex = accFromComboBox1.getSelectedIndex();
        Accounts senderAcc = userAccounts.get(senderAccIndex);
        int bankDetailIndex = findBankDetailsByAccNo(receiventAccNo);
        double amountInput;

        // Try to parse the input amount as a double
        try {
            amountInput = Double.parseDouble(jTextField2.getText().trim());
        }
        // If the text cannot be parsed as a double, display a warning message and return
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "your amount can only consist of numbers!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the recipient's account does not exist, display error message
        if (bankDetailIndex == -1) {
            JOptionPane.showMessageDialog(null, "The account you are trying to send does not exist!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //if the receivent account number is the same as user account number, display error message
        if (receiventAccNo.equals(userAccNo)) {
            JOptionPane.showMessageDialog(null, "You can't send to your own account!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If any of the input fields are empty, display error message
        if (jTextField2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the details!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the sender does not have sufficient balance, display error message
        if (senderAcc.getBalanceOfAccount() < amountInput) {
            JOptionPane.showMessageDialog(null, "insufficient balance!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the input amount is negative, display error message
        if (amountInput < 0) {
            JOptionPane.showMessageDialog(null, "amount cannot be less than zero!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the amount is more than 100.000, display a warning message and return
        if (amountInput > 100000) {
            JOptionPane.showMessageDialog(null, "You cannot deposit more than $100,000 at a time.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If all the checks pass
        // Get the bank details of the recipient and their accounts
        BankDetails receiventBankDetails = RegisterMenu.bankDetailsList.get(findBankDetailsByAccNo(receiventAccNo));
        ArrayList<Accounts> receiventsAccounts = findAccountsByAccNo(accounts, receiventAccNo);
        Accounts senderAccount = userAccounts.get(senderAccIndex);
        Accounts receiverAccount = receiventsAccounts.get(0);

        // Transfer the money between the sender and recipient accounts
        senderAccount.setBalanceOfAccount(senderAccount.getBalanceOfAccount() - amountInput);
        receiverAccount.setBalanceOfAccount(receiverAccount.getBalanceOfAccount() + amountInput);

        accounts.set(accounts.indexOf(senderAccount), senderAccount);
        accounts.set(accounts.indexOf(receiverAccount), receiverAccount);

        // Create new transactions for the sender and recipient and add them to the list of transactions
        Transactions senderTransaction = new Transactions(userAccNo, getBankDetails().getName(), receiventBankDetails.getName(), systemDate, amountInput, senderAccount.getAccountName());
        Transactions receiventTransaction = new Transactions(receiventAccNo, getBankDetails().getName(), receiventBankDetails.getName(), systemDate, amountInput, "---");

        transactions.add(senderTransaction);
        transactions.add(receiventTransaction);

        // Save the updated list of transactions and accounts to file and display success message
        saveTransactionsFile();
        saveAccountsFile();

        JOptionPane.showMessageDialog(null, "The transfer was successful!");
        // Close the current window and open the main bank menu
        dispose();
        new MainBankMenu().setVisible(true);

    }//GEN-LAST:event_confirmOtherAccButtonMouseClicked

    private void accNoInputİnputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_accNoInputİnputMethodTextChanged

    }//GEN-LAST:event_accNoInputİnputMethodTextChanged

    //this function used to only show certain pages in main menu.
    private void transactionsTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transactionsTabMouseClicked
        lastPage = 4;
        paymentsMenu.setVisible(false);
        dashboardMenu.setVisible(false);
        accountsMenu.setVisible(false);
        sendAccPanel.setVisible(false);
        transactionMenu.setVisible(true);
    }//GEN-LAST:event_transactionsTabMouseClicked

    //this function used to only show certain pages in main menu.
    private void depositButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_depositButtonMouseClicked
        transactionsPanel.setVisible(false);
        depositPanel.setVisible(true);
        withdrawPanel.setVisible(false);
    }//GEN-LAST:event_depositButtonMouseClicked

    //this function used to only show certain pages in main menu.
    private void transactionHistoryButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transactionHistoryButtonMouseClicked
        transactionsPanel.setVisible(true);
        depositPanel.setVisible(false);
        withdrawPanel.setVisible(false);
    }//GEN-LAST:event_transactionHistoryButtonMouseClicked

    private void depositAccButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_depositAccButtonMouseClicked
        // Find the accounts associated with the user's account number
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        // Parse the double value from the text field
        double amountInput;
        int selectedIndex = depositAccComboBox.getSelectedIndex();

        try {
            amountInput = Double.parseDouble(jTextField1.getText().trim());
        }
        // If the text cannot be parsed as a double, display a warning message and return
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "your amount can only consist of numbers!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the text field is empty, display a warning message and return
        if (jTextField1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the details!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the amount is less than 0, display a warning message and return
        if (amountInput < 0) {
            JOptionPane.showMessageDialog(null, "amount cannot be less than zero!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the amount is more than 100.000, display a warning message and return
        if (amountInput > 100000) {
            JOptionPane.showMessageDialog(null, "You cannot deposit more than $100,000 at a time.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If all the checks pass, proceed with the deposit
        // Retrieve the selected account
        Accounts depositedAcc = userAccounts.get(selectedIndex);

        // Increase the balance of the selected account by the amount input by the user
        depositedAcc.setBalanceOfAccount(depositedAcc.getBalanceOfAccount() + amountInput);

        // Update the accounts list with the updated account
        accounts.set(accounts.indexOf(depositedAcc), depositedAcc);

        // Create a new transaction object with details of the deposit
        Transactions newTransaction = new Transactions(userAccNo, getBankDetails().getName(), depositedAcc.getAccountName(), systemDate, amountInput, "Deposit");
        // Add the transaction to the transactions list
        transactions.add(newTransaction);

        // Save the updated transactions and accounts lists to file
        saveTransactionsFile();
        saveAccountsFile();

        // Display a success message
        JOptionPane.showMessageDialog(null, "The deposit was successful!");

        // Close the current window and open the main bank menu window
        dispose();
        new MainBankMenu().setVisible(true);


    }//GEN-LAST:event_depositAccButtonMouseClicked

    private void withdrawAccButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_withdrawAccButtonMouseClicked
        // Find the accounts associated with the user's account number
        ArrayList<Accounts> userAccounts = findAccountsByAccNo(accounts, userAccNo);

        // Parse the double value from the text field
        double amountInput;
        int selectedIndex = withdrawAccComboBox.getSelectedIndex();
        try {
            amountInput = Double.parseDouble(jTextField3.getText().trim());
        }
        // If the text cannot be parsed as a double, display a warning message and return
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "your amount can only consist of numbers!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the text field is empty, display a warning message and return
        if (jTextField3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the details!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the parsed double value is less than 0, display a warning message and return
        if (amountInput < 0) {
            JOptionPane.showMessageDialog(null, "amount cannot be less than zero!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If the amount is more than 100.000, display a warning message and return
        if (amountInput > 100000) {
            JOptionPane.showMessageDialog(null, "You cannot withdraw more than $100,000 at a time.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // check if the user has enough balance to withdraw
        if (userAccounts.get(selectedIndex).getBalanceOfAccount() < amountInput) {
            JOptionPane.showMessageDialog(null, "insufficent balance", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // If all the checks pass, proceed with the withdrawal
        // Retrieve the selected account
        Accounts withdrawAcc = userAccounts.get(selectedIndex);

        // Decrease the balance of the selected account by the amount input by the user
        withdrawAcc.setBalanceOfAccount(withdrawAcc.getBalanceOfAccount() - amountInput);

        // Update the accounts list with the updated account
        accounts.set(accounts.indexOf(withdrawAcc), withdrawAcc);

        // Create a new transaction object with details of the withdrawal
        Transactions newTransaction = new Transactions(userAccNo, withdrawAcc.getAccountName(), getBankDetails().getName(), systemDate, amountInput, "Withdraw");
        // Add the transaction to the transactions list
        transactions.add(newTransaction);

        // Save the updated transactions and accounts lists to file
        saveTransactionsFile();
        saveAccountsFile();

        // Display a success message
        JOptionPane.showMessageDialog(null, "The Withdraw was successful!");

        // Close the current window and open the main bank menu window
        dispose();
        new MainBankMenu().setVisible(true);

    }//GEN-LAST:event_withdrawAccButtonMouseClicked

    //this function used to only show certain pages in main menu.
    private void withdrawButtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_withdrawButtnMouseClicked
        transactionsPanel.setVisible(false);
        depositPanel.setVisible(false);
        withdrawPanel.setVisible(true);
    }//GEN-LAST:event_withdrawButtnMouseClicked

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
    private javax.swing.JList<String> AmountTransactionList;
    private javax.swing.JList<String> DateTransactionList;
    private javax.swing.JPanel Menus;
    private javax.swing.JList<String> ReceiventNameTransactionList;
    private javax.swing.JList<String> SenderNameTransactionList;
    private javax.swing.JList<String> SourceTransactionList;
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
    private javax.swing.JPanel depositAccButton;
    private javax.swing.JComboBox<String> depositAccComboBox;
    private javax.swing.JPanel depositButton;
    private javax.swing.JPanel depositPanel;
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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPanel logoutButton;
    private javax.swing.JPanel paymentsMenu;
    private javax.swing.JPanel paymentsTab;
    private javax.swing.JTextField sendAccAmount;
    private javax.swing.JPanel sendAccPanel;
    private javax.swing.JPanel sendAccount;
    private javax.swing.JPanel sendOtherAccPanel;
    private javax.swing.JPanel sendPerson;
    private javax.swing.JLabel switchButton;
    private javax.swing.JLabel totalBalance;
    private javax.swing.JPanel transactionHistoryButton;
    private javax.swing.JPanel transactionMenu;
    private javax.swing.JPanel transactionsPanel;
    private javax.swing.JPanel transactionsTab;
    private javax.swing.JLabel welcomeLabel;
    private javax.swing.JLabel welcomeLabel1;
    private javax.swing.JPanel withdrawAccButton;
    private javax.swing.JComboBox<String> withdrawAccComboBox;
    private javax.swing.JLabel withdrawButtn;
    private javax.swing.JPanel withdrawButton;
    private javax.swing.JPanel withdrawPanel;
    // End of variables declaration//GEN-END:variables
}
