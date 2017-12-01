package com.coinomi.core.bitwage.data.employer.payrolls;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

import java.math.BigInteger;


/**
 * Created by gkoro on 04-Nov-17.	
 */

public class CompanyPaymentMethod extends BitwageBase{

    private String status;
    private BigInteger payroll_id;
    private String payment_method;
    private String payment_method_set_msg;

    public CompanyPaymentMethod(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
            	status=data.getString("status");
            	payroll_id=new BigInteger(data.getString("payroll_id"));
            	payment_method=data.getString("payment_method");
            	payment_method_set_msg=data.getString("payment_method_set_msg");

            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
        	status=null;
        	payroll_id=null;
        	payment_method=null;
        	payment_method_set_msg=null;
        }
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigInteger getPayroll_id() {
		return payroll_id;
	}

	public void setPayroll_id(BigInteger payroll_id) {
		this.payroll_id = payroll_id;
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
		return "CompanyPaymentMethod [status=" + status + ", payroll_id=" + payroll_id + ", payment_method="
				+ payment_method + ", payment_method_set_msg=" + payment_method_set_msg + "]";
	}

}
