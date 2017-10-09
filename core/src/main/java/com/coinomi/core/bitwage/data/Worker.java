package com.coinomi.core.bitwage.data;

import com.coinomi.core.exchange.shapeshift.data.ShapeShiftBase;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;

import org.json.JSONObject;

/**
 * @author cgoros
 */

final public class Worker extends ShapeShiftBase {

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
	private String receive_method_entered;

	public Worker(JSONObject data) throws ShapeShiftException {
		super(data);
		if (!isError) {
			try {
				first_name = data.getString("first_name");
				last_name = data.getString("last_name");
				email = data.getString("email");
				dob = data.getString("dob");
				phone_number = data.getString("phone_number");
				street_address = data.getString("street_addres");
				role = data.getString("role");
				worker_id = data.getString("worker_id");
				total_not_fulfilled = data.getString("total_not_fulfilled");
				total_fulfilled = data.getString("total_fulfilled");
				receive_method_entered = data.getString("receive_method_entered");

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
	
}