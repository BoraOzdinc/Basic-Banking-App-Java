/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopfinal;

/**
 *
 * @author borao
 */
public class Accounts {
    
    private String accNo;
    private int numberOfAccount;
    private double balanceOfAccount;
    private String accountName;

    public Accounts(String accNo, int numberOfAccount, double balanceOfAccount, String accountName) {
        this.accNo = accNo;
        this.numberOfAccount = numberOfAccount;
        this.balanceOfAccount = balanceOfAccount;
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public int getNumberOfAccount() {
        return numberOfAccount;
    }

    public void setNumberOfAccount(int numberOfAccount) {
        this.numberOfAccount = numberOfAccount;
    }

    public double getBalanceOfAccount() {
        return balanceOfAccount;
    }

    public void setBalanceOfAccount(double balanceOfAccount) {
        this.balanceOfAccount = balanceOfAccount;
    }
    
    
}
