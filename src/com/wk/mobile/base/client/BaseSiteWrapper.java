package com.wk.mobile.base.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.util.Offline;
import com.wk.mobile.base.client.event.DashEvent;
import com.wk.mobile.base.client.widget.Midget;
import com.wk.mobile.base.client.widget.OneWidget;
import gwt.material.design.addins.client.ui.MaterialWaterfall;
import gwt.material.design.client.constants.*;
import gwt.material.design.client.ui.*;

import static com.wk.mobile.base.client.widget.Midget.*;

public abstract class BaseSiteWrapper extends HTMLPanel {

    protected BaseClientFactory clientFactory;

    public BaseSiteWrapper(OneWidget viewWrapper, BaseClientFactory clientFactory, String appName) {
        super("");
        addStyleName("siteWrapper");

        this.clientFactory = clientFactory;

        add(header()
                .add(createNavBar(appName))
                .add(createWaterfall())
                .get());
        add(createFAB());
        add(createSideNav());
        add(createProfileDropDown());
        add(viewWrapper);
//        add(createFooter());
    }


    private Widget createWaterfall() {
        final MaterialPanel titlePanel = panel().addStyleName("waterfall-title-panel")
                .add(createWaterfallTitle())
                .get();

/*
        final MaterialPanel actionPanel = panel().addStyleName("waterfall-action-panel")
                .add(createWaterfallAction()
                        .get())
                .get();
*/

        MaterialWaterfall waterfall = waterfall().addStyleName("waterfall-main")
                .add(row()
                        .add(col().grid("s12 m6 l6").addStyleName("waterfall-title-col")
                                .add(titlePanel)
                                .get())
/*
                        .add(col().grid("s12 m6 l6").addStyleName("waterfall-action-col")
                                .add(actionPanel)
                                .get())
*/
                        .get())
                .get();

        waterfall.setCallbacks(
                new Runnable() {
                    @Override
                    public void run() {
                        titlePanel.setOpacity(1);
                        clientFactory.getNavBrand().setOpacity(0);
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        titlePanel.setOpacity(0);
                        clientFactory.getNavBrand().setOpacity(1);
                    }
                });

        return waterfall;
    }

    private Midget<MaterialRow> createWaterfallAction() {
        return row()
                .add(col().floatStyle(Style.Float.RIGHT)
                        .add(button().text(clientFactory.getConstants().cancel()).get())
                        .get())
                .add(col().floatStyle(Style.Float.RIGHT)
                        .add(button().text(clientFactory.getConstants().save()).get())
                        .get());
    }

    private MaterialLabel createWaterfallTitle() {
        MaterialLabel label = label().id("headerLabel").get();
        label.addStyleName("waterfall-title");

        clientFactory.setWaterfalltitle(label);
        return label;
    }


    private Widget createProfileDropDown() {

        MaterialDropDown dropDown = dropDown("dropProfile")
                .add(link().text(clientFactory.getConstants().logout()).iconType(IconType.SETTINGS_POWER).waves(WavesType.LIGHT)
                        .addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                logout();
                            }
                        })
                        .get())
                .get();

        dropDown.setHover(true);
        return dropDown;
    }

    private void logout() {
        Offline.remove("auth_token");
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.setActionURL(clientFactory.getServerHost()+"/j_spring_security_logout");
        RPCManager.sendRequest(rpcRequest);
        Window.Location.assign("login.html");
    }


    private Widget createFAB() {
        MaterialFAB fab = fab().get();
        clientFactory.setFAB(fab);
        return fab;
    }



    private Widget createFooterContent() {
        return row()
                .add(col().grid("s12 m6 l6")
                        .add(title().textColor("white").title("Lorem ipsum").description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.").get())
                        .add(toolTip()
                                .add(button().text("Lorem").waves(WavesType.LIGHT).get())
                                .text("Lorem ipsum").get().asWidget())
                        .get())
                .add(col().grid("s12 m6 l6")
                        .add(title().textColor("white").title("Lorem ipsum").description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.").get())
                        .add(button().text("Lorem").waves(WavesType.LIGHT).get())
                        .get())
                .get();
    }


    private MaterialNavBar createNavBar(String appName) {
        MaterialNavBar navBar = navBar().activates("mysidebar").type(NavBarType.FIXED)
                .add(createNavBrand(appName))
//                .add(createNavSection())
                .get();
        clientFactory.setNavBar(navBar);
        return navBar;
    }

    private MaterialNavBrand createNavBrand(String appName) {
        MaterialNavBrand navBrand = navBrand().text(appName).get();
        navBrand.setOpacity(0);
        clientFactory.setNavBrand(navBrand);
        return navBrand;
    }


    private MaterialNavSection createNavSection() {
        return navSection().floatStyle(Style.Float.RIGHT).addStyleName("nav-section")
                .add(link().iconType(IconType.CLOSE).waves(WavesType.LIGHT).get())
                .add(link().iconType(IconType.CHECK).waves(WavesType.LIGHT).get())
                .add(link().iconType(IconType.MORE_VERT).waves(WavesType.LIGHT).get())
                .get();
    }


    protected MaterialSideNav createSideNav() {

        Midget<MaterialSideNav> midgetSideNav = sideNav().id("mysidebar").type(SideNavType.OPEN).width("250")
                .add(sideProfile().backgroundColor("blue").addStyleName("side-profile")
//                        .url("images/profileBackdrop.png")
                        .add(image().url("images/avatar.png").get())
//                        .add(label().text("Werner Kok").textColor("white").get())
                        .add(link().text(Session.getLoginName()).fontSize(0.8, Style.Unit.EM).activates("dropProfile").iconType(IconType.ARROW_DROP_DOWN).iconPosition(IconPosition.RIGHT).textColor("white").get())
                        .get())
                .add(link().iconType(IconType.DASHBOARD).iconPosition(IconPosition.LEFT)
                        .text(clientFactory.getConstants().dashboard()).textColor("blue")
                        .waves(WavesType.LIGHT)
                        .addClickHandler(new ClickHandler() {
                            public void onClick(ClickEvent event) {
                                DashEvent.fire(clientFactory.getEventBus());
                                clientFactory.getSideNav().hide(clientFactory.getSideNav().getElement());
                            }
                        })
                        .get());

        addSideNavExtras(midgetSideNav);

        MaterialSideNav sideNav = midgetSideNav.get();
        clientFactory.setSideNav(sideNav);
        return sideNav;
    }

    protected abstract void addSideNavExtras(Midget<MaterialSideNav> midgetSideNav);

}
