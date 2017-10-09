package com.coinomi.core.bitwage.data.LogMessages;

import org.json.JSONObject;

import com.coinomi.core.exchange.shapeshift.data.ShapeShiftBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

public class EmailToIdLog extends ShapeShiftBase{
	
	private String email;
	private String user_id;
	
	public EmailToIdLog(JSONObject data) throws ShapeShiftException {
		super(data);
		if (!isError) {
			try {
				email = data.getString("email");
				user_id = data.getString("user_id");

			} catch (Exception e) {
				throw new ShapeShiftException("Could not parse object", e);
			}
		} else {
			email = null;
			user_id = null;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}
