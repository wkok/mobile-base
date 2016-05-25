package com.wk.mobile.base.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
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
public class RetryDialog extends MaterialModal {

    public static void open(String title, String description, final Place place, final BaseClientFactory clientFactory) {
        clientFactory.getSplashScreen().hide();

        final MaterialModal modal = modal().get();
        Midget<MaterialModal> m = new Midget<MaterialModal>(modal);
        m.add(modalContent()
                .add(title().title(title).description(description).get())
                .get())
                .add(modalFooter()
                        .add(button().text(clientFactory.getConstants().retry()).type(ButtonType.FLAT)
                                .addClickHandler(new ClickHandler() {
                                    @Override
                                    public void onClick(ClickEvent event) {
                                        retry(place, modal, clientFactory);
                                    }
                                })
                                .get())
                        .get())
                .get();

        RootPanel.get().add(modal);
        modal.openModal();
    }

    private static void retry(Place place, MaterialModal modal, BaseClientFactory clientFactory) {
        close(modal);
        if (place != null) {
            History.back();
//            clientFactory.getPlaceController().goTo(place);
        }
        else {
            Window.Location.reload();
        }
    }

    private static void close(MaterialModal modal) {
        modal.closeModal();
        RootPanel.get().remove(modal);
    }

}
