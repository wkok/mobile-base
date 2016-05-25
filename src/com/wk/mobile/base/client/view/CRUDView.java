package com.wk.mobile.base.client.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.smartgwt.client.data.Record;

/**
 * User: werner
 * Date: 15/11/29
 * Time: 1:44 PM
 */
public interface CRUDView extends IsWidget {
    void setData(Record[] records);
    void updateFAB();
}
