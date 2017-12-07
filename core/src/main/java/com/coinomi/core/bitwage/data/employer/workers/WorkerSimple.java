package com.coinomi.core.bitwage.data.employer.workers;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

import java.math.BigInteger;

/**
 * Created by gkoro on 30-Sep-17.
 */

public class WorkerSimple extends BitwageBase {

    private String email;
    private BigInteger user_id;
    private String role;

    public WorkerSimple(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                if (data.has("user_id"))
                    user_id = new BigInteger(data.getString("user_id"));
                else if (data.has("id"))
                    user_id = new BigInteger(data.getString("id"));
                else throw new ShapeShiftException("Worker has no Id");
                email = data.getString("email");
                role = data.optString("role");
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            user_id = null;
            email = null;
            role = null;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getUser_id() {
        return user_id;
    }

    public void setUser_id(BigInteger user_id) {
        this.user_id = user_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
