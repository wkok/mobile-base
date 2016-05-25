package com.wk.mobile.base.client.place;

import com.google.gwt.place.shared.Place;

public class BaseMobilePlace extends Place {

	protected String token;
	protected Place returnPlace;

	public BaseMobilePlace(String token) {
		this.token = token;
	}

	public BaseMobilePlace(String token, Place returnPlace) {
		this.token = token;
        this.returnPlace = returnPlace;
	}

	public String getToken() {
		return token;
	}

    public Place getReturnPlace() {
        return returnPlace;
    }
}
