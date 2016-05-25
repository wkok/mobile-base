package com.wk.mobile.base.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.wk.mobile.base.client.BaseClientFactory;
import com.wk.mobile.base.client.widget.FormItemsContainer;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.Position;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialRow;

import java.util.Map;

import static com.wk.mobile.base.client.widget.Midget.*;

public abstract class CRUDEditViewImpl extends MaterialContainer implements CRUDEditView {

    protected BaseClientFactory clientFactory;

    private FormItemsContainer panel;
    private MaterialIcon cancelButton;
    private MaterialIcon saveButton;
    private MaterialIcon deleteButton;

    public CRUDEditViewImpl(BaseClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        add(createContentHeader());
        add(createContentBody());
        add(createContentFooter());
    }



    private HTMLPanel createContentBody() {
        panel = new FormItemsContainer();
        panel.getPanel().addStyleName("contentBody");

        createFormItems(panel);

        return panel.getPanel();
    }



    private MaterialRow createContentHeader() {
        return row().addStyleName("contentHeader")
                .add(col().floatStyle(Style.Float.RIGHT)
                        .add(createSaveButton())
                        .get())
                .add(col().floatStyle(Style.Float.RIGHT)
                        .add(createCancelButton())
                        .get())
                .get();
    }

    private MaterialRow createContentHeaderWithTooltips() {
        return row().addStyleName("contentHeader")
                .add(col().floatStyle(Style.Float.RIGHT)
                        .add(toolTip()
                                .add(createSaveButton())
                                .position(Position.BOTTOM).text(clientFactory.getConstants().save())
                                .get().asWidget())
                        .get())
                .add(col().floatStyle(Style.Float.RIGHT)
                        .add(toolTip()
                                .add(createCancelButton())
                                .position(Position.BOTTOM).text(clientFactory.getConstants().cancel())
                                .get().asWidget())
                        .get())
                .get();
    }

    private MaterialRow createContentFooter() {
        return row().addStyleName("contentFooter")
                .add(col().floatStyle(Style.Float.LEFT)
                        .add(createDeleteButton())
                        .get())
                .get();
    }

    private MaterialRow createContentFooterWithTooltips() {
        return row().addStyleName("contentFooter")
                .add(col().floatStyle(Style.Float.LEFT)
                        .add(toolTip()
                                .add(createDeleteButton())
                                .position(Position.TOP).text(clientFactory.getConstants().delete())
                                .get().asWidget())
                        .get())
                .get();
    }



    private MaterialIcon createSaveButton() {
        saveButton = icon().iconType(IconType.CHECK).iconColor("green").waves(WavesType.LIGHT).get();
        return saveButton;
    }

    private MaterialIcon createCancelButton() {
        cancelButton = icon().iconType(IconType.CLOSE).iconColor("black").waves(WavesType.LIGHT).get();
        return cancelButton;
    }

    private MaterialIcon createDeleteButton() {
        deleteButton = icon().iconType(IconType.DELETE).iconColor("red").waves(WavesType.LIGHT).get();
        return deleteButton;
    }



    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }

    public HasClickHandlers getSaveButton() {
        return saveButton;
    }

    @Override
    public HasClickHandlers getDeleteButton() {
        return deleteButton;
    }

    public Map<String, Widget> getFormItems() {
        return panel.getItems();
    }

    protected abstract void createFormItems(FormItemsContainer panel);

}
