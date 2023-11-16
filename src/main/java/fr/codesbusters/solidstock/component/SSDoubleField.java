package fr.codesbusters.solidstock.component;

import com.sun.javafx.scene.control.DoubleField;
import fr.codesbusters.solidstock.controls.BoundDoubleField;
import fr.codesbusters.solidstock.skin.SSDoubleFieldSkin;
import io.github.palexdev.materialfx.beans.properties.styleable.StyleableBooleanProperty;
import io.github.palexdev.materialfx.beans.properties.styleable.StyleableDoubleProperty;
import io.github.palexdev.materialfx.beans.properties.styleable.StyleableIntegerProperty;
import io.github.palexdev.materialfx.beans.properties.styleable.StyleableObjectProperty;
import io.github.palexdev.materialfx.controls.BoundTextField;
import io.github.palexdev.materialfx.controls.MFXContextMenu;
import io.github.palexdev.materialfx.controls.base.MFXMenuControl;
import io.github.palexdev.materialfx.controls.base.Themable;
import io.github.palexdev.materialfx.css.themes.Stylesheets;
import io.github.palexdev.materialfx.css.themes.Theme;
import io.github.palexdev.materialfx.enums.FloatMode;
import io.github.palexdev.materialfx.utils.StyleablePropertiesUtils;
import io.github.palexdev.materialfx.validation.MFXValidator;
import io.github.palexdev.materialfx.validation.Validated;
import javafx.beans.property.*;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.paint.Color;

import java.util.List;

public class SSDoubleField extends DoubleField implements Validated, MFXMenuControl, Themable {
    public static final Color DEFAULT_TEXT_COLOR = Color.rgb(0, 0, 0, 0.87);
    private static final PseudoClass FLOATING_PSEUDO_CLASS = PseudoClass.getPseudoClass("floating");
    protected final BoundDoubleField boundField;
    protected final BooleanProperty floating = new SimpleBooleanProperty() {
        @Override
        public void unbind() {
        }
    };
    protected final MFXValidator validator = new MFXValidator();
    //================================================================================
    // Properties
    //================================================================================
    private final String STYLE_CLASS = "mfx-text-field";
    private final BooleanProperty selectable = new SimpleBooleanProperty(true);
    private final ObjectProperty<Node> leadingIcon = new SimpleObjectProperty<>();
    private final ObjectProperty<Node> trailingIcon = new SimpleObjectProperty<>();
    private final StringProperty floatingText = new SimpleStringProperty();
    private final StringProperty measureUnit = new SimpleStringProperty("");
    //================================================================================
    // Styleable Properties
    //================================================================================
    private final StyleableBooleanProperty allowEdit = new StyleableBooleanProperty(
            StyleableProperties.EDITABLE,
            this,
            "allowEdit",
            true
    );
    private final StyleableBooleanProperty animated = new StyleableBooleanProperty(
            StyleableProperties.ANIMATED,
            this,
            "animated",
            true
    );
    private final StyleableDoubleProperty borderGap = new StyleableDoubleProperty(
            StyleableProperties.BORDER_GAP,
            this,
            "borderGap",
            10.0
    );
    private final StyleableBooleanProperty caretVisible = new StyleableBooleanProperty(
            StyleableProperties.CARET_VISIBLE,
            this,
            "caretAnimated",
            true
    );
    private final StyleableObjectProperty<FloatMode> floatMode = new StyleableObjectProperty<>(
            StyleableProperties.FLOAT_MODE,
            this,
            "floatMode",
            FloatMode.INLINE
    );

    //================================================================================
    // Static Methods
    //================================================================================
    private final StyleableDoubleProperty floatingTextGap = new StyleableDoubleProperty(
            StyleableProperties.FLOATING_TEXT_GAP,
            this,
            "gap",
            5.0
    );
    private final StyleableDoubleProperty graphicTextGap = new StyleableDoubleProperty(
            StyleableProperties.GRAPHIC_TEXT_GAP,
            this,
            "graphicTextGap",
            10.0
    );
    private final StyleableDoubleProperty measureUnitGap = new StyleableDoubleProperty(
            StyleableProperties.MEASURE_UNIT_GAP,
            this,
            "measureUnitGap",
            5.0
    );
    private final StyleableBooleanProperty scaleOnAbove = new StyleableBooleanProperty(
            StyleableProperties.SCALE_ON_ABOVE,
            this,
            "scaleOnAbove",
            false
    );
    private final StyleableObjectProperty<Color> textFill = new StyleableObjectProperty<>(
            StyleableProperties.TEXT_FILL,
            this,
            "textFill",
            DEFAULT_TEXT_COLOR
    );
    private final StyleableIntegerProperty textLimit = new StyleableIntegerProperty(
            StyleableProperties.TEXT_LIMIT,
            this,
            "textLimit",
            -1
    );
    protected MFXContextMenu contextMenu;

    //================================================================================
    // Constructors
    //================================================================================
    public SSDoubleField() {
        this(0.0);
    }

    public SSDoubleField(Double text) {
        this(text, "");
    }

    public SSDoubleField(Double text, String promptText) {
        this(text, promptText, "");
    }

    public SSDoubleField(Double text, String promptText, String floatingText) {
        super();
        boundField = new BoundDoubleField(this);
        setPromptText(promptText);
        setFloatingText(floatingText);
        initialize();
    }


    /**
     * Calls  with empty promptText.
     */
    public static SSDoubleField asLabel(Double text) {
        return asLabel(text, "");
    }

    /**
     * Calls  with empty floatingText.
     */
    public static SSDoubleField asLabel(Double text, String promptText) {
        return asLabel(text, promptText, "");
    }

    /**
     * Creates a text field that is not editable nor selectable to act just like a label.
     */
    public static SSDoubleField asLabel(Double text, String promptText, String floatingText) {
        SSDoubleField textField = new SSDoubleField(text, promptText, floatingText);
        textField.setEditable(false);
        textField.setSelectable(false);
        return textField;
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.cssMetaDataList;
    }

    //================================================================================
    // Methods
    //================================================================================
    private void initialize() {
        getStyleClass().setAll(STYLE_CLASS);
        setPrefColumnCount(6);
        setFocusTraversable(false);
        floating.addListener(invalidated -> pseudoClassStateChanged(FLOATING_PSEUDO_CLASS, floating.get()));
        allowEditProperty().bindBidirectional(editableProperty());

        addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        sceneBuilderIntegration();
    }


    //================================================================================
    // Overridden Methods
    //================================================================================
    @Override
    public Parent toParent() {
        return this;
    }

    @Override
    public Theme getTheme() {
        return Stylesheets.TEXT_FIELD;
    }

    @Override
    public MFXContextMenu getMFXContextMenu() {
        return contextMenu;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SSDoubleFieldSkin(this, boundField);
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return SSDoubleField.getClassCssMetaData();
    }

    //================================================================================
    // Workaround Methods
    //================================================================================

    public boolean delegateIsFocused() {
        return boundField.isFocused();
    }

    /**
     * Specifies whether the {@link BoundTextField} is focused.
     */
    public ReadOnlyBooleanProperty delegateFocusedProperty() {
        return boundField.focusedProperty();
    }

    public void delegateSetFocusTraversable(boolean value) {
        boundField.setFocusTraversable(value);
    }

    /**
     * Specifies whether the {@link BoundTextField} it focus traversable.
     */
    public BooleanProperty delegateFocusTraversableProperty() {
        return boundField.focusTraversableProperty();
    }

    public boolean delegateIsFocusTraversable() {
        return boundField.isFocusTraversable();
    }

    //================================================================================
    // Validation
    //================================================================================
    @Override
    public MFXValidator getValidator() {
        return validator;
    }

    //================================================================================
    // Getters/Setters
    //================================================================================
    public boolean isSelectable() {
        return selectable.get();
    }

    public void setSelectable(boolean selectable) {
        this.selectable.set(selectable);
    }

    /**
     * Specifies whether selection is allowed.
     */
    public BooleanProperty selectableProperty() {
        return selectable;
    }

    public Node getLeadingIcon() {
        return leadingIcon.get();
    }

    public void setLeadingIcon(Node leadingIcon) {
        this.leadingIcon.set(leadingIcon);
    }

    /**
     * Specifies the icon placed before the input field.
     */
    public ObjectProperty<Node> leadingIconProperty() {
        return leadingIcon;
    }

    public Node getTrailingIcon() {
        return trailingIcon.get();
    }

    public void setTrailingIcon(Node trailingIcon) {
        this.trailingIcon.set(trailingIcon);
    }

    /**
     * Specifies the icon placed after the input field.
     */
    public ObjectProperty<Node> trailingIconProperty() {
        return trailingIcon;
    }

    public String getFloatingText() {
        return floatingText.get();
    }

    public void setFloatingText(String floatingText) {
        this.floatingText.set(floatingText);
    }

    /**
     * Specifies the text of the floating text node.
     */
    public StringProperty floatingTextProperty() {
        return floatingText;
    }

    public boolean isFloating() {
        return floating.get();
    }

    /**
     * Specifies if the floating text node is currently floating or not.
     */
    public BooleanProperty floatingProperty() {
        return floating;
    }

    public String getMeasureUnit() {
        return measureUnit.get();
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit.set(measureUnit);
    }

    /**
     * Specifies the unit of measure of the field.
     * <p></p>
     * This is useful of course when dealing with numeric fields that represent for example:
     * weight, volume, length and so on...
     */
    public StringProperty measureUnitProperty() {
        return measureUnit;
    }

    public boolean isAllowEdit() {
        return allowEdit.get();
    }

    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit.set(allowEdit);
    }

    /**
     * Specifies whether the field is editable.
     * <p>
     * This property is bound bidirectionally to {@link TextField#editableProperty()},
     * it's here just to be set via CSS.
     */
    public StyleableBooleanProperty allowEditProperty() {
        return allowEdit;
    }

    public boolean isAnimated() {
        return animated.get();
    }

    public void setAnimated(boolean animated) {
        this.animated.set(animated);
    }

    /**
     * Specifies whether the floating text positioning is animated.
     */
    public StyleableBooleanProperty animatedProperty() {
        return animated;
    }

    public double getBorderGap() {
        return borderGap.get();
    }

    public void setBorderGap(double borderGap) {
        this.borderGap.set(borderGap);
    }

    /**
     * For {@link FloatMode#BORDER} and {@link FloatMode#ABOVE} modes, this specifies the distance from
     * the control's x origin (padding not included).
     */
    public StyleableDoubleProperty borderGapProperty() {
        return borderGap;
    }

    public boolean getCaretVisible() {
        return caretVisible.get();
    }

    public void setCaretVisible(boolean caretVisible) {
        this.caretVisible.set(caretVisible);
    }

    /**
     * Specifies whether the caret should be visible.
     */
    public StyleableBooleanProperty caretVisibleProperty() {
        return caretVisible;
    }

    public FloatMode getFloatMode() {
        return floatMode.get();
    }

    public void setFloatMode(FloatMode floatMode) {
        this.floatMode.set(floatMode);
    }

    /**
     * Specifies how the floating text is positioned when floating.
     */
    public StyleableObjectProperty<FloatMode> floatModeProperty() {
        return floatMode;
    }

    public double getFloatingTextGap() {
        return floatingTextGap.get();
    }

    public void setFloatingTextGap(double floatingTextGap) {
        this.floatingTextGap.set(floatingTextGap);
    }

    /**
     * For {@link FloatMode#INLINE} mode, this specifies the gap between
     * the floating text node and the input field node.
     */
    public StyleableDoubleProperty floatingTextGapProperty() {
        return floatingTextGap;
    }

    public double getGraphicTextGap() {
        return graphicTextGap.get();
    }

    public void setGraphicTextGap(double graphicTextGap) {
        this.graphicTextGap.set(graphicTextGap);
    }

    /**
     * Specifies the gap between the input field and the icons.
     */
    public StyleableDoubleProperty graphicTextGapProperty() {
        return graphicTextGap;
    }

    public boolean scaleOnAbove() {
        return scaleOnAbove.get();
    }

    /**
     * Specifies whether the floating text node should be scaled or not when
     * the float mode is set to {@link FloatMode#ABOVE}.
     */
    public StyleableBooleanProperty scaleOnAboveProperty() {
        return scaleOnAbove;
    }

    public void setScaleOnAbove(boolean scaleOnAbove) {
        this.scaleOnAbove.set(scaleOnAbove);
    }

    public double getMeasureUnitGap() {
        return measureUnitGap.get();
    }

    public void setMeasureUnitGap(double measureUnitGap) {
        this.measureUnitGap.set(measureUnitGap);
    }

    /**
     * Specifies the gap between the field and the measure unit label.
     */
    public StyleableDoubleProperty measureUnitGapProperty() {
        return measureUnitGap;
    }

    public Color getTextFill() {
        return textFill.get();
    }

    public void setTextFill(Color textFill) {
        this.textFill.set(textFill);
    }

    /**
     * Specifies the text color.
     */
    public StyleableObjectProperty<Color> textFillProperty() {
        return textFill;
    }

    public int getTextLimit() {
        return textLimit.get();
    }

    public void setTextLimit(int textLimit) {
        this.textLimit.set(textLimit);
    }

    /**
     * Specifies the maximum number of characters the field's text can have.
     */
    public StyleableIntegerProperty textLimitProperty() {
        return textLimit;
    }

    //================================================================================
    // CSSMetaData
    //================================================================================
    private static class StyleableProperties {
        private static final StyleablePropertyFactory<SSDoubleField> FACTORY = new StyleablePropertyFactory<>(DoubleField.getClassCssMetaData());
        private static final List<CssMetaData<? extends Styleable, ?>> cssMetaDataList;

        private static final CssMetaData<SSDoubleField, Boolean> ANIMATED =
                FACTORY.createBooleanCssMetaData(
                        "-mfx-animated",
                        SSDoubleField::animatedProperty,
                        true
                );

        private static final CssMetaData<SSDoubleField, Number> BORDER_GAP =
                FACTORY.createSizeCssMetaData(
                        "-mfx-border-gap",
                        SSDoubleField::borderGapProperty,
                        10.0
                );

        private static final CssMetaData<SSDoubleField, Boolean> CARET_VISIBLE =
                FACTORY.createBooleanCssMetaData(
                        "-mfx-caret-visible",
                        SSDoubleField::caretVisibleProperty,
                        true
                );

        private static final CssMetaData<SSDoubleField, Boolean> EDITABLE =
                FACTORY.createBooleanCssMetaData(
                        "-mfx-editable",
                        SSDoubleField::allowEditProperty,
                        true
                );

        private static final CssMetaData<SSDoubleField, FloatMode> FLOAT_MODE =
                FACTORY.createEnumCssMetaData(
                        FloatMode.class,
                        "-mfx-float-mode",
                        SSDoubleField::floatModeProperty,
                        FloatMode.INLINE
                );

        private static final CssMetaData<SSDoubleField, Number> FLOATING_TEXT_GAP =
                FACTORY.createSizeCssMetaData(
                        "-mfx-gap",
                        SSDoubleField::floatingTextGapProperty,
                        5.0
                );

        private static final CssMetaData<SSDoubleField, Number> GRAPHIC_TEXT_GAP =
                FACTORY.createSizeCssMetaData(
                        "-fx-graphic-text-gap",
                        SSDoubleField::graphicTextGapProperty,
                        10.0
                );

        private static final CssMetaData<SSDoubleField, Number> MEASURE_UNIT_GAP =
                FACTORY.createSizeCssMetaData(
                        "-mfx-measure-unit-gap",
                        SSDoubleField::measureUnitGapProperty,
                        5.0
                );

        private static final CssMetaData<SSDoubleField, Boolean> SCALE_ON_ABOVE =
                FACTORY.createBooleanCssMetaData(
                        "-mfx-scale-on-above",
                        SSDoubleField::scaleOnAboveProperty,
                        false
                );

        private static final CssMetaData<SSDoubleField, Color> TEXT_FILL =
                FACTORY.createColorCssMetaData(
                        "-fx-text-fill",
                        SSDoubleField::textFillProperty,
                        DEFAULT_TEXT_COLOR
                );

        private static final CssMetaData<SSDoubleField, Number> TEXT_LIMIT =
                FACTORY.createSizeCssMetaData(
                        "-mfx-text-limit",
                        SSDoubleField::textLimitProperty,
                        -1
                );

        static {
            cssMetaDataList = StyleablePropertiesUtils.cssMetaDataList(
                    DoubleField.getClassCssMetaData(),
                    ANIMATED, CARET_VISIBLE, BORDER_GAP,
                    EDITABLE, FLOAT_MODE, FLOATING_TEXT_GAP, GRAPHIC_TEXT_GAP, MEASURE_UNIT_GAP,
                    SCALE_ON_ABOVE, TEXT_FILL, TEXT_LIMIT
            );
        }
    }
}