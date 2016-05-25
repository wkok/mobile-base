package com.wk.mobile.base.client.widget;

import com.google.gwt.user.client.Timer;
import com.wk.mobile.base.client.BaseClientFactory;
import gwt.material.design.client.constants.ProgressType;
import gwt.material.design.client.ui.MaterialLoader;

/**
 * User: werner
 * Date: 15/12/10
 * Time: 4:32 PM
 */
public class Loader {

    private static int delay = 1000;
    private static Boolean showing = false;

    public static void showProgress(final BaseClientFactory clientFactory) {

        showing = true;

        new Timer() {
            @Override
            public void run() {

                if (showing) {
                    if (clientFactory.getNavBar() != null) {
                        clientFactory.getNavBar().showProgress(ProgressType.INDETERMINATE);
                    }
                    else {
                        showLoading();
                    }
//                    MaterialLoader.showProgress(true);
                }
            }
        }.schedule(delay);

    }

    public static void showLoading() {

        showing = true;

        new Timer() {
            @Override
            public void run() {

                if (showing) {
                    MaterialLoader.showLoading(true);
                }
            }
        }.schedule(delay);

    }


    public static void hideProgress(final BaseClientFactory clientFactory) {
        showing = false;
        if (clientFactory.getNavBar() != null) {
            clientFactory.getNavBar().hideProgress();
        }
        else {
            hideLoading();
        }
//        MaterialLoader.showProgress(false);
    }

    public static void hideLoading() {
        showing = false;
        MaterialLoader.showLoading(false);
    }


}
