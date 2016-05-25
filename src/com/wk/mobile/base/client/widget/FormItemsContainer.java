package com.wk.mobile.base.client.widget;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;
import java.util.Map;

/**
 * User: werner
 * Date: 15/11/29
 * Time: 7:02 PM
 */
public class FormItemsContainer {

    private HTMLPanel panel = new HTMLPanel("");
    private Map<String, Widget> items = new HashMap<>();


    public FormItemsContainer() {
    }

    public HTMLPanel getPanel() {
        return panel;
    }

    public Map<String, Widget> getItems() {
        return items;
    }

    public void add(String field, Widget widget) {
        panel.add(widget);
        items.put(field, widget);
    }

}
