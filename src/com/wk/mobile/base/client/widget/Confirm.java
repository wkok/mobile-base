package com.wk.mobile.base.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.wk.mobile.base.client.BaseClientFactory;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.ui.*;


/**
 * User: werner
 * Date: 15/11/30
 * Time: 8:28 AM
 */
public class Confirm extends MaterialModal {

    public void open(String titleText, String descriptionText, CloseHandler<MaterialModal> closeHandler, BaseClientFactory clientFactory) {

        MaterialButton yesButton = new MaterialButton(ButtonType.FLAT);
        yesButton.setText(clientFactory.getConstants().yes());
        yesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                close(Confirm.this, false);
            }
        });

        MaterialButton noButton = new MaterialButton(ButtonType.FLAT);
        noButton.setText(clientFactory.getConstants().no());
        noButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                close(Confirm.this, true);
            }
        });

        MaterialTitle title = new MaterialTitle();
        title.setTitle(titleText);
        title.setDescription(descriptionText);

        MaterialModalFooter footer = new MaterialModalFooter();
        footer.add(yesButton);
        footer.add(noButton);

        MaterialModalContent content = new MaterialModalContent();
        content.add(title);
        content.add(footer);

        add(content);

        addCloseHandler(closeHandler);

        RootPanel.get().add(this);
        openModal();
    }

    private static void close(MaterialModal modal, boolean autoClosed) {
        modal.closeModal(autoClosed);
        RootPanel.get().remove(modal);
    }

}
