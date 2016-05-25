package com.wk.mobile.base.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.rpc.RPCResponse;
import com.wk.mobile.base.client.util.DS;
import com.wk.mobile.base.client.widget.Loader;
import com.wk.mobile.base.client.widget.RetryDialog;

/**
 * User: werner
 * Date: 15/06/04
 * Time: 7:05 PM
 */
public abstract class MobileDSCallback implements DSCallback {

    private BaseClientFactory clientFactory;
    private Place place = null;

    public MobileDSCallback(Place place, BaseClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.place = place;
    }

    public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {

        Loader.hideProgress(clientFactory);
        if (dsResponse.getStatus() == RPCResponse.STATUS_SUCCESS) {
            assertIsLoggedIn(dsResponse);
            GWT.log(this.getClass().getName() + " DataSource: " + dsRequest.getDataSource() + " - STATUS_SUCCESS");
            onSuccess(dsResponse, data, dsRequest);
        }
        else if (dsResponse.getStatus() == RPCResponse.STATUS_OFFLINE) {
            RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), place, clientFactory);
        }
        else {
            BaseMobileEntryPoint.showError(DS.constructErrorDebugString(null, dsRequest, dsResponse), clientFactory);
        }
    }


    private void assertIsLoggedIn(DSResponse dsResponse) {
        if (dsResponse.getHttpResponseText() != null) {
            if (dsResponse.getHttpResponseText()
                    .replaceAll("\\s", "")
                    .contains("metahttp-equiv=\"refresh\"content=\"0;url=login.html\"")) {

                Window.Location.assign("login.html");
            }
        }
    }


    protected abstract void onSuccess(DSResponse dsResponse, Object data, DSRequest dsRequest);

}
