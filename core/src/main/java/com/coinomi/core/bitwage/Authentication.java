package com.coinomi.core.bitwage;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

public class Authentication extends Connection {

	private static final Logger log = LoggerFactory.getLogger(Authentication.class);

	private static final String USER_AGENT = "Java/1.8";

	private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");

	private static final String LOGIN = "user/login?expire=";

	private int nonce;
	private String appApiKey;
	private String secret;

	public Authentication(OkHttpClient client) {
		super(client);
	}

	public Authentication() {
	}

	public void setApiPublicKey(String appApiKey) {
		this.appApiKey = appApiKey;
		this.nonce = 1;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * Get List of Supported Coins
	 *
	 * List of all the currencies that Shapeshift currently supports at any
	 * given time. Sometimes coins become temporarily unavailable during updates
	 * or unexpected service issues.
	 * 
	 * @throws ShapeShiftException
	 * @throws JSONException 
	 */
	public String getUUID(String username, String password) throws IOException, ShapeShiftException, JSONException {

		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("username", username);
			requestJson.put("password", password);
		} catch (JSONException e) {
			throw new ShapeShiftException("Could not create a JSON request", e);
		}

		String apiUrl = getApiUrl(LOGIN);
		RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, requestJson.toString());
		Request request = new Request.Builder().url(getApiUrl(LOGIN)).addHeader("User-Agent", USER_AGENT)
				.addHeader("USER_APP", "True").addHeader("ACCESS_KEY", appApiKey)
				.addHeader("ACCES_SIGNATURE", getHMAC256Signature(this.appApiKey, this.secret))
				.addHeader("ACCESS_NONCE", Integer.toString(nonce)).addHeader("Content-Type", "application/json")
				.post(body).build();
		JSONObject response = getMakeApiCall(request);
		return response.getString("uuid");
	}
}
