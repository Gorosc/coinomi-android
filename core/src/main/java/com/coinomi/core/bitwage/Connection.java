package com.coinomi.core.bitwage;

import java.io.File;
import java.io.IOException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * @author Christos Goros
 */
abstract public class Connection {
	private static final String DEFAULT_BASE_URL = "https://api.bitwage.biz/v1/";

	private static Logger logger = LoggerFactory.getLogger(Connection.class);

	OkHttpClient client;
	String baseUrl = DEFAULT_BASE_URL;

	protected Connection(OkHttpClient client) {
		this.client = client;
	}

	protected Connection() {
		client = new OkHttpClient();
		client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.MODERN_TLS));
	}

	/**
	 * Setup caching. The cache directory should be private, and untrusted
	 * applications should not be able to read its contents!
	 */
	public void setCache(File cacheDirectory) {
		int cacheSize = 256 * 1024; // 256 KiB
		Cache cache = new Cache(cacheDirectory, cacheSize);
		client.setCache(cache);
	}

	public boolean isCacheSet() {
		return client.getCache() != null;
	}

	protected String getApiUrl(String path) {
		return baseUrl + path;
	}

	protected JSONObject getMakeApiCall(Request request) throws ShapeShiftException, IOException {
		try {
			Response response = client.newCall(request).execute();
			System.out.println(response.toString());
			if (!response.isSuccessful()) {
				JSONObject reply = parseReply(response);
				String genericMessage = "Error code " + response.code();
				throw new IOException(reply != null ? reply.optString("error", genericMessage) : genericMessage);
			}
			return parseReply(response);
		} catch (JSONException e) {
			throw new ShapeShiftException("Could not parse JSON", e);
		}
	}

	private static JSONObject parseReply(Response response) throws IOException, JSONException {
		return new JSONObject(response.body().string());
	}

	public static String getHMAC256Signature(String message, String secret) {

        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return Hex.toHexString(mac.doFinal(message.getBytes()));
	}
}
