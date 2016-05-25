package com.wk.mobile.base.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.smartgwt.client.data.*;
import com.wk.mobile.base.client.BaseClientFactory;
import com.wk.mobile.base.client.MobileDSCallback;
import com.wk.mobile.base.client.mvp.MyAbstractActivity;
import com.wk.mobile.base.client.util.DS;
import com.wk.mobile.base.client.view.CRUDView;

/**
 * User: werner
 * Date: 15/11/18
 * Time: 3:27 PM
 */
public abstract class CRUDActivity extends MyAbstractActivity {

    protected BaseClientFactory clientFactory;
    protected CRUDView display;
    protected Place place;

    public CRUDActivity(Place place, BaseClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.place = place;
    }

    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        display = getDisplay();
        panel.setWidget(display);

        setViewHeading();

        clientFactory.getFAB().setVisible(true);

        bind(eventBus);

        doLoad();

        display.updateFAB();
    }

    private void setViewHeading() {
        String title = getDataSource().getTitle();
        clientFactory.getNavBrand().setText(title);
        clientFactory.getWaterfallTitle().setText(title);
    }


    private void doLoad() {

        DSRequest requestProperties = new DSRequest();

        SortSpecifier[] sort = getSort();
        if (sort != null) {
            requestProperties.setSortBy(sort);
        }

        DS.fetch(getDataSource(), new Criteria(), new MobileDSCallback(place, clientFactory) {

            @Override
            protected void onSuccess(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                display.setData(dsResponse.getData());
                clientFactory.getSplashScreen().hide();
            }
        }, requestProperties, clientFactory);

    }



    private void bind(EventBus eventBus) {
    }


    protected abstract SortSpecifier[] getSort();
    protected abstract CRUDView getDisplay();
    protected abstract DataSource getDataSource();

}
