package com.wk.mobile.base.client;

import com.wk.mobile.base.client.widget.Midget;
import gwt.material.design.client.ui.MaterialTooltip;

import java.util.ArrayList;
import java.util.List;

/**
 * User: werner
 * Date: 15/12/11
 * Time: 8:09 PM
 */
public class BaseFAB {

    protected static List<Midget<MaterialTooltip>> toolTips = new ArrayList<>();

    public static void hide(BaseClientFactory clientFactory) {
        clearToolTips();
        clientFactory.getFAB().setVisible(false);
    }

    private static void clearToolTips() {
        for (Midget<MaterialTooltip> tooltip : toolTips) {
            tooltip.get().remove();
        }
        toolTips.clear();
    }

    protected static String calcBackgroundColor(int index) {
        switch (index) {
            case 0:  return "red";
            case 1:  return "blue";
            case 2:  return "green";
            default: return "green";
        }
    }

}
