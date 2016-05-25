package com.wk.mobile.base.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.storage.client.Storage;
import com.google.web.bindery.event.shared.EventBus;
import com.smartgwt.client.data.*;
import com.wk.mobile.base.client.i18n.Constants;
import com.wk.mobile.base.client.i18n.Messages;
import gwt.material.design.client.ui.*;

public abstract class BaseClientFactoryImpl implements BaseClientFactory {

	public static final EventBus eventBus = new SimpleEventBus();
	protected static final PlaceController placeController = new PlaceController(eventBus);
    protected String serverURL;
    protected String appName;
    protected String appDescription;
	public static Constants constants = GWT.create(Constants.class);
	public static Messages messages = GWT.create(Messages.class);
    protected SuperDevModeIndicator superDevModeIndicator = GWT.create(SuperDevModeIndicator.class);
	public static Storage localStorage = Storage.getLocalStorageIfSupported();
	public static Storage sessionStorage = Storage.getSessionStorageIfSupported();

    protected MaterialSplashScreen splashScreen;
    protected MaterialLabel waterfallTitle;
    protected MaterialSideNav sideNav;
    protected MaterialNavBar navBar;
    protected MaterialNavBrand navBrand;
    protected MaterialFAB fab;


	public EventBus getEventBus() {
		return eventBus;
	}

	public PlaceController getPlaceController() {
		return placeController;
	}

	public String getServerHost() {
        if (superDevModeIndicator.isSuperDevMode()) {
            return "http://localhost:8081";
        }
        else {
            return serverURL;
        }
	}

	public void setServerHost(String serverURL) {
		this.serverURL = serverURL;
	}

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String getAppDescription() {
        return appDescription;
    }

    @Override
    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public Constants getConstants() {
		return constants;
	}

	public Messages getMessages() {
		return messages;
	}

	public Storage getLocalStorage() {
		return localStorage;
	}

	public Storage getSessionStorage() {
		return sessionStorage;
	}

	public SuperDevModeIndicator getSuperDevModeIndicator() {
		return superDevModeIndicator;
	}

    @Override
    public MaterialSplashScreen getSplashScreen() {
        return splashScreen;
    }

    @Override
    public void setSplashScreen(MaterialSplashScreen splashScreen) {
        this.splashScreen = splashScreen;
    }

    @Override
	public MaterialLabel getWaterfallTitle() {
		return waterfallTitle;
	}

	@Override
	public void setWaterfalltitle(MaterialLabel waterfallTitle) {
		this.waterfallTitle = waterfallTitle;
	}

	@Override
	public MaterialNavBar getNavBar() {
		return navBar;
	}

	@Override
	public void setNavBar(MaterialNavBar navBar) {
		this.navBar = navBar;
	}


	@Override
	public MaterialNavBrand getNavBrand() {
		return navBrand;
	}

	@Override
	public void setNavBrand(MaterialNavBrand navBrand) {
        this.navBrand = navBrand;
	}

	@Override
	public MaterialSideNav getSideNav() {
		return sideNav;
	}

	@Override
	public void setSideNav(MaterialSideNav sideNav) {
		this.sideNav = sideNav;
	}

	@Override
    public MaterialFAB getFAB() {
        return fab;
    }

    @Override
    public void setFAB(MaterialFAB fab) {
        this.fab = fab;
    }


}
