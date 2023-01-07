package oopfinal;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author borao
 */
public class Transactions implements Serializable {

    private static final long serialVersionUID = -6053234864767433320L;
    
    private String userAccNo;
    private String senderAcc;
    private String receiverAcc;
    private String transactionDate;
    private double amount;
    private String paymentSource;

    public Transactions(String userAccNo, String senderAcc, String receiverAcc, String transactionDate, double amount, String paymentSource) {
        this.userAccNo = userAccNo;
        this.senderAcc = senderAcc;
        this.receiverAcc = receiverAcc;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.paymentSource = paymentSource;
    }

    public String getUserAccNo() {
        return userAccNo;
    }

    public void setUserAccNo(String userAccNo) {
        this.userAccNo = userAccNo;
    }

    public String getSenderAcc() {
        return senderAcc;
    }

    public void setSenderAcc(String senderAcc) {
        this.senderAcc = senderAcc;
    }

    public String getReceiverAcc() {
        return receiverAcc;
    }

    public void setReceiverAcc(String receiverAcc) {
        this.receiverAcc = receiverAcc;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentSource() {
        return paymentSource;
    }

    public void setPaymentSource(String paymentSource) {
        this.paymentSource = paymentSource;
    }

    

}
