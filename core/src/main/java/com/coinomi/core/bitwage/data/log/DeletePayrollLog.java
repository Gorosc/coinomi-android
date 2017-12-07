package com.coinomi.core.bitwage.data.log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

public class DeletePayrollLog extends BitwageBase {

	private List<BigInteger> success;
	private List<BigInteger> failure;

	public DeletePayrollLog(JSONObject data) throws ShapeShiftException, JSONException {
		super(data);
		if (!isError) {
			try {
				JSONArray successArray = data.optJSONArray("success");
				if (successArray.length() > 0) {
					success = new ArrayList<>();
					for (int i = 0; i < successArray.length(); i++) {
						success.add(new BigInteger(successArray.getString(i)));	
					}
				}

				JSONArray failureArray = data.optJSONArray("failure");
				if (failureArray.length() > 0) {
					failure = new ArrayList<>();
					for (int i = 0; i < failureArray.length(); i++) {
						failure.add(new BigInteger(failureArray.getJSONObject(i).getString("payroll_id")));
					}
				}
			}  catch (Exception e) {
				throw new ShapeShiftException("Could not parse object", e);
			}
		} else {
			success=null;
			failure=null;
		}
	}

	public List<BigInteger> getSuccess() {
		return success;
	}

	public void setSuccess(List<BigInteger> success) {
		this.success = success;
	}

	public List<BigInteger> getFailure() {
		return failure;
	}

	public void setFailure(List<BigInteger> failure) {
		this.failure = failure;
	}

	@Override
	public String toString() {
		return "DeletePayrollLog [success=" + success + ", failure=" + failure + "]";
	}
	
}
