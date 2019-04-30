package com.inloco.gateway.model;

import java.util.Date;

public class UserInteraction {

	private String website;
	private String user;
	private InteractionType event;
	private Date timestamp;

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public InteractionType getEvent() {
		return event;
	}

	public void setEvent(InteractionType event) {
		this.event = event;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
