package com.coinomi.core.bitwage.data.employer.invoices;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.bitwage.data.employer.profile.Company;
import com.coinomi.core.bitwage.data.employer.workers.WorkerSimple;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Invoice extends BitwageBase {

	private BigInteger id;
	private Company company;
    private WorkerSimple worker;

    private List<LineItem> line_items;

    private String time_created;
	private String currency;
	private String due_date;
	private Boolean approved;

	public Invoice(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
            	id=new BigInteger(data.getString("id"));
                company=new Company(data.getJSONObject("company"));
            	worker=new WorkerSimple(data.getJSONObject("worker"));
            	time_created=data.getString("time_created");
            	currency=data.getString("currency");
            	due_date=data.getString("due date");
            	approved=data.getBoolean("approved");

                line_items = new ArrayList<>();
                JSONArray responsearray = data.getJSONArray("line_items");
                for (int i = 0; i < responsearray.length(); i++) {
                    line_items.add(new LineItem(responsearray.getJSONObject(i)));
                }
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            id=null;
            company=null;
            worker=null;
            time_created=null;
            currency=null;
            due_date=null;
            approved=null;
            line_items=null;
        } 
	}

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public WorkerSimple getWorker() {
        return worker;
    }

    public void setWorker(WorkerSimple worker) {
        this.worker = worker;
    }

    public List<LineItem> getLine_items() {
        return line_items;
    }

    public void setLine_items(List<LineItem> line_items) {
        this.line_items = line_items;
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
        return "Invoice{" +
                "id=" + id +
                ", company=" + company +
                ", worker=" + worker +
                ", line_items=" + line_items +
                ", time_created='" + time_created + '\'' +
                ", currency='" + currency + '\'' +
                ", due_date='" + due_date + '\'' +
                ", approved=" + approved +
                '}';
    }
}