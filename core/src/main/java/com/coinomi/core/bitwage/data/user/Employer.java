package com.coinomi.core.bitwage.data.user;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

import java.math.BigInteger;
import java.net.URL;

/**
 * Created by gkoro on 09-Sep-17.
 */

public class Employer extends BitwageBase {

    private JSONObject json;
    private String ppname;
    private String created;
    private URL ppwebsite;
    private String employer;
    private URL employerwebsite;
    private String employercurrency;
    private String jobrole;
    private BigInteger bpionboardid;
    private Integer order;

    public Employer(JSONObject data) throws ShapeShiftException {
        super(data);
        this.json = data;
        if (!isError) {
            try {
                ppname = data.getString("ppname");
                created = data.getString("created");
                ppwebsite = new URL(data.getString("ppwebsite"));
                employer = data.getString("employer");
                employerwebsite = new URL(data.getString("employerwebsite"));
                employercurrency = data.getString("employercurrency");
                jobrole = data.getString("jobrole");
                bpionboardid = new BigInteger(data.getString("bpionboardid"));
                order = Integer.valueOf(data.getString("order"));
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            ppname = null;
            created = null;
            ppwebsite = null;
            employer = null;
            employerwebsite = null;
            employercurrency = null;
            jobrole = null;
            bpionboardid = null;
            order = null;
        }
    }

    public String getPpname() {
        return ppname;
    }

    public void setPpname(String ppname) {
        this.ppname = ppname;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public URL getPpwebsite() {
        return ppwebsite;
    }

    public void setPpwebsite(URL ppwebsite) {
        this.ppwebsite = ppwebsite;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public URL getEmployerwebsite() {
        return employerwebsite;
    }

    public void setEmployerwebsite(URL employerwebsite) {
        this.employerwebsite = employerwebsite;
    }

    public String getEmployercurrency() {
        return employercurrency;
    }

    public void setEmployercurrency(String employercurrency) {
        this.employercurrency = employercurrency;
    }

    public String getJobrole() {
        return jobrole;
    }

    public void setJobrole(String jobrole) {
        this.jobrole = jobrole;
    }

    public BigInteger getBpionboardid() {
        return bpionboardid;
    }

    public void setBpionboardid(BigInteger bpionboardid) {
        this.bpionboardid = bpionboardid;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }
}
