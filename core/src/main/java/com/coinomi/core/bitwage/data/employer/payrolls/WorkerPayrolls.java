package com.coinomi.core.bitwage.data.employer.payrolls;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.bitwage.data.Pagination;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by gkoro on 26-Nov-17.
 */

public class WorkerPayrolls extends BitwageBase{
    private String company_id;
    private String company_name;
    private int payroll_fulfilled;
    private int payroll_not_fulfilled;
    private double total_fulfilled;
    private double total_not_fulfilled;
    private Map<String, Double> total_by_currency_fulfilled;
    private Map<String, Double> total_by_currency_not_fulfilled;

    private List<CompanyPayrollSimple> payrolls;

    private Pagination meta;

    public WorkerPayrolls(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                total_by_currency_fulfilled = new HashMap<>();
                total_by_currency_not_fulfilled = new HashMap<>();
                company_id = data.getString("company_id");
                company_name = data.getString("company_name");
                payroll_fulfilled = data.getInt("payroll_fulfilled");
                payroll_not_fulfilled = data.getInt("payroll_not_fulfilled");
                total_fulfilled = data.getDouble("total_fulfilled");
                total_not_fulfilled = data.getDouble("total_not_fulfilled");

                JSONObject total_by_currency = data.getJSONObject("total_by_currency");
                JSONObject fulfilled = total_by_currency.getJSONObject("fulfilled");
                JSONObject not_fulfilled = total_by_currency.getJSONObject("not_fulfilled");

                Iterator<String> itr = fulfilled.keys();
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

                meta = new Pagination(data.getJSONObject("meta"));

                payrolls = new ArrayList<>();
                JSONArray payrollsarray = data.getJSONArray("payrolls");
                for (int i = 0; i < payrollsarray.length(); i++) {
                    payrolls.add(new CompanyPayrollSimple(payrollsarray.getJSONObject(i)));
                }


            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            company_id=null;
            company_name=null;
            payroll_fulfilled=0;
            payroll_not_fulfilled=0;
            total_fulfilled=0;
            total_not_fulfilled=0;
            total_by_currency_fulfilled=null;
            total_by_currency_not_fulfilled=null;
            payrolls=null;
            meta=null;
        }
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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

    public List<CompanyPayrollSimple> getPayrolls() {
        return payrolls;
    }

    public void setPayrolls(List<CompanyPayrollSimple> payrolls) {
        this.payrolls = payrolls;
    }

    public Pagination getMeta() {
        return meta;
    }

    public void setMeta(Pagination meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "WorkerPayrolls{" +
                "company_id='" + company_id + '\'' +
                ", company_name='" + company_name + '\'' +
                ", payroll_fulfilled=" + payroll_fulfilled +
                ", payroll_not_fulfilled=" + payroll_not_fulfilled +
                ", total_fulfilled=" + total_fulfilled +
                ", total_not_fulfilled=" + total_not_fulfilled +
                ", total_by_currency_fulfilled=" + total_by_currency_fulfilled +
                ", total_by_currency_not_fulfilled=" + total_by_currency_not_fulfilled +
                ", payrolls=" + payrolls +
                ", meta=" + meta +
                '}';
    }
}
