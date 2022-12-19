/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopfinal;

import java.io.Serializable;

/**
 *
 * @author borao
 */
public class BankDetails implements Serializable{
    private static final long serialVersionUID = 6529685098267757690L;
    
    private String accNo;
    private String password;
    private double balance;
    private String name;
    private int age;

    public BankDetails(String accNo, String password, double balance, String name, int age) {
        this.accNo = accNo;
        this.password = password;
        this.balance = balance;
        this.name = name;
        this.age = age;
    }

    
    @Override
    public String toString() {
        RegisterMenu r = new RegisterMenu();

        String results = "";

        for(BankDetails d : r.bankDetailsList) {

            results += d.toString(); 
    }
    return results;
  }
    

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    
    
}
