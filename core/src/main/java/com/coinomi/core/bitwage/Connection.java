package com.coinomi.core.bitwage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.macs.HMac;
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
	private static final String DEFAULT_BASE_URL = " https://api.bitwage.biz/v1/";

	private Logger logger = LoggerFactory.getLogger(Connection.class);

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

	protected String getHMAC256Signature(String apiKey, String secret) {

		byte[] b = apiKey.getBytes();
		try {
			SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
			HMac sha256_HMAC = new HMac(new SHA256Digest());
			sha256_HMAC.init((CipherParameters) secretKey);
			sha256_HMAC.doFinal(b, 0);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		return Hex.toHexString(b);
	}
}
