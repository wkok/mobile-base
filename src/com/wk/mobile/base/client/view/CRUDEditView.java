package com.wk.mobile.base.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import java.util.Map;

public interface CRUDEditView extends IsWidget {
    Map<String, Widget> getFormItems();
	HasClickHandlers getCancelButton();
	HasClickHandlers getSaveButton();
	HasClickHandlers getDeleteButton();
}
