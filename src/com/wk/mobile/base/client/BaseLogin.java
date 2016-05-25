package com.wk.mobile.base.client;

import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.core.Function;
import com.smartgwt.client.data.*;

/**
 * User: werner
 * Date: 15/11/25
 * Time: 8:50 AM
 */
public abstract class BaseLogin extends BaseMobileEntryPoint {


    @Override
    protected String smartClientURLPath() {
        return "/login/sc";
    }

    @Override
    protected void doModuleLoad() {

        DataSource.load(dataSourceNames(), new Function() {
            @Override
            public void execute() {
                RootPanel.get().add(new LoginForm("", clientFactory, viewWrapper, historyHandler));

                clientFactory.getSplashScreen().hide();
            }
        }, false);
    }


}
