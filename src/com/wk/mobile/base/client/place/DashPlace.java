package com.wk.mobile.base.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

public class DashPlace extends BaseMobilePlace {

	public DashPlace(String token) {
		super(token);
	}

	public static class Tokenizer implements PlaceTokenizer<DashPlace> {

		public String getToken(DashPlace place) {
			return place.getToken();
		}

		public DashPlace getPlace(String token) {
			return new DashPlace(token);
		}
	}

}
