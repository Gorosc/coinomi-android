package com.coinomi.core.bitwage.data.employer.profile;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

/**
 * Created by gkoro on 14-Oct-17.
 */

public class LinkedAccount extends BitwageBase {

    private String type;
    private String brand;
    private String last4;
    private String id;

    public LinkedAccount(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                type = data.getString("type");
                brand = data.getString("brand");
                last4 = data.getString("last4");
                id = data.getString("id");
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            type = null;
            brand = null;
            last4 = null;
            id = null;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
