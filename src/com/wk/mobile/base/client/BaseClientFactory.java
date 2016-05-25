package com.wk.mobile.base.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.storage.client.Storage;
import com.google.web.bindery.event.shared.EventBus;
import com.wk.mobile.base.client.i18n.Constants;
import com.wk.mobile.base.client.i18n.Messages;
import gwt.material.design.client.ui.*;

public interface BaseClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	String getServerHost();
	void setServerHost(String serverURL);
	String getAppName();
	void setAppName(String appName);
	String getAppDescription();
	void setAppDescription(String appDescription);
	Constants getConstants();
	Messages getMessages();
	Storage getLocalStorage();
	Storage getSessionStorage();
	SuperDevModeIndicator getSuperDevModeIndicator();

	MaterialSplashScreen getSplashScreen();
    void setSplashScreen(MaterialSplashScreen splashScreen);

	MaterialLabel getWaterfallTitle();
    void setWaterfalltitle(MaterialLabel navBar);

	MaterialNavBar getNavBar();
    void setNavBar(MaterialNavBar navBar);

	MaterialNavBrand getNavBrand();
    void setNavBrand(MaterialNavBrand navBrand);

	MaterialSideNav getSideNav();
    void setSideNav(MaterialSideNav sideNav);

	MaterialFAB getFAB();
    void setFAB(MaterialFAB fab);

}
