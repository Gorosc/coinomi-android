package com.coinomi.core.bitwage.data.employer.payrolls;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.bitwage.data.Pagination;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.bitcoin.protocols.payments.Protos;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gkoro on 04-Nov-17.
 */

public class CompanyPayroll extends BitwageBase{

    private String time_created;
    private String date_received;
    private String date_processed;
    private String date_fulfilled;
    private Double total_amount;
    private String currency;
    private List<UserPayrollSimple> userpayrolls;
    private String payment_type;
    private CompanyPaymentDetails paymentDetails;
    private Pagination meta;

    public CompanyPayroll(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                time_created=data.getString("time_created");
                date_received=data.getString("date_received");
                date_processed=data.getString("date_processed");
                date_fulfilled=data.getString("date_fulfilled");
                total_amount=data.getDouble("total_amount");
                currency= data.getString("currency");

                userpayrolls = new ArrayList<>();
                JSONArray userpayrollsarray = data.getJSONArray("userpayrolls");
                for (int i = 0; i < userpayrollsarray.length() ; i++) {
                    userpayrolls.add(new UserPayrollSimple(userpayrollsarray.getJSONObject(i)));
                }

                payment_type=data.getString("payment_type");
                paymentDetails=new CompanyPaymentDetails(data.getJSONObject("paymentDetails"));
                meta=new Pagination(data.getJSONObject("meta"));

            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            time_created=null;
            date_received=null;
            date_processed=null;
            date_fulfilled=null;
            total_amount=null;
            currency=null;
            userpayrolls =null;
            payment_type=null;
            paymentDetails=null;
            meta=null;
        }
    }

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }

    public String getDate_received() {
        return date_received;
    }

    public void setDate_received(String date_received) {
        this.date_received = date_received;
    }

    public String getDate_processed() {
        return date_processed;
    }

    public void setDate_processed(String date_processed) {
        this.date_processed = date_processed;
    }

    public String getDate_fulfilled() {
        return date_fulfilled;
    }

    public void setDate_fulfilled(String date_fulfilled) {
        this.date_fulfilled = date_fulfilled;
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

    public List<UserPayrollSimple> getUserpayrolls() {
        return userpayrolls;
    }

    public void setUserpayrolls(List<UserPayrollSimple> userpayrolls) {
        this.userpayrolls = userpayrolls;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public CompanyPaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(CompanyPaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public Pagination getMeta() {
        return meta;
    }

    public void setMeta(Pagination meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "CompanyPayroll{" +
                "time_created='" + time_created + '\'' +
                ", date_received='" + date_received + '\'' +
                ", date_processed='" + date_processed + '\'' +
                ", date_fulfilled='" + date_fulfilled + '\'' +
                ", total_amount=" + total_amount +
                ", currency='" + currency + '\'' +
                ", userpayrolls=" + userpayrolls +
                ", payment_type='" + payment_type + '\'' +
                ", paymentDetails=" + paymentDetails +
                ", meta=" + meta +
                '}';
    }
}
