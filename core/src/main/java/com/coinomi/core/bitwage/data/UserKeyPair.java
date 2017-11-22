package com.coinomi.core.bitwage.data;

import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;


/**
 * Created by gkoro on 04-Sep-17.
 */

public class UserKeyPair extends BitwageBase{

    private String username;
    private String apikey;
    private String apisecret;

    public UserKeyPair(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                username = data.getString("username");
                apikey = data.getString("apikey");
                apisecret = data.getString("apisecret");
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            username = null;
            apikey = null;
            apisecret = null;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }
}
