package com.coinomi.core.bitwage.data.employer.workers;

import com.coinomi.core.bitwage.data.BitwageBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author cgoros
 */

public class Worker extends BitwageBase {

	private String first_name;
	private String last_name;
	private String email;
	private String dob;
	private String phone_number;
	private String street_address;
	private String role;
	private String worker_id;
	private String total_not_fulfilled;
	private String total_fulfilled;
	private Map<String, Double> total_by_currency_fulfilled;
	private Map<String, Double> total_by_currency_not_fulfilled;
	private String receive_method_entered;

	public Worker(JSONObject data) throws JSONException, ShapeShiftException {

		super(data);
		if (!isError) {
			try {
				total_by_currency_fulfilled = new HashMap<>();
				total_by_currency_not_fulfilled = new HashMap<>();

				first_name = data.getString("first_name");
				last_name = data.getString("last_name");
				email = data.getString("email");
				dob = data.getString("dob");
				phone_number = data.getString("phone_number");
				street_address = data.getString("street_address");
				role = data.getString("role");
				worker_id = data.getString("worker_id");
				total_not_fulfilled = data.getString("total_not_fulfilled");
				total_fulfilled = data.getString("total_fulfilled");
				receive_method_entered = data.getString("receive_method_entered");

				JSONObject total_by_currency = data.getJSONObject("total_by_currency");
				JSONObject fulfilled = total_by_currency.getJSONObject("fulfilled");
				JSONObject not_fulfilled = total_by_currency.getJSONObject("not_fulfilled");

				Iterator<String> itr;
				itr = fulfilled.keys();
				while(itr.hasNext()) {
					String name = itr.next();
					try {
						total_by_currency_fulfilled.put(name, Double.parseDouble(fulfilled.getString(name)));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				itr = not_fulfilled.keys();
				while(itr.hasNext()) {
					String name = itr.next();
					try {
						total_by_currency_not_fulfilled.put(name, Double.parseDouble(not_fulfilled.getString(name)));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				throw new ShapeShiftException("Could not parse object", e);
			}
		} else {
			first_name = null;
			last_name = null;
			email = null;
			dob = null;
			phone_number = null;
			street_address = null;
			role = null;
			worker_id = null;
			total_not_fulfilled = null;
			total_fulfilled = null;
			total_by_currency_not_fulfilled = null;
			total_by_currency_fulfilled = null;
			receive_method_entered = null;
		}
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getStreet_address() {
		return street_address;
	}

	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getWorker_id() {
		return worker_id;
	}

	public void setWorker_id(String worker_id) {
		this.worker_id = worker_id;
	}

	public String getTotal_not_fulfilled() {
		return total_not_fulfilled;
	}

	public void setTotal_not_fulfilled(String total_not_fulfilled) {
		this.total_not_fulfilled = total_not_fulfilled;
	}

	public String getTotal_fulfilled() {
		return total_fulfilled;
	}

	public void setTotal_fulfilled(String total_fulfilled) {
		this.total_fulfilled = total_fulfilled;
	}

	public String getReceive_method_entered() {
		return receive_method_entered;
	}

	public void setReceive_method_entered(String receive_method_entered) {
		this.receive_method_entered = receive_method_entered;
	}

	@Override
	public String toString() {
		return "Worker{" +
				"first_name='" + first_name + '\'' +
				", last_name='" + last_name + '\'' +
				", email='" + email + '\'' +
				", dob='" + dob + '\'' +
				", phone_number='" + phone_number + '\'' +
				", street_address='" + street_address + '\'' +
				", role='" + role + '\'' +
				", worker_id='" + worker_id + '\'' +
				", total_not_fulfilled='" + total_not_fulfilled + '\'' +
				", total_fulfilled='" + total_fulfilled + '\'' +
				", total_by_currency_fulfilled=" + total_by_currency_fulfilled +
				", total_by_currency_not_fulfilled=" + total_by_currency_not_fulfilled +
				", receive_method_entered='" + receive_method_entered + '\'' +
				'}';
	}
}