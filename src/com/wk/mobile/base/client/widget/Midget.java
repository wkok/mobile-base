package com.wk.mobile.base.client.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import gwt.material.design.addins.client.ui.MaterialCutOut;
import gwt.material.design.addins.client.ui.MaterialSideProfile;
import gwt.material.design.addins.client.ui.MaterialWaterfall;
import gwt.material.design.client.base.*;
import gwt.material.design.client.constants.*;
import gwt.material.design.client.ui.*;

/**
 * User: werner
 * Date: 15/11/16
 * Time: 4:33 PM
 */
public class Midget<T> {

    private T widget;

    public Midget(T widget) {
        this.widget = widget;
    }

    public T get() {
        return widget;
    }

    public static Midget<MaterialRow> row() {
        return new Midget(new MaterialRow());
    }

    public static Midget<MaterialColumn> col() {
        return new Midget(new MaterialColumn());
    }

    public static Midget<MaterialButton> button() {
        return new Midget(new MaterialButton());
    }

    public static Midget<MaterialTitle> title() {
        return new Midget(new MaterialTitle());
    }

    public static Midget<MaterialFAB> fab() {
        return new Midget(new MaterialFAB());
    }

    public static Midget<MaterialFABList> fabList() {
        return new Midget(new MaterialFABList());
    }

    public static Midget<MaterialFooter> footer() {
        return new Midget(new MaterialFooter());
    }

    public static Midget<MaterialFooterCopyright> copyright() {
        return new Midget(new MaterialFooterCopyright());
    }

    public static Midget<MaterialLabel> label() {
        return new Midget(new MaterialLabel());
    }

    public static Midget<MaterialContainer> container() {
        return new Midget(new MaterialContainer());
    }

    public static Midget<MaterialCollapsible> collapsible() {
        return new Midget(new MaterialCollapsible());
    }

    public static Midget<MaterialCollection> collection() {
        return new Midget(new MaterialCollection());
    }

    public static Midget<MaterialCollectionItem> collectionItem() {
        return new Midget(new MaterialCollectionItem());
    }

    public static Midget<MaterialCollapsibleItem> collapsibleItem() {
        return new Midget(new MaterialCollapsibleItem());
    }

    public static Midget<MaterialIcon> icon() {
        return new Midget(new MaterialIcon());
    }

    public static Midget<MaterialCollectionSecondary> collectionSecondary() {
        return new Midget(new MaterialCollectionSecondary());
    }

    public static Midget<MaterialLink> link() {
        return new Midget(new MaterialLink());
    }

    public static Midget<MaterialTooltip> toolTip() {
        return new Midget(new MaterialTooltip());
    }

    public static Midget<MaterialNavBrand> navBrand() {
        return new Midget(new MaterialNavBrand());
    }

    public static Midget<MaterialTopNav> topNav() {
        return new Midget(new MaterialTopNav());
    }

    public static Midget<MaterialSideNav> sideNav() {
        return new Midget(new MaterialSideNav());
    }

    public static Midget<MaterialNavSection> navSection() {
        return new Midget(new MaterialNavSection());
    }

    public static Midget<MaterialNavBar> navBar() {
        return new Midget(new MaterialNavBar());
    }

    public static Midget<MaterialCollapsibleHeader> collapsibleHeader() {
        return new Midget(new MaterialCollapsibleHeader());
    }

    public static Midget<MaterialSplashScreen> splashScreen() {
        return new Midget(new MaterialSplashScreen());
    }

    public static Midget<MaterialSideProfile> sideProfile() {
        return new Midget(new MaterialSideProfile());
    }

    public static Midget<MaterialWaterfall> waterfall() {
        return new Midget(new MaterialWaterfall());
    }

    public static Midget<MaterialCollapsibleBody> collapsibleBody() {
        return new Midget(new MaterialCollapsibleBody());
    }

    public static Midget<MaterialImage> image() {
        return new Midget(new MaterialImage());
    }

    public static Midget<MaterialDropDown> dropDown() {
        return new Midget(new MaterialDropDown());
    }

    public static Midget<MaterialDropDown> dropDown(String activator) {
        return new Midget(new MaterialDropDown(activator));
    }

    public static Midget<MaterialTextBox> textBox() {
        return new Midget(new MaterialTextBox());
    }
    public static Midget<MaterialCutOut> cutOut() {
        return new Midget(new MaterialCutOut());
    }

    public static Midget<MaterialFloatBox> floatBox() {
        return new Midget(new MaterialFloatBox());
    }

    public static Midget<MaterialDoubleBox> doubleBox() {
        return new Midget(new MaterialDoubleBox());
    }

    public static Midget<MaterialTextArea> textArea() {
        return new Midget(new MaterialTextArea());
    }

    public static Midget<MaterialListBox> listBox() {
        return new Midget(new MaterialListBox());
    }

    public static Midget<MaterialDatePicker> datePicker() {
        return new Midget(new MaterialDatePicker());
    }

    public static Midget<MaterialPanel> panel() {
        return new Midget(new MaterialPanel());
    }
    public static Midget<MaterialCheckBox> checkBox() {
        return new Midget(new MaterialCheckBox());
    }

    public static Midget<MaterialModal> modal() {
        return new Midget(new MaterialModal());
    }

    public static Midget<MaterialModalContent> modalContent() {
        return new Midget(new MaterialModalContent());
    }

    public static Midget<MaterialModalFooter> modalFooter() {
        return new Midget(new MaterialModalFooter());
    }
    public static Midget<MaterialHeader> header() {
        return new Midget(new MaterialHeader());
    }


    public Midget<T> add(Widget child) {
        T widget = get();
        if (widget instanceof HasWidgets) {
            ((HasWidgets)widget).add(child);
            return new Midget(widget);
        }
        return this;
    }


    public Midget<T> iconPosition(IconPosition position) {
        T widget = get();
        if (widget instanceof HasIcon) {
            ((HasIcon)widget).setIconPosition(position);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> shadow(int shadow) {
        T widget = get();
        if (widget instanceof HasShadow) {
            ((HasShadow)widget).setShadow(shadow);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> type(InputType type) {
        T widget = get();
        if (widget instanceof MaterialTextBox) {
            ((MaterialTextBox)widget).setType(type);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> type(ButtonType type) {
        T widget = get();
        if (widget instanceof MaterialButton) {
            ((MaterialButton)widget).setType(type);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> name(String name) {
        T widget = get();
        if (widget instanceof HasName) {
            ((HasName)widget).setName(name);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> dismissable(boolean dismissable) {
        T widget = get();
        if (widget instanceof HasDismissable) {
            ((HasDismissable)widget).setDismissable(dismissable);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> inDuration(int inDuration) {
        T widget = get();
        if (widget instanceof HasTransition) {
            ((HasTransition)widget).setInDuration(inDuration);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> enabled(boolean enabled) {
        T widget = get();
        if (widget instanceof MaterialWidget) {
            ((MaterialWidget)widget).setEnabled(enabled);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> outDuration(int outDuration) {
        T widget = get();
        if (widget instanceof HasTransition) {
            ((HasTransition)widget).setOutDuration(outDuration);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> header(String header) {
        T widget = get();
        if (widget instanceof MaterialCollection) {
            ((MaterialCollection)widget).setHeader(header);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> type(ImageType type) {
        T widget = get();
        if (widget instanceof HasType) {
            ((HasType)widget).setType(type);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> type(ModalType type) {
        T widget = get();
        if (widget instanceof HasType) {
            ((HasType)widget).setType(type);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> offset(String offset) {
        T widget = get();
        if (widget instanceof HasGrid) {
            ((HasGrid)widget).setOffset(offset);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> addClickHandler(ClickHandler handler) {
        T widget = get();
        if (widget instanceof AbstractButton) {
            ((AbstractButton)widget).addClickHandler(handler);
            return new Midget(widget);
        }
        return this;
    }


    public Midget<T> showOn(ShowOn showOn) {
        T widget = get();
        if (widget instanceof HasShowOn) {
            ((HasShowOn)widget).setShowOn(showOn);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> url(String url) {
        T widget = get();
        if (widget instanceof HasImage) {
            ((HasImage)widget).setUrl(url);
            return new Midget(widget);
        }
        else if (widget instanceof Image) {
            ((Image)widget).setUrl(url);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> hideOn(HideOn hideOn) {
        T widget = get();
        if (widget instanceof HasHideOn) {
            ((HasHideOn)widget).setHideOn(hideOn);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> position(Position position) {
        T widget = get();
        if (widget instanceof HasPosition) {
            ((HasPosition)widget).setPosition(position);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> floatStyle(Style.Float floatStyle) {
        T widget = get();
        if (widget instanceof HasFloat) {
            ((HasFloat)widget).setFloat(floatStyle);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> textColor(String color) {
        T widget = get();
        if (widget instanceof HasColors) {
            ((HasColors)widget).setTextColor(color);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> width(String width) {
        T widget = get();
        if (widget instanceof UIObject) {
            ((UIObject)widget).setWidth(width);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> title(String title) {
        T widget = get();
        if (widget instanceof UIObject) {
            ((UIObject)widget).setTitle(title);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> placeHolder(String placeHolder) {
        T widget = get();
        if (widget instanceof HasPlaceholder) {
            ((HasPlaceholder)widget).setPlaceholder(placeHolder);
            return new Midget(widget);
        }
        else if (widget instanceof MaterialDatePicker) {
            ((MaterialDatePicker)widget).setPlaceholder(placeHolder);
            return new Midget(widget);
        }
        else if (widget instanceof MaterialListBox) {
            ((MaterialListBox)widget).setPlaceholder(placeHolder);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> grid(String grid) {
        T widget = get();
        if (widget instanceof HasGrid) {
            ((HasGrid)widget).setGrid(grid);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> selectionType(MaterialDatePicker.MaterialDatePickerType type) {
        T widget = get();
        if (widget instanceof MaterialDatePicker) {
            ((MaterialDatePicker)widget).setSelectionType(type);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> size(ButtonSize size) {
        T widget = get();
        if (widget instanceof AbstractButton) {
            ((AbstractButton)widget).setSize(size);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> description(String description) {
        T widget = get();
        if (widget instanceof HasTitle) {
            ((HasTitle)widget).setDescription(description);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> backgroundColor(String color) {
        T widget = get();
        if (widget instanceof HasColors) {
            ((HasColors)widget).setBackgroundColor(color);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> target(Widget target) {
        T widget = get();
        if (widget instanceof MaterialCutOut) {
            ((MaterialCutOut)widget).setTarget(target);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> circle(boolean circle) {
        T widget = get();
        if (widget instanceof MaterialCutOut) {
            ((MaterialCutOut)widget).setCircle(circle);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> opacity(double opacity) {
        T widget = get();
        if (widget instanceof MaterialCutOut) {
            ((MaterialCutOut)widget).setOpacity(opacity);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> addStyleName(String style) {
        T widget = get();
        if (widget instanceof MaterialRow) {
            ((MaterialRow)widget).addStyleName(style);
            return new Midget(widget);
        }
        else if (widget instanceof MaterialPanel) {
            ((MaterialPanel)widget).addStyleName(style);
            return new Midget(widget);
        }
        else if (widget instanceof UIObject) {
            ((UIObject)widget).addStyleName(style);
            return new Midget(widget);
        }
        return this;
    }


    public Midget<T> type(CssType type) {
        T widget = get();
        if (widget instanceof MaterialCollapsible) {
            ((MaterialCollapsible)widget).setType((CollapsibleType)type);
            return new Midget(widget);
        }
        else if (widget instanceof MaterialNavBar) {
            ((MaterialNavBar) widget).setType((NavBarType) type);
            return new Midget(widget);
        }
        else if (widget instanceof HasType) {
            ((HasType)widget).setType(type);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> id(String id) {
        T widget = get();
        if (widget instanceof HasId) {
            ((HasId)widget).setId(id);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> closeOnClick(boolean closeOnClick) {
        T widget = get();
        if (widget instanceof MaterialSideNav) {
            ((MaterialSideNav)widget).setCloseOnClick(closeOnClick);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> activates(String activates) {
        T widget = get();
        if (widget instanceof HasActivates) {
            ((HasActivates)widget).setActivates(activates);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> textAlign(TextAlign align) {
        T widget = get();
        if (widget instanceof HasTextAlign) {
            ((HasTextAlign)widget).setTextAlign(align);
            return new Midget(widget);
        }
        return this;
    }

    public Midget<T> text(String text) {
        T widget = get();
        if (widget instanceof AbstractButton) {
            ((AbstractButton)widget).setText(text);
        }
        else if (widget instanceof HasText) {
            ((HasText)widget).setText(text);
        }
        return new Midget(widget);
    }

    public Midget<T> waves(WavesType wavesType) {
        T widget = get();
        if (widget instanceof HasWaves) {
            ((HasWaves)widget).setWaves(wavesType);
        }
        return new Midget(widget);
    }

    public Midget<T> height(String height) {
        T widget = get();
        if (widget instanceof UIObject) {
            ((UIObject)widget).setHeight(height);
        }
        return new Midget(widget);
    }

    public Midget<T> iconType(IconType iconType) {
        T widget = get();
        if (widget instanceof HasIcon) {
            ((HasIcon)widget).setIconType(iconType);
        }
        return new Midget(widget);
    }

    public Midget<T> iconColor(String color) {
        T widget = get();
        if (widget instanceof HasIcon) {
            ((HasIcon)widget).setIconColor(color);
        }
        return new Midget(widget);
    }

    public Midget<T> fontSize(double size, Style.Unit unit) {
        T widget = get();
        if (widget instanceof HasFontSize) {
            ((HasFontSize)widget).setFontSize(size, unit);
        }
        return new Midget(widget);
    }

    public Midget<T> fontSize(String size) {
        T widget = get();
        if (widget instanceof MaterialWidget) {
            ((MaterialWidget)widget).setFontSize(size);
        }
        return new Midget(widget);
    }



}
