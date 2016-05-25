package com.wk.mobile.base.client.mvp;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import java.util.LinkedList;

/**
 * User: werner
 * Date: 15/11/21
 * Time: 3:56 PM
 */
public abstract class MyAbstractActivity extends AbstractActivity {

    private LinkedList<HandlerRegistration> oldHandlers;
    private LinkedList<com.google.web.bindery.event.shared.HandlerRegistration> handlers;

    public MyAbstractActivity() {
        oldHandlers = new LinkedList<HandlerRegistration>();
        handlers = new LinkedList<com.google.web.bindery.event.shared.HandlerRegistration>();
    }

    /**
     * add a {@link HandlerRegistration} to the handler collection
     *
     * @param handlerRegistration a {@link HandlerRegistration} object.
     */
    protected void addHandlerRegistration(com.google.web.bindery.event.shared.HandlerRegistration handlerRegistration) {
        handlers.add(handlerRegistration);
    }

    /**
     * add a {@link HandlerRegistration} to the handler collection
     *
     * @param handlerRegistration a {@link HandlerRegistration} object.
     */
    protected void addHandlerRegistration(HandlerRegistration handlerRegistration) {
        oldHandlers.add(handlerRegistration);
    }

    /**
     * {@inheritDoc}
     *
     * onStop is overriden to automatically clear all {@link HandlerRegistration}
     */
    @Override
    public void onStop() {
        super.onStop();

        cancelAllHandlerRegistrations();
    }

    /**
     * Remove all collected oldHandlers, and remove them from the collection
     */
    protected void cancelAllHandlerRegistrations() {
        for (HandlerRegistration hr : oldHandlers) {
            hr.removeHandler();
        }
        oldHandlers.clear();

        for (com.google.web.bindery.event.shared.HandlerRegistration hr : handlers) {
            hr.removeHandler();
        }
        handlers.clear();
    }

    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        start(panel, (com.google.web.bindery.event.shared.EventBus) eventBus);
    }

    public void start(AcceptsOneWidget panel, com.google.web.bindery.event.shared.EventBus eventBus) {
    }
}