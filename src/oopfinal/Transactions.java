package oopfinal;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author borao
 */
public class Transactions implements Serializable{
    
    private String senderName;
    private String receiverName;
    private Date transactionDate;
    private double amount;
    private String paymentSource;
    private String note;

    public Transactions(String senderName, String receiverName, Date transactionDate, double amount, String paymentSource, String note) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.paymentSource = paymentSource;
        this.note = note;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    
}
