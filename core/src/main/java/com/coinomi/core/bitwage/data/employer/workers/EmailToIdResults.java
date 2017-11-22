package com.coinomi.core.bitwage.data.employer.workers;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.bitwage.data.log.EmailToIdLog;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gkoro on 14-Oct-17.
 */

public class EmailToIdResults extends BitwageBase {

    private List<EmailToIdLog> successList;
    private List<EmailToIdLog> failureList;

    public EmailToIdResults(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                successList = new ArrayList<>();
                failureList = new ArrayList<>();
                JSONArray successArray = data.optJSONArray("success");
                for (int i = 0; i < successArray.length(); i++) {
                    successList.add(new EmailToIdLog(successArray.getJSONObject(i)));
                }
                JSONArray failureArray = data.optJSONArray("failure");
                for (int i = 0; i < failureArray.length(); i++) {
                    failureList.add(new EmailToIdLog(failureArray.getJSONObject(i)));
                }
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            successList = null;
            failureList = null;
        }
    }

    public List<EmailToIdLog> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<EmailToIdLog> successList) {
        this.successList = successList;
    }

    public List<EmailToIdLog> getFailureList() {
        return failureList;
    }

    public void setFailureList(List<EmailToIdLog> failureList) {
        this.failureList = failureList;
    }

    @Override
    public String toString() {
        return "EmailToIdResults{" +
                "successList=" + successList +
                ", failureList=" + failureList +
                '}';
    }
}
