package com.wk.mobile.base.client.widget;

import com.google.gwt.user.client.ui.*;

public class OneWidget extends HTMLPanel implements AcceptsOneWidget {


    public OneWidget(String html) {
        super(html);
        addStyleName("oneWidget");
    }

    public void setWidget(IsWidget w) {
        WidgetCollection membersToRemove = getChildren();
        for(Widget member : membersToRemove) {
            remove(member);
        }
        if (w != null) {
            add(w);
        }
    }
}
