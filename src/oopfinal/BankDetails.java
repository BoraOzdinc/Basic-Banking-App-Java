package oopfinal;

import java.io.Serializable;

/**
 *
 * @author borao
 */
public class BankDetails implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private String accNo;
    private String password;

    private String name;
    private int age;

    public BankDetails(String accNo, String password, String name, int age) {
        this.accNo = accNo;
        this.password = password;

        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        RegisterMenu r = new RegisterMenu();

        String results = "";

        for (BankDetails d : r.bankDetailsList) {

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
