package com.coinomi.core.bitwage.data.employer.profile;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

import java.math.BigInteger;
import java.net.URL;

import javax.annotation.Nullable;

/**
 * Created by gkoro on 15-Sep-17.
 */

public class Company extends BitwageBase {
    private String company_name;
    private BigInteger company_id;
    private String street_address;
    private String country;
    @Nullable
    private String city;
    private String state;
    private int zip;
    private URL website_url;
    private String email;
    private String phone;
    private String ein;
    private String default_payment_method;

   public Company(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                if (data.has("company_name"))
                    company_name = data.getString("company_name");
                else if (data.has("name"))
                    company_name = data.getString("name");
                if (data.has("company_id"))
                    company_id = new BigInteger(data.getString("company_id"));
                else if ( (data.has("id")))
                    company_id = new BigInteger(data.getString("id"));
                else throw new ShapeShiftException("Company Id not found");
                street_address = data.getString("street_address");
                city=data.optString("city");
                country = data.optString("country");
                state = data.getString("state");
                website_url = new URL(data.getString("website_url"));
                email = data.getString("email");
                if (data.has("phone"))
                    phone = data.getString("phone");
                else if (data.has("phone_number"))
                    phone = data.getString("phone_number");

                ein = data.getString("ein");
                zip = data.getInt("zip");
                default_payment_method=data.optString("default_payment_method");
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            company_name = null;
            company_id = null;
            street_address = null;
            city=null;
            country = null;
            state = null;
            website_url = null;
            email = null;
            phone = null;
            ein = null;
            zip = 0;
        }
    }

    @Nullable
    public String getCity() {
        return city;
    }

    public void setCity(@Nullable String city) {
        this.city = city;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public BigInteger getCompany_id() {
        return company_id;
    }

    public void setCompany_id(BigInteger company_id) {
        this.company_id = company_id;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public URL getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(URL website_url) {
        this.website_url = website_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEin() {
        return ein;
    }

    public void setEin(String ein) {
        this.ein = ein;
    }

    public String getDefault_payment_method() {
        return default_payment_method;
    }

    public void setDefault_payment_method(String default_payment_method) {
        this.default_payment_method = default_payment_method;
    }

    @Override
    public String toString() {
        return "Company{" +
                "company_name='" + company_name + '\'' +
                ", company_id=" + company_id +
                ", street_address='" + street_address + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip=" + zip +
                ", website_url=" + website_url +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", ein='" + ein + '\'' +
                ", default_payment_method='" + default_payment_method + '\'' +
                '}';
    }
}

