package com.entity;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class User {

	private String first_name;
	private String last_name;
	private String uuid;
	private String rider_id;
	private String promocode;
	private boolean mobile_verified;
	private String email;
	private String picture;

	
	public User() {
		// TODO Auto-generated constructor stub
	}

	@JsonProperty("first_name")
	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	@JsonProperty("last_name")
	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	@JsonProperty("uuid")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@JsonProperty("rider_id")
	public String getRider_id() {
		return rider_id;
	}

	public void setRider_id(String rider_id) {
		this.rider_id = rider_id;
	}


	@JsonProperty("promocode")
	public String getPromocode() {
		return promocode;
	}

	public void setPromocode(String promocode) {
		this.promocode = promocode;
	}

	@JsonProperty("mobile_verified")
	public boolean isMobile_verified() {
		return mobile_verified;
	}

	public void setMobile_verified(boolean mobile_verified) {
		this.mobile_verified = mobile_verified;
	}

	@JsonProperty("Email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("picture")
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	
}
