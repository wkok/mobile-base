package com.wk.mobile.base.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.wk.mobile.base.client.BaseClientFactory;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.ui.MaterialModal;

import static com.wk.mobile.base.client.widget.Midget.*;

/**
 * User: werner
 * Date: 15/11/30
 * Time: 8:28 AM
 */
public class ErrorDialog extends MaterialModal {

    public static void open(String error, final BaseClientFactory clientFactory) {
        final MaterialModal modal = modal().get();
        Midget<MaterialModal> m = new Midget<MaterialModal>(modal);
        m.add(modalContent()
                .add(title().title(clientFactory.getConstants().errorDetail()).description(error).get())
                .get())
                .add(modalFooter()
//                        .add(button().text(clientFactory.getConstants().reportIt()).type(ButtonType.FLAT)
//                                .addClickHandler(new ClickHandler() {
//                                    @Override
//                                    public void onClick(ClickEvent event) {
//                                        close(modal);
//                                    }
//                                })
//                                .get())
                        .add(button().text(clientFactory.getConstants().close()).type(ButtonType.FLAT)
                                .addClickHandler(new ClickHandler() {
                                    @Override
                                    public void onClick(ClickEvent event) {
                                        close(modal);
                                    }
                                })
                                .get())
                        .get())
                .get();

        RootPanel.get().add(modal);
        modal.openModal();
    }

    private static void close(MaterialModal modal) {
        modal.closeModal();
        RootPanel.get().remove(modal);
    }

}
