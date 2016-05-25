package com.wk.mobile.base.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.*;
import com.smartgwt.client.data.*;
import com.smartgwt.client.rpc.*;
import com.wk.mobile.base.client.mvp.BaseAppPlaceHistoryMapper;
import com.wk.mobile.base.client.place.DashPlace;
import com.wk.mobile.base.client.util.DS;
import com.wk.mobile.base.client.widget.Loader;
import com.wk.mobile.base.client.widget.OneWidget;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.ui.MaterialSplashScreen;
import gwt.material.design.client.ui.MaterialToast;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.wk.mobile.base.client.widget.Midget.*;

/**
 * User: werner
 * Date: 15/11/15
 * Time: 12:15 PM
 */
public abstract class BaseMobileEntryPoint implements EntryPoint {

    protected MaterialSplashScreen splashScreen = createAndShowSplashScreen();
    protected static BaseClientFactory clientFactory;
    protected OneWidget viewWrapper;
    protected BaseAppPlaceHistoryMapper historyMapper;
    protected PlaceHistoryHandler historyHandler;
    protected static PhoneGap phoneGap;
    protected static boolean appInitialised = false;
    protected static boolean exitAppRequested = false;



    public void onModuleLoad() {
        GWT.log(this.getClass().getName() +  " - onModuleLoad");
        createPhonegap();
        initNonPhonegapStuffs();
    }

    private void createPhonegap() {

        GWT.log(this.getClass().getName() +  " - initPhonegap");

        phoneGap = GWT.create(PhoneGap.class);

        phoneGap.addHandler(new PhoneGapAvailableHandler() {

            public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
                if (exitAppRequested) {
                    phoneGap.exitApp();
                }
                else {
                    initPhonegapStuffs();
                }
            }
        });


        phoneGap.addHandler(new PhoneGapTimeoutHandler() {

            public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
                throw new RuntimeException("Timed out waiting for PhoneGap - " + event.toString());
            }
        });

        phoneGap.initializePhoneGap();
    }

    private void initPhonegapStuffs() {
        phoneGap.getSplashScreen().hide();
    }


    private void initNonPhonegapStuffs() {
        GWT.log(this.getClass().getName() +  " - init");

        clientFactory = createClientFactory();
        clientFactory.setSplashScreen(splashScreen);
        clientFactory.setAppName(appName());
        clientFactory.setAppDescription(appDescription());

        viewWrapper = new OneWidget("");
        historyMapper = createPlaceHistoryMapper();
        historyHandler = new PlaceHistoryHandler(historyMapper);

        clientFactory.setServerHost(serverURL());

        EventBus eventBus = clientFactory.getEventBus();

        PlaceController placeController = clientFactory.getPlaceController();

        ActivityMapper activityMapper = createAppActivityMapper();
        ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(viewWrapper);

        Place defaultPlace = new DashPlace("");
        historyHandler.register(placeController, eventBus, defaultPlace);

        registerEventHandlers(eventBus, placeController);

        initUncaughtExceptionHandler(clientFactory);

        setSmartGWTURLs();
        doModuleLoad();

    }


    private void setSmartGWTURLs() {
        RPCManager.setActionURL(clientFactory.getServerHost() + smartClientURLPath() + "/IDACall");
        DataSource.setLoaderURL(clientFactory.getServerHost() + smartClientURLPath() + "/DataSourceLoader");
    }


    private MaterialSplashScreen createAndShowSplashScreen() {
        MaterialSplashScreen splashScreen = splashScreen().backgroundColor("blue lighten-2").textColor("white").textAlign(TextAlign.CENTER)
//                .add(image().url("images/avatar.png").width("100px").get())
                .add(title().title(appName()).description(appDescription()).get())
                .get();

        RootPanel.get().add(splashScreen);

        splashScreen.show();

        return splashScreen;
    }


    private void initUncaughtExceptionHandler(final BaseClientFactory clientFactory) {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {

            @Override
            public void onUncaughtException(final Throwable err) {
                hideNotifs(clientFactory);
                String errMsg = calcErrMsg(err);
                if (errMsg != null &&
                        !errMsg.equals("")) {

                    if (!errMsg.equals("Exception caught: undefined")) { // When editing a decimal in a float text field this error gets thrown
                        Record record = new Record();
                        record.setAttribute("custom_error", errMsg);
                        showError(record, clientFactory);
                    }
                }
                else {
                    if (!errMsg.equals("Exception caught: undefined")) { // When editing a decimal in a float text field this error gets thrown
                        Record record = new Record();
                        record.setAttribute("custom_error", clientFactory.getConstants().anErrorOccurredPleaseTryAgainLater());
                        showError(record, clientFactory);
                    }
                }
            }
        });

        RPCManager.setHandleErrorCallback(new HandleErrorCallback() {
            @Override
            public void handleError(DSResponse response, DSRequest request) {

                hideNotifs(clientFactory);
                Record record = DS.constructErrorDebugString(null, request, response);

                if (errorRecordContains("Email Not Registered", record)) {
                    MaterialToast.fireToast(clientFactory.getConstants().emailNotRegistered());
                }
                else if (errorRecordContains("foreign key constraint", record)) {
                    MaterialToast.fireToast(clientFactory.getConstants().stillInUseCannotDelete());
                }
                else {
                    showError(record, clientFactory);
                }
            }
        });


        RPCManager.setLoginRequiredCallback(new LoginRequiredCallback() {
            @Override
            public void loginRequired(int transactionNum, RPCRequest rpcRequest, RPCResponse rpcResponse) {
                hideNotifs(clientFactory);
                Window.Location.assign("login.html");
            }
        });

    }

    private boolean errorRecordContains(String s, Record record) {
        String custom_error = record.getAttribute("custom_error");
        if (custom_error == null)
            custom_error = "";

        Map<String, String> request_errors = record.getAttributeAsMap("request_errors");
        if (request_errors == null)
            request_errors = new HashMap<>();

        Map<String, String> response_errors = record.getAttributeAsMap("response_errors");
        if (response_errors == null)
            response_errors = new HashMap<>();


        if (custom_error.contains(s) ||
                request_errors.toString().contains(s) ||
                response_errors.toString().contains(s)) {

            return true;
        }
        return false;
    }

    private String calcErrMsg(Throwable err) {
        if (err.getCause() != null
                && err.getCause().getMessage() != null
                && !err.getCause().getMessage().equals("")) {

            return err.getCause().getMessage();
        }
        else {
            return err.getMessage();
        }
    }

    private void hideNotifs(BaseClientFactory clientFactory) {
        Loader.hideProgress(clientFactory);
        clientFactory.getSplashScreen().hide();
    }

    public static void showError(final Record record, final BaseClientFactory clientFactory) {
        if (appInitialised) {
            new MaterialToast(link().text(clientFactory.getConstants().reportIt())
                    .addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            reportError(record);
//                            ErrorDialog.open(error, clientFactory);
                        }
                    })
                    .get())
                    .toast(clientFactory.getConstants().oopsErrorOccurred());
        }
        else {
            Window.alert(record.getAttribute("custom_error"));
            exitApp();
        }
    }

    private static void reportError(Record record) {
        DataSource.get("user").performCustomOperation("reportError", record, new DSCallback() {
            @Override
            public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                MaterialToast.fireToast(clientFactory.getConstants().errorReportedThankYou());
            }
        });
    }

    private static void exitApp() {
        exitAppRequested = true;
        phoneGap.exitApp();
    }


    @NotNull
    protected String[] dataSourceNames() {
        return new String[]{
                "customer",
                "customer_module_assigned",
                "customer_module_unassigned",
                "dashboard",
                "user_dashboard",
                "userright",
                "usergroup",
                "usergroup_userright_unassigned",
                "usergroup_userright_assigned",
                "user",
                "customer_datasource_field",
                "transaction",
                "category",
                "account"
        };
    }


    protected abstract String appName();
    protected abstract String appDescription();
    protected abstract String serverURL();
    protected abstract String smartClientURLPath();
    protected abstract BaseClientFactory createClientFactory();
    protected abstract ActivityMapper createAppActivityMapper();
    protected abstract BaseAppPlaceHistoryMapper createPlaceHistoryMapper();
    protected abstract void doModuleLoad();
    protected abstract void registerEventHandlers(EventBus eventBus, PlaceController placeController);

}
