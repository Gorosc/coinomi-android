package com.coinomi.core.bitwage.data.employer.payrolls;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gkoro on 15-Oct-17.
 */

public class CompanyPayrollSimple extends BitwageBase {

    private Double amount;
    private String currency;
    private BigInteger id;
    private String time_created;

    List<UserPayrollSimple> userpayrolls;

    public CompanyPayrollSimple(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                amount = data.getDouble("amount");
                currency = data.getString("currency");
                id = new BigInteger(data.getString("id"));
                time_created = data.getString("time_created");

                userpayrolls = new ArrayList<>();
                JSONArray userpayrollsarray = data.getJSONArray("userpayrolls");
                for (int i = 0; i < userpayrollsarray.length() ; i++) {
                    userpayrolls.add(new UserPayrollSimple(userpayrollsarray.getJSONObject(i)));
                }

            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            amount = null;
            currency = null;
            id = null;
            time_created = null;
        }
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }

    public List<UserPayrollSimple> getUserpayrolls() {
        return userpayrolls;
    }

    public void setUserpayrolls(List<UserPayrollSimple> userpayrolls) {
        this.userpayrolls = userpayrolls;
    }
}

