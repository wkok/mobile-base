package com.wk.mobile.base.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.wk.mobile.base.client.BaseClientFactory;

public abstract class BaseAppActivityMapper implements ActivityMapper {

	protected BaseClientFactory clientFactory;

	/**
	 * AppActivityMapper associates each Place with its corresponding {@link Activity}
	 * 
	 * @param clientFactory Factory to be passed to activities
	 */
	public BaseAppActivityMapper(BaseClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	/**
	 * Map each Place to its corresponding Activity.
	 */
	public Activity getActivity(Place place) {
        return doGetActivity(place);
	}

    protected abstract Activity doGetActivity(Place place);

}
