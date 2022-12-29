package oopfinal;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author borao
 */
public class Transactions implements Serializable{
    
    private String senderAccNo;
    private String receiverAccNo;
    private Date transactionDate;
    private double amount;
    private String paymentSource;

    public Transactions(String senderAccNo, String receiverAccNo, Date transactionDate, double amount, String paymentSource) {
        this.senderAccNo = senderAccNo;
        this.receiverAccNo = receiverAccNo;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.paymentSource = paymentSource;
    }

    public String getSenderAccNo() {
        return senderAccNo;
    }

    public void setSenderAccNo(String senderAccNo) {
        this.senderAccNo = senderAccNo;
    }

    public String getReceiverAccNo() {
        return receiverAccNo;
    }

    public void setReceiverAccNo(String receiverAccNo) {
        this.receiverAccNo = receiverAccNo;
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
    
}
