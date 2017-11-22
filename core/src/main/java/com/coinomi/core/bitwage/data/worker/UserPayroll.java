package com.coinomi.core.bitwage.data.worker;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.bitwage.data.SimpleCompany;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gkoro on 12-Sep-17.
 */

public class UserPayroll extends BitwageBase {

    private BigInteger id;
    private BigInteger payroll_id;

    private SimpleCompany company;

    private String payment_type;
    private String created;
    private boolean received;
    private String datereceived;
    private boolean approved;
    private String dateapproved;
    private boolean broadcasted;
    private boolean fulfilled;
    private String datefulfilled;

    private String currency;
    private double amount_usd;
    private double amount_btc;
    private String transaction_id;

    private List<DistributionObject> distributionObjectList;

    public UserPayroll(JSONObject data) throws ShapeShiftException {
        super(data);
        distributionObjectList = new ArrayList<>();
        if (!isError) {
            try {
                id = new BigInteger(data.getString("id"));
                payroll_id = new BigInteger(data.getString("payroll_id"));
                company = new SimpleCompany(data.getString("company_name"),new BigInteger(data.getString("company_id")));
                payment_type = data.getString("payment_type");
                created = data.getString("created");
                received = data.getBoolean("received");
                datereceived = data.getString("datereceived");
                approved = data.getBoolean("approved");
                dateapproved = data.getString("dateapproved");
                broadcasted = data.getBoolean("broadcasted");
                fulfilled = data.getBoolean("fulfilled");
                datefulfilled = data.getString("datefulfilled");
                currency = data.getString("currency");
                amount_usd = data.getDouble("amount_usd");
                amount_btc = data.getDouble("amount_btc");
                transaction_id = data.getString("transaction_id");

                JSONArray distobjarray = data.getJSONArray("distobj_list");
                for (int i=0; i < distobjarray.length() ; i++) {
                    distributionObjectList.add(new DistributionObject(distobjarray.getJSONObject(i)));
                }
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            payment_type = null;
            created = null;
            received = false;
            datereceived = null;
            approved = false;
            dateapproved = null;
            broadcasted = false;
            fulfilled = false;
            datefulfilled = null;
            currency = null;
            amount_usd = 0;
            amount_btc = 0;
            transaction_id = null;
        }
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getPayroll_id() {
        return payroll_id;
    }

    public void setPayroll_id(BigInteger payroll_id) {
        this.payroll_id = payroll_id;
    }

    public SimpleCompany getCompany() {
        return company;
    }

    public void setCompany(SimpleCompany company) {
        this.company = company;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public String getDatereceived() {
        return datereceived;
    }

    public void setDatereceived(String datereceived) {
        this.datereceived = datereceived;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getDateapproved() {
        return dateapproved;
    }

    public void setDateapproved(String dateapproved) {
        this.dateapproved = dateapproved;
    }

    public boolean isBroadcasted() {
        return broadcasted;
    }

    public void setBroadcasted(boolean broadcasted) {
        this.broadcasted = broadcasted;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public String getDatefulfilled() {
        return datefulfilled;
    }

    public void setDatefulfilled(String datefulfilled) {
        this.datefulfilled = datefulfilled;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount_usd() {
        return amount_usd;
    }

    public void setAmount_usd(double amount_usd) {
        this.amount_usd = amount_usd;
    }

    public double getAmount_btc() {
        return amount_btc;
    }

    public void setAmount_btc(double amount_btc) {
        this.amount_btc = amount_btc;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public List<DistributionObject> getDistributionObjectList() {
        return distributionObjectList;
    }

    public void setDistributionObjectList(List<DistributionObject> distributionObjectList) {
        this.distributionObjectList = distributionObjectList;
    }
}


