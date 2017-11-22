package com.coinomi.core.bitwage.data.employer.payrolls;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

import java.math.BigInteger;

/**
 * Created by gkoro on 15-Oct-17.
 */

public class UserPayrollSimple extends BitwageBase {

    private String user_email;
    private BigInteger user_id;
    private BigInteger userpayroll_id;

    //Used on single Company Payroll
    private Double amount;

    public UserPayrollSimple(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                user_email=data.getString("user_email");
                user_id=new BigInteger(data.getString("user_id"));
                userpayroll_id=new BigInteger(data.getString("userpayroll_id"));
                amount=data.optDouble("amount");
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            user_email=null;
            user_id=null;
            userpayroll_id=null;
            amount=null;
        }
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public BigInteger getUser_id() {
        return user_id;
    }

    public void setUser_id(BigInteger user_id) {
        this.user_id = user_id;
    }

    public BigInteger getUserpayroll_id() {
        return userpayroll_id;
    }

    public void setUserpayroll_id(BigInteger userpayroll_id) {
        this.userpayroll_id = userpayroll_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}