package com.wk.mobile.base.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.smartgwt.client.core.Function;
import com.smartgwt.client.data.*;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.Offline;
import com.wk.mobile.base.client.event.DashEvent;
import com.wk.mobile.base.client.util.DS;
import com.wk.mobile.base.client.widget.RetryDialog;


/**
 * User: werner
 * Date: 15/11/15
 * Time: 12:15 PM
 */
public abstract class Base extends BaseMobileEntryPoint {


    @Override
    protected void doModuleLoad() {
        checkUserLoggedIn();
    }


    private void checkUserLoggedIn() {
        if (!Offline.isOffline()) {

            checkUserLoggedIn(new RPCCallback() {
                @Override
                public void execute(RPCResponse response, Object rawData, RPCRequest request) {

                    if (response.getStatus() == RPCResponse.STATUS_SUCCESS) {

                        if (rawData != null && rawData.toString().trim().equals("1")) {
                            loadDataSources();
                        }
                        else if (rawData != null && rawData.toString().trim().equals("0")) {
                            Window.Location.assign("login.html");
                        }
                        else {
                            Window.Location.assign("login.html?toast=" + constructLoginErrorMessage(rawData.toString().trim()));
                        }
                    }
                    else if(response.getStatus() == RPCResponse.STATUS_OFFLINE) {
                        RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
                    }
                    else {
                        processRPCResponseError(clientFactory.getConstants().responseStatus() + ": " + response.getStatus(), request, response);
                    }
                }
            });
        } else {
            RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
        }
    }

    private void loadDataSources() {
        if (!Offline.isOffline()) {

            DataSource.load(dataSourceNames(), new Function() {
                @Override
                public void execute() {
                    setDataSourceProperties();
                    loadSessionUser();
                }
            }, false);
        }
        else {
            RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
        }
    }

    private void loadSessionUser() {
        if (!Offline.isOffline()) {

            loadSessionUser(new DSCallback() {
                @Override
                public void execute(final DSResponse response, Object data, DSRequest dsRequest) {

                    if (response.getStatus() == RPCResponse.STATUS_SUCCESS) {
                        loadSessionRights(response.getData()[0]);
                    } else if (response.getStatus() == RPCResponse.STATUS_OFFLINE) {
                        RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
                    } else {
                        processRPCResponseError(clientFactory.getConstants().responseStatus() + ": "+response.getStatus(), dsRequest, response);
                    }
                }
            });
        }
        else {
            RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
        }

    }

    private void loadSessionRights(final Record user) {
        if (!Offline.isOffline()) {

            loadSessionRights(user.getAttribute("usergroup_id"), new DSCallback() {
                @Override
                public void execute(DSResponse response, Object data, DSRequest dsRequest) {

                    if (response.getStatus() == RPCResponse.STATUS_SUCCESS) {

                        Record[] rights = response.getData();

                        Session.init(user.getAttribute("username"), user.getAttributeAsInt("id"), user.getAttributeAsInt("usergroup_id"), user.getAttributeAsInt("customer_id"), user.getAttribute("customer_name"), user.getAttribute("site_name"), rights, user.getAttribute("auth_token"));

                        loadLookupsInBackground();

                        RootPanel.get().add(createSiteWrapper());

                        loadChartAPI();
                    }
                    else if (response.getStatus() == RPCResponse.STATUS_OFFLINE) {
                        RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
                    }
                    else {
                        processRPCResponseError(clientFactory.getConstants().responseStatus() + ": "+response.getStatus(), dsRequest, response);
                    }
                }

            });
        }
        else {
            RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
        }

    }



    private void loadChartAPI() {
        if (!Offline.isOffline()) {

            ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
            chartLoader.loadApi(new Runnable() {
                @Override
                public void run() {
                    navigateToDash();
                }
            });
        }
        else {
            RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
        }

    }

    private void navigateToDash() {
        appInitialised = true;
        DashEvent.fire(clientFactory.getEventBus());
    }

    private void processRPCResponseError(String customMsg, RPCRequest request, RPCResponse response) {
        showError(DS.constructErrorDebugString(customMsg, request, response), clientFactory);
    }

    private void setDataSourceProperties() {
        GWT.log(this.getClass().getName() + " - setDataSourceProperties");
        for (String dsName : dataSourceNames()) {
            DataSource dataSource = DataSource.get(dsName);

            dataSource.setShowPrompt(false);
            dataSource.setUseOfflineStorage(true);
        }
    }





    private String constructLoginErrorMessage(String error) {
        if (error.equalsIgnoreCase("Bad credentials")) {
            return clientFactory.getConstants().invalidEmailAddressOrPassword();
        }
        else if (error.equalsIgnoreCase("User is disabled")) {
            return clientFactory.getConstants().pleaseActivateYourAccount();
        }
        return error;
    }

    private void checkUserLoggedIn(final RPCCallback rpcCallback) {
        GWT.log(this.getClass().getName() + " - checkUserLoggedIn");
        RPCRequest rpcRequest = new RPCRequest();
        DS.setAuthToken(rpcRequest);
        rpcRequest.setActionURL(clientFactory.getServerHost()+"/userLoggedIn.jsp");
        RPCManager.sendRequest(rpcRequest, rpcCallback);
    }


    private void loadSessionUser(final DSCallback dsCallback) {
        GWT.log(this.getClass().getName() + " - loadSessionUser");
        DSRequest dsRequest = new DSRequest();
        dsRequest.setOperationId("session");
        DataSource.get("user").fetchData(null, dsCallback, dsRequest);
    }

    private void loadSessionRights(final String usergroup_id, final DSCallback dsCallback) {
        GWT.log(this.getClass().getName() + " - loadSessionRights, usergroup_id: "+usergroup_id);
        DSRequest dsRequest = new DSRequest();
        DataSource.get("usergroup_userright_assigned").fetchData(new Criteria("usergroup_id", usergroup_id), dsCallback, dsRequest);
    }


    protected abstract void loadLookupsInBackground();
    protected abstract IsWidget createSiteWrapper();

}
