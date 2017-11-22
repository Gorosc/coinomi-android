package com.coinomi.core.bitwage.data.employer.workers;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.bitwage.data.Pagination;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gkoro on 14-Oct-17.
 */

public class WorkersSimple extends BitwageBase {

    private List<WorkerSimple> workerSimpleList;
    private Pagination meta;

    public WorkersSimple(JSONObject data)  throws ShapeShiftException {
        super(data);
        workerSimpleList = new ArrayList<>();
        if (!isError) {
            try {
                JSONArray workersArray = data.getJSONArray("workers");
                for (int i=0 ; i < workersArray.length() ; i++) {
                    workerSimpleList.add(new WorkerSimple(workersArray.getJSONObject(i)));
                }
                meta = new Pagination(data.getJSONObject("meta"));
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            workerSimpleList = null;
            meta = null;
        }
    }

    public List<WorkerSimple> getWorkerSimpleList() {
        return workerSimpleList;
    }

    public void setWorkerSimpleList(List<WorkerSimple> workerSimpleList) {
        this.workerSimpleList = workerSimpleList;
    }

    public Pagination getMeta() {
        return meta;
    }

    public void setMeta(Pagination meta) {
        this.meta = meta;
    }
}
