package com.wk.mobile.base.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.Offline;
import org.jetbrains.annotations.NotNull;

public class Session {

	private static Session instance;

	private final String username;
	private final int user_id;
	private final int usergroup_id;
	private final int customer_id;
	private final String customer_name;
	private final String site_name;
	private final String auth_token;

	private Record[] rights;


	private Session(String username, int user_id, int usergroup_id, int customer_id, String customer_name, String site_name, Record[] rights, String auth_token) {
		this.user_id = user_id;
		this.username = username;
		this.usergroup_id = usergroup_id;
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.site_name = site_name;
		this.rights = rights;
		this.auth_token = auth_token;
	}

	public static void init(String username, int user_id, int usergroup_id, int customer_id, String customer_name, String site_name, Record[] rights, String auth_token) {
		GWT.log(Session.class.getName() + " - init");
		if (instance == null) {
			instance = new Session(username, user_id, usergroup_id, customer_id, customer_name, site_name, rights, auth_token);

            Offline.put("auth_token", auth_token);
		}
		else {
			throw new IllegalStateException("Session was already initialised. You cannot call init again.");
		}
	}

	@NotNull
	public static Session get() {
		if (instance == null) {
			throw new IllegalStateException("Session has not been initialised");
		}
		return instance;
	}

	public static int getUserID() {
		return Session.get().user_id;
	}

	public static int getUserGroupID() {
		return Session.get().usergroup_id;
	}

	public static int getCustomerID() {
		return Session.get().customer_id;
	}

	public static String getCustomerName() {
		return Session.get().customer_name;
	}

	public static String getSiteName() {
		return Session.get().site_name;
	}

	public static String getLoginName() {
		return Session.get().username;
	}

	public static boolean hasRight(String right)  {
		for (Record assignedRight : get().rights) {
			if (assignedRight.getAttribute("userright_name").equalsIgnoreCase(right)) {
				return true;
			}
		}
		return false;
	}


}
