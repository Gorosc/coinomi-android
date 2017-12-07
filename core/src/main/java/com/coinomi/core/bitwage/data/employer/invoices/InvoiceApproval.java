package com.coinomi.core.bitwage.data.employer.invoices;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.bitwage.data.employer.payrolls.PayrollCreation;
import com.coinomi.core.bitwage.data.employer.workers.WorkerSimple;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

import java.math.BigInteger;

public class InvoiceApproval extends BitwageBase {

	private InnerInvoice invoice;
	private PayrollCreation payroll;

	public InvoiceApproval(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
 				invoice=new InnerInvoice(data.getJSONObject("invoice"));
				payroll=new PayrollCreation(data.getJSONObject("payroll"));
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
			invoice=null;
			payroll=null;
        } 
	}

	public InnerInvoice getInvoice() {
		return invoice;
	}

	public void setInvoice(InnerInvoice invoice) {
		this.invoice = invoice;
	}

	public PayrollCreation getPayroll() {
		return payroll;
	}

	public void setPayroll(PayrollCreation payroll) {
		this.payroll = payroll;
	}

	private class InnerInvoice extends BitwageBase {

		 private BigInteger id;
         private String status;
         private String currency;
         private Double total_amount_fiat;

		public InnerInvoice(JSONObject data) throws ShapeShiftException {
			super(data);
			if (!isError) {
				try {
					id=new BigInteger(data.getString("id"));
                    status=data.getString("status");
                    currency= data.getString("currency");
                    total_amount_fiat=data.getDouble("total_amount_fiat");
                } catch (Exception e) {
					throw new ShapeShiftException("Could not parse object", e);
				}
			} else {
                id=null;
                status=null;
                currency=null;
                total_amount_fiat=null;
			}
		}

        @Override
        public String toString() {
            return "InnerInvoice{" +
                    "id=" + id +
                    ", status='" + status + '\'' +
                    ", currency='" + currency + '\'' +
                    ", total_amount_fiat=" + total_amount_fiat +
                    '}';
        }

        public BigInteger getId() {
            return id;
        }

        public void setId(BigInteger id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Double getTotal_amount_fiat() {
            return total_amount_fiat;
        }

        public void setTotal_amount_fiat(Double total_amount_fiat) {
            this.total_amount_fiat = total_amount_fiat;
        }
    }
}