package com.coinomi.core.bitwage.data.employer.invoices;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.bitwage.data.employer.workers.WorkerSimple;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

import java.math.BigInteger;

public class LineItem extends BitwageBase {

	private Double time;
	private Double amount_fiat;
	private Double amountpertime;
	private String currency;
	private String description;

	public LineItem(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
 				time=data.getDouble("time");
				amount_fiat=data.getDouble("amount_fiat");
				amountpertime=data.getDouble("amountpertime");
            	currency=data.getString("currency");
				description=data.getString("description");

            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
			time=null;
			amount_fiat=null;
			amountpertime=null;
			currency=null;
			description=null;
        } 
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Double getAmount_fiat() {
		return amount_fiat;
	}

	public void setAmount_fiat(Double amount_fiat) {
		this.amount_fiat = amount_fiat;
	}

	public Double getAmountpertime() {
		return amountpertime;
	}

	public void setAmountpertime(Double amountpertime) {
		this.amountpertime = amountpertime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "LineItem{" +
				"time=" + time +
				", amount_fiat=" + amount_fiat +
				", amountpertime=" + amountpertime +
				", currency='" + currency + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}