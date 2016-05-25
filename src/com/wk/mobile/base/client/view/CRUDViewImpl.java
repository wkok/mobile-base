package com.wk.mobile.base.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.Offline;
import com.wk.mobile.base.client.BaseClientFactory;
import com.wk.mobile.base.client.widget.Midget;
import gwt.material.design.addins.client.ui.MaterialCutOut;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialContainer;


public abstract class CRUDViewImpl extends MaterialContainer implements CRUDView {

    protected BaseClientFactory clientFactory;

    protected HTMLPanel panel = new HTMLPanel("");

    public CRUDViewImpl(BaseClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        panel.addStyleName("contentBody");
        add(panel);
    }



    @Override
    public void setData(Record[] records) {

        panel.clear();
        if (records != null && records.length > 0) {
            Midget<MaterialCollection> collection = Midget.collection();
            panel.add(collection.get());

            for (final Record record : records) {
                collection.add(Midget.collectionItem()
                        .add(displayLine1(record))
                        .add(displayLine2(record))
                        .add(displayLine3(record))
                        .add(displayLine4(record))
                        .add(Midget.collectionSecondary()
                                .add(Midget.icon().iconType(IconType.EDIT).iconPosition(IconPosition.RIGHT)
                                        .addClickHandler(new ClickHandler() {
                                            @Override
                                            public void onClick(ClickEvent event) {
                                                editClicked(record);
                                            }
                                        })
                                        .get())
                                .get())
                        .get());
            }
        }
        else {
            doCutOut();
        }
    }


    private void doCutOut() {

        Object cutOutGotIt = Offline.get(this.getClass().getName()+"cutOutGotIt");
        if (cutOutGotIt == null || cutOutGotIt.toString().equals("false")) {

            final Midget<MaterialCutOut> cutOut = Midget.cutOut();

            MaterialButton button = Midget.button()
                    .text(clientFactory.getConstants().gotIt())
                    .textColor("blue")
                    .backgroundColor("white")
                    .addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            Offline.put(CRUDViewImpl.this.getClass().getName()+"cutOutGotIt", true);
                            cutOut.get().closeCutOut();
                        }
                    })
                    .get();


            cutOut
                    .target(clientFactory.getFAB())
                    .backgroundColor("blue")
                    .circle(true)
                    .opacity(0.8)
                    .textAlign(TextAlign.CENTER)
                    .add(Midget.title()
                            .title(cutOutTitle())
                            .description(cutOutDescription())
                            .textColor("white")
                            .get())
                    .add(button)
                    .get();


            cutOut.get().openCutOut();
        }

    }


    protected abstract void editClicked(Record record);
    protected abstract String cutOutDescription();
    protected abstract String cutOutTitle();
    protected abstract Widget displayLine1(Record record);
    protected abstract Widget displayLine2(Record record);
    protected abstract Widget displayLine3(Record record);
    protected abstract Widget displayLine4(Record record);



}
