package com.coinomi.core.bitwage.data.log;

import org.json.JSONException;
import org.json.JSONObject;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

public class EmailToIdLog extends BitwageBase {

	private String email;
	private String user_id;

	public EmailToIdLog(JSONObject data) throws ShapeShiftException, JSONException {
		super(data);
		email = data.getString("email");
		if (!isError) {
			try {
				user_id=data.getString("user_id");
			}  catch (Exception e) {
				throw new ShapeShiftException("Could not parse object", e);
			}
		} else {
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

	@Override
	public String toString() {
		return "EmailToIdLog{" +
				"email='" + email + '\'' +
				", user_id='" + user_id + '\'' +
				'}';
	}
}
