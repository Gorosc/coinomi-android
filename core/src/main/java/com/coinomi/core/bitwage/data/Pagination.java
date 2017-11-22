package com.coinomi.core.bitwage.data;

import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

/**
 * Created by gkoro on 14-Oct-17.
 */

public class Pagination extends BitwageBase {

    private String curr_page;
    private String next_page;
    private String total_pages;

    public Pagination(JSONObject data) throws ShapeShiftException {
        super(data);
        if (!isError) {
            try {
                curr_page = data.getString("curr_page");
                next_page = data.getString("next_page");
                total_pages = data.getString("total_pages");
            } catch (Exception e) {
                throw new ShapeShiftException("Could not parse object", e);
            }
        } else {
            curr_page = null;
            next_page = null;
            total_pages = null;
        }
    }

    public String getCurr_page() {
        return curr_page;
    }

    public void setCurr_page(String curr_page) {
        this.curr_page = curr_page;
    }

    public String getNext_page() {
        return next_page;
    }

    public void setNext_page(String next_page) {
        this.next_page = next_page;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "curr_page='" + curr_page + '\'' +
                ", next_page='" + next_page + '\'' +
                ", total_pages='" + total_pages + '\'' +
                '}';
    }
}