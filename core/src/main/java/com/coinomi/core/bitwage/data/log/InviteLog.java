package com.coinomi.core.bitwage.data.log;

import org.json.JSONObject;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

public class InviteLog extends BitwageBase{
	
	private String email;
	private String status;
	
	public InviteLog(JSONObject data) throws ShapeShiftException {
		super(data);
		if (!isError) {
			try {
				email = data.getString("email");
				status = data.getString("status");

			} catch (Exception e) {
				throw new ShapeShiftException("Could not parse object", e);
			}
		} else {
			email = null;
			status = null;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
