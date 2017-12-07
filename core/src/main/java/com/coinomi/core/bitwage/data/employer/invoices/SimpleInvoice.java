package com.coinomi.core.bitwage.data.employer.invoices;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.bitwage.data.SimpleCompany;
import com.coinomi.core.bitwage.data.employer.workers.WorkerSimple;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

public class SimpleInvoice extends BitwageBase {

	private BigInteger id;
	private WorkerSimple worker;
	private Double total_amount_fiat;
	private String time_created;
	private String currency;
	private BigInteger payroll_id;
	private String due_date;
	private Boolean approved;

	public SimpleInvoice(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
            	id=new BigInteger(data.getString("id"));
            	worker=new WorkerSimple(data.getJSONObject("worker"));
            	total_amount_fiat=data.getDouble("total_amount_fiat");
            	time_created=data.getString("time_created");
            	currency=data.getString("currency");
            	payroll_id=new BigInteger(data.getString("payroll_id"));
            	due_date=data.getString("due_date");
            	approved=data.getBoolean("approved");
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
        	id=null;
        	worker=null;
        	total_amount_fiat=null;
        	time_created=null;
        	currency=null;
        	payroll_id=null;
        	due_date=null;
        	approved=null;
        } 
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public WorkerSimple getWorker() {
		return worker;
	}

	public void setWorker(WorkerSimple worker) {
		this.worker = worker;
	}

	public Double getTotal_amount_fiat() {
		return total_amount_fiat;
	}

	public void setTotal_amount_fiat(Double total_amount_fiat) {
		this.total_amount_fiat = total_amount_fiat;
	}

	public String getTime_created() {
		return time_created;
	}

	public void setTime_created(String time_created) {
		this.time_created = time_created;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigInteger getPayroll_id() {
		return payroll_id;
	}

	public void setPayroll_id(BigInteger payroll_id) {
		this.payroll_id = payroll_id;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "SimpleInvoice [id=" + id + ", worker=" + worker + ", total_amount_fiat=" + total_amount_fiat
				+ ", time_created=" + time_created + ", currency=" + currency + ", payroll_id=" + payroll_id
				+ ", due_date=" + due_date + ", approved=" + approved + "]";
	}
	
}