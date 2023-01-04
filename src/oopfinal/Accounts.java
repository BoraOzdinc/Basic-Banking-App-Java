package oopfinal;

import java.io.Serializable;

/**
 *
 * @author borao
 */
public class Accounts implements Serializable {

    private static final long serialVersionUID = -695257841392802020L;

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
