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

    private String senderAccNo;
    private String receiverAccNo;
    private String transactionDate;
    private double amount;
    private String paymentSource;

    public Transactions(String senderAccNo, String receiverAccNo, String transactionDate, double amount, String paymentSource) {
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
