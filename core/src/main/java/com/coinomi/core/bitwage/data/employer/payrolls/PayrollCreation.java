package com.coinomi.core.bitwage.data.employer.payrolls;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gkoro on 27-Nov-17.
 */

public class PayrollCreation extends BitwageBase{
    private BigInteger payroll_id;
    private String status;

    private List<SubOrder> suborder_list;

    private Double total_amount;
    private String currency;
    private Integer num_suborders;
    private String payment_method;
    private String payment_method_set_msg;

    public PayrollCreation(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                status=data.getString("status");
                payroll_id=new BigInteger(data.getString("payroll_id"));
                currency=data.getString("currency");
                total_amount=data.optDouble("total_amount");
                num_suborders=data.getInt("num_suborders");
                payment_method=data.getString("payment_method");
                payment_method_set_msg=data.getString("payment_method_set_msg");

                suborder_list = new ArrayList<>();
                JSONArray arrayList = data.getJSONArray("suborder_list");
                for (int i = 0; i < arrayList.length() ; i++) {
                    suborder_list.add(new SubOrder(arrayList.getJSONObject(i)));
                }

            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            status=null;
            payroll_id=null;
            currency=null;
            total_amount=null;
            num_suborders=null;
            payment_method= null;
            payment_method_set_msg=null;
        }
    }

    public BigInteger getPayroll_id() {
        return payroll_id;
    }

    public void setPayroll_id(BigInteger payroll_id) {
        this.payroll_id = payroll_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SubOrder> getSuborder_list() {
        return suborder_list;
    }

    public void setSuborder_list(List<SubOrder> suborder_list) {
        this.suborder_list = suborder_list;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getNum_suborders() {
        return num_suborders;
    }

    public void setNum_suborders(Integer num_suborders) {
        this.num_suborders = num_suborders;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_method_set_msg() {
        return payment_method_set_msg;
    }

    public void setPayment_method_set_msg(String payment_method_set_msg) {
        this.payment_method_set_msg = payment_method_set_msg;
    }

    @Override
    public String toString() {
        return "PayrollCreation{" +
                "payroll_id=" + payroll_id +
                ", status='" + status + '\'' +
                ", suborder_list=" + suborder_list +
                ", total_amount=" + total_amount +
                ", currency='" + currency + '\'' +
                ", num_suborders=" + num_suborders +
                ", payment_method='" + payment_method + '\'' +
                ", payment_method_set_msg='" + payment_method_set_msg + '\'' +
                '}';
    }

    class SubOrder extends BitwageBase {
        private String email;
        private Double amount;

        public SubOrder(JSONObject data) throws ShapeShiftException {
            super(data);
            if (!isError) {
                try {
                    email=data.getString("email");
                    amount=data.optDouble("amount");
                } catch (Exception e) {
                    throw new ShapeShiftException("Could not parse object", e);
                }
            } else {
                email=null;
                amount=null;
            }
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "SubOrder{" +
                    "email='" + email + '\'' +
                    ", amount=" + amount +
                    '}';
        }
    }
}

