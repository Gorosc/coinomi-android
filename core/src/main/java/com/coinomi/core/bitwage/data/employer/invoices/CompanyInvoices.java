package com.coinomi.core.bitwage.data.employer.invoices;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.bitwage.data.SimpleCompany;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gkoro on 15-Sep-17.
 */

public class CompanyInvoices extends BitwageBase {
    
	private SimpleCompany company;
	private List<SimpleInvoice> invoices;

   public CompanyInvoices(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
            	company=new SimpleCompany(data.getJSONObject("company"));
            	invoices=new ArrayList<>();
            	JSONArray responsearray = data.getJSONArray("invoices");
            	for (int i = 0; i < responsearray.length(); i++) {
					 invoices.add(new SimpleInvoice(responsearray.getJSONObject(i)));					
				}
               
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
           company=null;
           invoices=null;
        }
    }

    
}

