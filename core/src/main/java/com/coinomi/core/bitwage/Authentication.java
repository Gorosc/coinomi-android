package com.coinomi.core.bitwage;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coinomi.core.bitwage.data.UserKeyPair;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import static com.coinomi.core.bitwage.Constants.ACCESS_KEY;
import static com.coinomi.core.bitwage.Constants.ACCESS_NONCE;
import static com.coinomi.core.bitwage.Constants.ACCESS_SIGNATURE;
import static com.coinomi.core.bitwage.Constants.MEDIA_TYPE_JSON;
import static com.coinomi.core.bitwage.Constants.THIS_USER_AGENT;
import static com.coinomi.core.bitwage.Constants.USER_APP;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.USER_AGENT;

public class Authentication extends Connection {

	private static final Logger log = LoggerFactory.getLogger(Authentication.class);

	private static final String LOGIN = "user/login";
    private static final String TWOFA = "user/twofa?expire=";

	private String appApiKey;
	private String secret;

	public Authentication(OkHttpClient client) {
		super(client);
	}

	public Authentication() {
	}

	public void setApiPublicKey(String appApiKey) {
		this.appApiKey = appApiKey;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public UserKeyPair twofa(String username, String uuid, String access_token) {

        JSONObject requestJson = new JSONObject();
        try {
            requestJson.put("username", username);
            requestJson.put("uuid", uuid);
            requestJson.put("access_token", access_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = getRequest(requestJson, TWOFA);

        JSONObject response = null;
        try {
            response = getMakeApiCall(request);
        } catch (ShapeShiftException | IOException e) {
            e.printStackTrace();
        }
        UserKeyPair userkeypair= null;
        try {
            userkeypair =  new UserKeyPair(response);
        } catch (ShapeShiftException e) {
            e.printStackTrace();
        }
        return userkeypair;
    }

    public String login(String username, String password) {

		JSONObject requestJson = new JSONObject();
		try {
            requestJson.put("username", username);
            requestJson.put("password", password);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Request request = getRequest(requestJson, LOGIN);

        JSONObject response = null;
		try {
			response = getMakeApiCall(request);
		} catch (ShapeShiftException | IOException e) {
			e.printStackTrace();
		}
        String responsestring = "";
        try {
            responsestring = response.getString("uuid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responsestring;
	}

    private Request getRequest(JSONObject requestJson, String path) {
        String accessnonce = String.valueOf(System.currentTimeMillis());
        String apiUrl = getApiUrl(path);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, requestJson.toString());
        return new Request.Builder().url(apiUrl).addHeader(USER_AGENT, THIS_USER_AGENT)
                .addHeader(USER_APP, String.valueOf(true)).addHeader(ACCESS_KEY, this.appApiKey)
                .addHeader(ACCESS_SIGNATURE, getHMAC256Signature(ACCESS_NONCE+apiUrl+requestJson.toString(), this.secret))
                .addHeader(ACCESS_NONCE, accessnonce)
                .addHeader(CONTENT_TYPE, MEDIA_TYPE_JSON.toString())
                .post(body).build();
    }

}
