package com.wk.mobile.base.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.data.*;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.util.Offline;
import com.wk.mobile.base.client.util.DS;
import com.wk.mobile.base.client.widget.Confirm;
import com.wk.mobile.base.client.widget.Loader;
import com.wk.mobile.base.client.widget.OneWidget;
import com.wk.mobile.base.client.widget.RetryDialog;
import gwt.material.design.client.constants.*;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.wk.mobile.base.client.widget.Midget.*;

/**
 * User: werner
 * Date: 15/11/24
 * Time: 4:20 PM
 */
public class LoginForm extends HTMLPanel {

    private BaseClientFactory clientFactory;
    private OneWidget viewWrapper;
    private PlaceHistoryHandler historyHandler;

    private FormPanel form;
    private MaterialTextBox emailTextBox;
    private MaterialTextBox passwordTextBox;
    private MaterialCheckBox keepMeLoggedInCheckBox;

    public LoginForm(String html, BaseClientFactory clientFactory, OneWidget viewWrapper, PlaceHistoryHandler historyHandler) {
        super(html);
        this.historyHandler = historyHandler;
        this.viewWrapper = viewWrapper;
        this.clientFactory = clientFactory;

        add(create());
        toast();
    }


    private void toast() {
        String toast = Window.Location.getParameter("toast");
        if (toast != null && !toast.equals("")) {
            if (toast.equalsIgnoreCase(clientFactory.getConstants().pleaseActivateYourAccount())) {
                new MaterialToast(link().text(clientFactory.getConstants().resend())
                        .addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                resendActivationEmail();
                            }
                        })
                        .get())
                        .toast(toast);
            }
            else {
                MaterialToast.fireToast(toast);
            }
        }
    }

    private void resendActivationEmail() {

        new Confirm().open(clientFactory.getConstants().resend(), clientFactory.getConstants().thisWillResendActivationEmail(), new CloseHandler<MaterialModal>() {
            @Override
            public void onClose(CloseEvent<MaterialModal> event) {
                if (!event.isAutoClosed()) {
                    DataSource dsUser = DataSource.get("user");
                    dsUser.setShowPrompt(false);
                    Loader.showLoading();
                    dsUser.fetchData(new Criteria("username", emailTextBox.getValue()), new DSCallback() {
                        @Override
                        public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                            Loader.hideLoading();
                            if (dsResponse.getStatus() == RPCResponse.STATUS_SUCCESS) {
                                MaterialToast.fireToast(clientFactory.getConstants().pleaseCheckYourMailBox());
                            }
                            else {
                                BaseMobileEntryPoint.showError(DS.constructErrorDebugString(clientFactory.getConstants().errorSendingPasswordReminder(), dsRequest, dsResponse), clientFactory);
                            }
                        }
                    }, new DSRequest(DSOperationType.FETCH, "resendActivationEmail"));
                }
            }
        }, clientFactory);

    }

    private Widget createForm() {
        form = new FormPanel();
        form.setMethod(FormPanel.METHOD_POST);
        form.setAction(clientFactory.getServerHost()+"/j_spring_security_check");

        form.add(panel()
                        .add(createEmailTextBox())
                        .add(createPasswordTextBox())
                        .get()
        );

        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                Offline.put("email", emailTextBox.getValue());
                Loader.hideLoading();
                Window.Location.assign("index.html");
            }
        });

        return form;
    }

    private MaterialTextBox createPasswordTextBox() {
        passwordTextBox = textBox()
                .type(InputType.PASSWORD)
                .placeHolder(clientFactory.getConstants().password())
                .name("j_password")
                .id("passwordTextBox")
                .get();
        return passwordTextBox;
    }

    private MaterialTextBox createEmailTextBox() {
        emailTextBox = textBox()
                .type(InputType.EMAIL)
                .placeHolder(clientFactory.getConstants().email())
                .name("j_username")
                .text(prePopulateEmail())
                .id("emailTextBox")
                .get();

        Object email = Offline.get("email");
        if (email != null) {
            emailTextBox.setValue(email.toString());
        }

        return emailTextBox;
    }

    private String prePopulateEmail() {
        String email = Window.Location.getParameter("email");
        if (email != null && !email.equals("")) {
            return email;
        }
        else {
            return "";
        }
    }

    private IsWidget create() {

        return row().addStyleName("login_row")
                .add(col().grid("s12 m6 l4").offset("m3 l4")
                        .add(panel().shadow(1).addStyleName("login_panel")
                                .add(panel().addStyleName("login_fieldPanel")
                                        .add(image().url("images/avatar.png").type(ImageType.CIRCLE).addStyleName("login_imgProfile z-depth-1").get())
                                        .add(createForm())
                                        .add(button().waves(WavesType.LIGHT).text(clientFactory.getConstants().login()).width("100%").id("loginButton")
                                                .addClickHandler(new ClickHandler() {
                                                    @Override
                                                    public void onClick(ClickEvent event) {
                                                        login();
                                                    }
                                                })
                                                .get())
                                        .add(row().addStyleName("login_rowRegister")
                                                .add(col().grid("s6 m6 l6")
                                                        .add(link().textAlign(TextAlign.LEFT).floatStyle(Style.Float.LEFT).text(clientFactory.getConstants().forgotPassword())
                                                                .addClickHandler(new ClickHandler() {
                                                                    @Override
                                                                    public void onClick(ClickEvent event) {
                                                                        forgotPassword();
                                                                    }
                                                                })
                                                                .get())
                                                        .get())
                                                .add(col().grid("s6 m6 l6")
                                                        .add(link().textAlign(TextAlign.RIGHT).floatStyle(Style.Float.RIGHT).text(clientFactory.getConstants().register())
                                                                .addClickHandler(new ClickHandler() {
                                                                    @Override
                                                                    public void onClick(ClickEvent event) {
                                                                        register();
                                                                    }
                                                                })
                                                                .get())
                                                        .get())
                                                .get())
                                        .add(row().addStyleName("login_rowAction")
                                                .add(col().grid("s12 m12 l12")
                                                        .add(createKeepMeLoggedInCheckBox())
                                                        .get())
                                                .get())
                                        .get())
                                .get())
                        .get())
                .get();
    }

    private MaterialCheckBox createKeepMeLoggedInCheckBox() {
        keepMeLoggedInCheckBox = checkBox().text(clientFactory.getConstants().keepMeLoggedIn()).get();
        Object keepMeLoggedIn = Offline.get("keepMeLoggedIn");
        if (keepMeLoggedIn != null) {
            keepMeLoggedInCheckBox.setValue(keepMeLoggedIn.equals("true"));
        }

        keepMeLoggedInCheckBox.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Offline.put("keepMeLoggedIn", keepMeLoggedInCheckBox.getValue());
            }
        });
        return keepMeLoggedInCheckBox;
    }

    private void forgotPassword() {
        clearErrors();
        if (emailTextBox.getValue().equals("")) {
            emailTextBox.setError(clientFactory.getConstants().enterYourRegisteredEmailAddress());
        }
        else {
            if (!Offline.isOffline()) {

                final Record record = setRecordFromDisplay();
                DataSource dsUser = DataSource.get("user");
                dsUser.setShowPrompt(false);
                Loader.showLoading();
                dsUser.performCustomOperation("forgotPassword", record, new DSCallback() {
                    @Override
                    public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                        Loader.hideLoading();
                        if (dsResponse.getStatus() == RPCResponse.STATUS_SUCCESS) {
                            if (dsResponse.getDataAsMap() != null && dsResponse.getDataAsMap().get("warning") != null) {
                                MaterialToast.fireToast(dsResponse.getDataAsMap().get("warning").toString());
                            }
                            else {
                                MaterialToast.fireToast(clientFactory.getConstants().pleaseCheckYourMailBox());
                            }
                        }
                        else if(dsResponse.getStatus() == RPCResponse.STATUS_OFFLINE) {
                            RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
                        }
                        else {
                            BaseMobileEntryPoint.showError(DS.constructErrorDebugString(clientFactory.getConstants().errorSendingPasswordReminder(), dsRequest, dsResponse), clientFactory);
                        }
                    }
                });
            }
            else {
                RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
            }


        }
    }

    private void login() {
        clearErrors();
        boolean validated = true;
        if (emailTextBox.getValue().equals("")) {
            emailTextBox.setError(clientFactory.getConstants().enterYourRegisteredEmailAddress());
            validated = false;
        }
        if (passwordTextBox.getValue().equals("")) {
            passwordTextBox.setError(clientFactory.getConstants().enterYourPassword());
            validated = false;
        }
        if (validated) {
            if (!Offline.isOffline()) {
                Loader.showLoading();
                form.submit();
            }
            else {
                RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
            }
        }
    }

    private void register() {
        clearErrors();
        if (!Offline.isOffline()) {

            final Record record = setRecordFromDisplay();
            validate(record, new DSCallback() {
                @Override
                public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                    Loader.hideLoading();
                    if (dsResponse.getStatus() == RPCResponse.STATUS_SUCCESS) {
                        DataSource dsUser = DataSource.get("user");
                        dsUser.setShowPrompt(false);
                        dsUser.addData(record, new DSCallback() {
                            @Override
                            public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                                if (dsResponse.getStatus() == RPCResponse.STATUS_SUCCESS) {
                                    MaterialToast.fireToast(clientFactory.getConstants().pleaseCheckYourMailBox());
                                } else {
                                    BaseMobileEntryPoint.showError(DS.constructErrorDebugString(clientFactory.getConstants().errorRegistering(), dsRequest, dsResponse), clientFactory);
                                }
                            }
                        }, new DSRequest(DSOperationType.ADD, "register"));
                    }
                    else if(dsResponse.getStatus() == RPCResponse.STATUS_OFFLINE) {
                        RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
                    }
                    else {
                        processValidationErrors(dsResponse.getErrors());
                    }
                }
            });
        }
        else {
            RetryDialog.open(clientFactory.getConstants().disconnectedFromServer(), clientFactory.getConstants().pleaseCheckYourInternetConnection(), null, clientFactory);
        }

    }

    private void clearErrors() {
        emailTextBox.clearErrorOrSuccess();
        passwordTextBox.clearErrorOrSuccess();
    }


    private Record setRecordFromDisplay() {
        Record record = new Record();
        record.setAttribute("username", emailTextBox.getValue());
        record.setAttribute("password", passwordTextBox.getValue());
        return record;
    }

    protected void validate(Record record, DSCallback callback) {
        DataSource dataSource = DataSource.get("user");
        dataSource.setShowPrompt(false);
        Loader.showLoading();
        dataSource.validateData(record, callback);
    }

    protected void processValidationErrors(Map<String, List<String>> errors) {
        if (errors != null) {
            for (Iterator<String> it = errors.keySet().iterator(); it.hasNext(); ) {
                String field = it.next();
                List<String> errorDescr = errors.get(field);
                if (errorDescr != null && errorDescr.size() > 0) {
                    if (field.equals("username")) {
                        emailTextBox.setError(errorDescr.get(0));
                    }
                    else if (field.equals("password")) {
                        passwordTextBox.setError(errorDescr.get(0));
                    }
                }
            }
        }
    }


}
