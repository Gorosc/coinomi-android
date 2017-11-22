package com.coinomi.core.bitwage.data.user;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.bitwage.data.SimpleCompany;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gkoro on 09-Sep-17.
 */

public class Companies extends BitwageBase {

    private List<SimpleCompany> companies;
    private Integer defaultcompanyid;

    public Companies(JSONObject data) throws ShapeShiftException {
        super(data);

        companies = new ArrayList<>();

        if (!isError) try {

            defaultcompanyid = data.getInt("default_company");
            JSONArray companiesarray = data.getJSONArray("companies");

            for (int i=0; i < companiesarray.length() ; i++) {
                companies.add(new SimpleCompany(companiesarray.getJSONObject(i)));
            }

        } catch (Exception e) {

            throw new ShapeShiftException("Could not parse object", e);
        }
        else {
            defaultcompanyid = null;
        }
    }

    public List<SimpleCompany> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<SimpleCompany> companies) {
        this.companies = companies;
    }

    public Integer getDefaultcompanyid() {
        return defaultcompanyid;
    }

    public void setDefaultcompanyid(Integer defaultcompanyid) {
        this.defaultcompanyid = defaultcompanyid;
    }
}
