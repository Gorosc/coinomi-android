package com.coinomi.core.bitwage.data.user;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

/**
 * Created by gkoro on 09-Sep-17.
 */

public class Profile extends BitwageBase {

     private String userid;
     private String firstname;
     private String lastname;
     private String dateofbirth;
     private String phonenumber;
     private String streetaddress;
     private String city;
     private String state;
     private String zip;

    public Profile(JSONObject data) throws ShapeShiftException {
        super(data);
            if (!isError) {
            try {
                userid = data.getString("user_id");
                firstname = data.getString("first_name");
                lastname = data.getString("last_name");
                dateofbirth = data.getString("date_of_birth");
                phonenumber = data.getString("phone_number");
                streetaddress = data.getString("street_address");
                city = data.getString("city");
                state = data.getString("state");
                zip = data.getString("zip");
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            userid = null;
            firstname = null;
            lastname = null;
            dateofbirth = null;
            phonenumber = null;
            streetaddress = null;
            city = null;
            state = null;
            zip = null;
        }
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getStreetaddress() {
        return streetaddress;
    }

    public void setStreetaddress(String streetaddress) {
        this.streetaddress = streetaddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
