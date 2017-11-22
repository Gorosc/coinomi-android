package com.coinomi.core.bitwage.data.worker;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by gkoro on 11-Sep-17.
 */

public class UserPayrollsInfo extends BitwageBase {

    private int payroll_fulfilled;
    private int payroll_not_fulfilled;
    private double total_fulfilled;
    private double total_not_fulfilled;
    private Map<String, Double> total_by_currency_fulfilled;
    private Map<String, Double> total_by_currency_not_fulfilled;
    private String bpicompanyname;
    private String bpicompanyid;

    private List<UserPayroll> userpayrolls;

    public UserPayrollsInfo(JSONObject data) throws ShapeShiftException {
        super(data);
        total_by_currency_fulfilled = new HashMap<>();
        total_by_currency_not_fulfilled = new HashMap<>();
        userpayrolls = new ArrayList<>();

        if (!isError) {
            try {
                payroll_fulfilled = data.getInt("payroll_fulfilled");
                payroll_not_fulfilled = data.getInt("payroll_not_fulfilled");
                total_fulfilled = data.getDouble("total_fulfilled");
                total_not_fulfilled = data.getDouble("total_not_fulfilled");
                bpicompanyname = data.getString("bpicompanyname");
                bpicompanyid = data.getString("bpicompanyid");

                JSONObject total_by_currency = data.getJSONObject("total_by_currency");
                JSONObject fulfilled = total_by_currency.getJSONObject("fulfilled");
                JSONObject not_fulfilled = total_by_currency.getJSONObject("not_fulfilled");

                Iterator<String> itr;
                itr = fulfilled.keys();
                while(itr.hasNext()) {
                    String name = itr.next();
                        try {
                            total_by_currency_fulfilled.put(name, Double.parseDouble(fulfilled.getString(name)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }

                itr = not_fulfilled.keys();
                while(itr.hasNext()) {
                    String name = itr.next();
                    try {
                        total_by_currency_not_fulfilled.put(name, Double.parseDouble(not_fulfilled.getString(name)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                JSONArray userpayrollsarray = data.getJSONArray("userpayrolls");
                for (int i=0; i < userpayrollsarray.length() ; i++) {
                    userpayrolls.add(new UserPayroll(userpayrollsarray.getJSONObject(i)));
                }

            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            payroll_fulfilled = 0;
            payroll_not_fulfilled = 0;
            total_fulfilled = 0;
            total_not_fulfilled = 0;
            total_by_currency_not_fulfilled = null;
            total_by_currency_fulfilled = null;
            bpicompanyname = null;
            bpicompanyid = null;
            userpayrolls = null;
        }
    }

    public int getPayroll_fulfilled() {
        return payroll_fulfilled;
    }

    public void setPayroll_fulfilled(int payroll_fulfilled) {
        this.payroll_fulfilled = payroll_fulfilled;
    }

    public int getPayroll_not_fulfilled() {
        return payroll_not_fulfilled;
    }

    public void setPayroll_not_fulfilled(int payroll_not_fulfilled) {
        this.payroll_not_fulfilled = payroll_not_fulfilled;
    }

    public double getTotal_fulfilled() {
        return total_fulfilled;
    }

    public void setTotal_fulfilled(double total_fulfilled) {
        this.total_fulfilled = total_fulfilled;
    }

    public double getTotal_not_fulfilled() {
        return total_not_fulfilled;
    }

    public void setTotal_not_fulfilled(double total_not_fulfilled) {
        this.total_not_fulfilled = total_not_fulfilled;
    }

    public Map<String, Double> getTotal_by_currency_fulfilled() {
        return total_by_currency_fulfilled;
    }

    public void setTotal_by_currency_fulfilled(Map<String, Double> total_by_currency_fulfilled) {
        this.total_by_currency_fulfilled = total_by_currency_fulfilled;
    }

    public Map<String, Double> getTotal_by_currency_not_fulfilled() {
        return total_by_currency_not_fulfilled;
    }

    public void setTotal_by_currency_not_fulfilled(Map<String, Double> total_by_currency_not_fulfilled) {
        this.total_by_currency_not_fulfilled = total_by_currency_not_fulfilled;
    }

    public String getBpicompanyname() {
        return bpicompanyname;
    }

    public void setBpicompanyname(String bpicompanyname) {
        this.bpicompanyname = bpicompanyname;
    }

    public String getBpicompanyid() {
        return bpicompanyid;
    }

    public void setBpicompanyid(String bpicompanyid) {
        this.bpicompanyid = bpicompanyid;
    }

    public List<UserPayroll> getUserpayrolls() {
        return userpayrolls;
    }

    public void setUserpayrolls(List<UserPayroll> userpayrolls) {
        this.userpayrolls = userpayrolls;
    }
}
