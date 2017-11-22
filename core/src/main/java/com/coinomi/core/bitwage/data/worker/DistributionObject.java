package com.coinomi.core.bitwage.data.worker;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

/**
 * Created by gkoro on 12-Sep-17.
 */

public class DistributionObject extends BitwageBase {

    private int percentage;
    private String usercompany_wallet;
    private String payment_outlet;
    private String paymentoutlet_orderid;
    private boolean distributionobjects;
    private boolean userpayrolldistributionobjects;
    private double amount_usd;
    private double amount_btc;
    private String country;
    private String currency;

    public DistributionObject(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                percentage = data.getInt("percentage");
                usercompany_wallet = data.getString("usercompany_wallet");
                payment_outlet = data.getString("payment_outlet");
                paymentoutlet_orderid = data.getString("paymentoutlet_orderid");
                distributionobjects = data.getBoolean("distributionobjects");
                userpayrolldistributionobjects = data.getBoolean("userpayrolldistributionobjects");
                amount_usd = data.getDouble("amount_usd");
                amount_btc = data.getDouble("amount_btc");
                country = data.getString("country");
                currency = data.getString("currency");
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            percentage = 0;
            usercompany_wallet = null;
            payment_outlet = null;
            paymentoutlet_orderid = null;
            distributionobjects = false;
            userpayrolldistributionobjects = false;
            amount_usd = 0;
            amount_btc = 0;
            country = null;
            currency = null;
        }
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getUsercompany_wallet() {
        return usercompany_wallet;
    }

    public void setUsercompany_wallet(String usercompany_wallet) {
        this.usercompany_wallet = usercompany_wallet;
    }

    public String getPayment_outlet() {
        return payment_outlet;
    }

    public void setPayment_outlet(String payment_outlet) {
        this.payment_outlet = payment_outlet;
    }

    public String getPaymentoutlet_orderid() {
        return paymentoutlet_orderid;
    }

    public void setPaymentoutlet_orderid(String paymentoutlet_orderid) {
        this.paymentoutlet_orderid = paymentoutlet_orderid;
    }

    public boolean isDistributionobjects() {
        return distributionobjects;
    }

    public void setDistributionobjects(boolean distributionobjects) {
        this.distributionobjects = distributionobjects;
    }

    public boolean isUserpayrolldistributionobjects() {
        return userpayrolldistributionobjects;
    }

    public void setUserpayrolldistributionobjects(boolean userpayrolldistributionobjects) {
        this.userpayrolldistributionobjects = userpayrolldistributionobjects;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
