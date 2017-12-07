package com.coinomi.core.bitwage.data.employer.payrolls;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

/**
 * Created by gkoro on 04-Nov-17.
 */

public class CompanyPaymentDetails extends BitwageBase{

    private String printOnCheck;
    private String address;
    private int checkNum;
    private String city;
    private String memo;
    private String phone;
    private String bankAccNum;
    private String bankRoute;
    private String state;
    private String email;
    private String first_name;
    private String last_name;
    private String zipcode;

    public CompanyPaymentDetails(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                printOnCheck=data.getString("printOnCheck");
                address=data.getString("address");
                checkNum=data.getInt("checkNum");
                city=data.getString("city");
                memo=data.getString("memo");
                phone=data.getString("phone");
                bankAccNum=data.getString("bankAccNum");
                bankRoute=data.getString("bankRoute");
                state=data.getString("state");
                email=data.getString("email");
                first_name=data.getString("first_name");
                last_name=data.getString("last_name");
                zipcode=data.getString("zipcode");

            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            printOnCheck=null;
            address=null;
            checkNum=0;
            city=null;
            memo=null;
            phone=null;
            bankAccNum=null;
            bankRoute=null;
            state=null;
            email=null;
            first_name=null;
            last_name=null;
            zipcode=null;
        }
    }

    public String getPrintOnCheck() {
        return printOnCheck;
    }

    public void setPrintOnCheck(String printOnCheck) {
        this.printOnCheck = printOnCheck;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankAccNum() {
        return bankAccNum;
    }

    public void setBankAccNum(String bankAccNum) {
        this.bankAccNum = bankAccNum;
    }

    public String getBankRoute() {
        return bankRoute;
    }

    public void setBankRoute(String bankRoute) {
        this.bankRoute = bankRoute;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "CompanyPaymentDetails{" +
                "printOnCheck='" + printOnCheck + '\'' +
                ", address='" + address + '\'' +
                ", checkNum=" + checkNum +
                ", city='" + city + '\'' +
                ", memo='" + memo + '\'' +
                ", phone='" + phone + '\'' +
                ", bankAccNum='" + bankAccNum + '\'' +
                ", bankRoute='" + bankRoute + '\'' +
                ", state='" + state + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
