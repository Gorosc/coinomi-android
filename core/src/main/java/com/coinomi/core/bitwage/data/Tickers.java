package com.coinomi.core.bitwage.data;

import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by gkoro on 05-Sep-17.
 */

public class Tickers extends BitwageBase {

    //TODO: Create Ticker enum
    private HashMap<String, Double> tickers;
    private static final String TIMESTAMP = "datetimeUTC";

    public Tickers(JSONObject data) throws ShapeShiftException {
        super(data);
        tickers = new HashMap<>();
        Iterator<String> itr;
        itr = data.keys();
        while(itr.hasNext()) {
            String name = itr.next();
            if (!name.equals(TIMESTAMP)) {
                try {
                    tickers.put(name, Double.parseDouble(data.getString(name)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public HashMap<String, Double> getTickers() {
        return tickers;
    }
}
