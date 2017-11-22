package com.coinomi.core.bitwage.data;

import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;
import org.spongycastle.util.BigIntegers;

import java.math.BigInteger;

/**
 * Created by gkoro on 09-Sep-17.
 */

public class SimpleCompany extends BitwageBase{

    private BigInteger companyid;
    private String companyname;

    public SimpleCompany(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                companyid = new BigInteger(data.getString("company_id"));
                companyname = data.getString("company_name");
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            companyid = null;
            companyname = null;
        }
    }

    public SimpleCompany(String companyname, BigInteger companyid) throws ShapeShiftException {
        this.companyname = companyname;
        this.companyid = companyid;
    }



    public BigInteger getCompanyid() {
        return companyid;
    }

    public void setCompanyid(BigInteger companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }
}
