package com.wk.mobile.base.client.activity;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.data.*;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.DSOperationType;
import com.wk.mobile.base.client.*;
import com.wk.mobile.base.client.mvp.MyAbstractActivity;
import com.wk.mobile.base.client.place.BaseMobilePlace;
import com.wk.mobile.base.client.util.DS;
import com.wk.mobile.base.client.view.CRUDEditView;
import com.wk.mobile.base.client.widget.Confirm;
import com.wk.mobile.base.client.widget.Loader;
import gwt.material.design.client.base.HasError;
import gwt.material.design.client.ui.MaterialModal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: werner
 * Date: 15/11/21
 * Time: 3:59 PM
 */
public abstract class CRUDEditActivity extends MyAbstractActivity {

    protected BaseClientFactory clientFactory;
    protected CRUDEditView display;
    protected BaseMobilePlace place;
    protected Record record;

    public CRUDEditActivity(BaseMobilePlace place, BaseClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.place = place;
    }

    public void start(AcceptsOneWidget panel, EventBus eventBus) {

        display = getDisplay();

        resetDisplay();
        panel.setWidget(display);

        setViewHeading();

        BaseFAB.hide(clientFactory);

        bind();

        clearErrors();

        load(place.getToken(), new MobileDSCallback(place, clientFactory) {
            @Override
            public void onSuccess(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                if (dsResponse.getData() != null
                        && dsResponse.getData()[0] != null) {
                    record = dsResponse.getData()[0];
                } else {
                    record = new Record();
                }
                setDisplayFromRecord();
            }
        });


    }

    private void setViewHeading() {
        String text = calcNavTitle();
        clientFactory.getNavBrand().setText(text);
        clientFactory.getWaterfallTitle().setText(text);
    }

    private String calcNavTitle() {
        if (place.getToken() != null && !place.getToken().equals("")) {
            return getNavTitleForEdit();
        }
        else {
            return getNavTitleForAdd();
        }
    }



    private void clearErrors() {
        for (Iterator<String> it = display.getFormItems().keySet().iterator(); it.hasNext(); ) {
            String field = it.next();
            Widget widget = display.getFormItems().get(field);
            if (widget instanceof HasError) {
                ((HasError) widget).clearErrorOrSuccess();
            }
        }
    }


    private void load(String id, MobileDSCallback callback) {
        DS.fetch(getDataSource(), new Criteria("id", id), callback, clientFactory);
    }


    private void bind() {
        addHandlerRegistration(display.getCancelButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                goBackOrPrevPlace();
            }
        }));

        addHandlerRegistration(display.getSaveButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                save();
            }
        }));

        addHandlerRegistration(display.getDeleteButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                delete();
            }
        }));

        bindMore();
    }

    private void goBackOrPrevPlace() {
        if (place.getReturnPlace() != null) {
            clientFactory.getPlaceController().goTo(place.getReturnPlace());
        }
        else {
            History.back();
        }
    }


    private void delete() {

//        display.getDeleteTooltip().reconfigure();

        new Confirm().open(clientFactory.getConstants().areYouSure(), clientFactory.getConstants().thisActionCannotBeUndone(), new CloseHandler<MaterialModal>() {
            @Override
            public void onClose(CloseEvent<MaterialModal> event) {
                if (!event.isAutoClosed()) {

                    setRecordFromDisplay();

                    if (isNew(record)) {
                        goBackOrPrevPlace();
                    } else {
                        DS.remove(getDataSource(), record, goBackCallback(), null, clientFactory);
                    }
                }
            }
        }, clientFactory);

    }



    private void save() {
        setRecordFromDisplay();
        validate(new DSCallback() {
            @Override
            public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                Loader.hideProgress(clientFactory);
                if (dsResponse.getStatus() == RPCResponse.STATUS_SUCCESS) {
                    if (isNew(record)) {
                        DS.add(getDataSource(), record, goBackCallback(), null, clientFactory);
                    }
                    else {
                        DS.update(getDataSource(), record, goBackCallback(), new DSRequest(DSOperationType.UPDATE, updateOperationId()), clientFactory);
                    }
                }
                else {
                    processValidationErrors(dsResponse.getErrors());
                }
            }
        });
    }



    private MobileDSCallback goBackCallback() {
        return new MobileDSCallback(place, clientFactory) {
            @Override
            public void onSuccess(DSResponse dsResponse, Object data, DSRequest dsRequest) {

                entitiesChanged(new MobileCallback() {
                    @Override
                    public void onComplete() {

                        Loader.hideProgress(clientFactory);
                        goBackOrPrevPlace();
                    }
                });
            }
        };
    }

    private boolean isNew(Record record) {
        return record.getAttribute("id") == null || record.getAttributeAsInt("id") <= 0;
    }


    protected void validate(DSCallback callback) {
        clearErrors();
        if (record.getAttribute("customer_id") == null) {
            record.setAttribute("customer_id", Integer.toString(Session.getCustomerID()));
        }
        getDataSource().setShowPrompt(false);
        Loader.showProgress(clientFactory);
        getDataSource().validateData(record, callback, new DSRequest(DSOperationType.VALIDATE, validateOperationId()));
    }

    protected void processValidationErrors(Map<String, List<String>> errors) {
        if (errors != null) {
            for (Iterator<String> it = errors.keySet().iterator(); it.hasNext(); ) {
                String field = it.next();
                List<String> errorDescr = errors.get(field);
                if (errorDescr != null && errorDescr.size() > 0) {
                    showFieldValidationError(field, errorDescr.get(0));
                }
            }
        }
    }


    protected abstract CRUDEditView getDisplay();
    protected abstract DataSource getDataSource();
    protected abstract void resetDisplay();
    protected abstract String getNavTitleForEdit();
    protected abstract String getNavTitleForAdd();
    protected abstract void setDisplayFromRecord();
    protected abstract void setRecordFromDisplay();
    protected abstract void showFieldValidationError(String field, String error);
    protected abstract void bindMore();
    protected abstract void entitiesChanged(MobileCallback callback);
    protected abstract String updateOperationId();
    protected abstract String validateOperationId();






}
