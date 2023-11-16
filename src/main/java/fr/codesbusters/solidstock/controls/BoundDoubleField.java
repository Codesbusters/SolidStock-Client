package fr.codesbusters.solidstock.controls;

import com.sun.javafx.scene.control.DoubleField;
import com.sun.javafx.scene.control.skin.DoubleFieldSkin;
import fr.codesbusters.solidstock.component.SSDoubleField;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BoundDoubleField extends DoubleField {
    //================================================================================
    // Properties
    //================================================================================
    private SSDoubleField textField;

    //================================================================================
    // Constructors
    //================================================================================
    public BoundDoubleField(SSDoubleField textField) {
        this.textField = textField;

        // Initialization
        setPromptText(textField.getPromptText());
        setValue(textField.getValue());
        setEditable(textField.isEditable());
        setPrefColumnCount(textField.getPrefColumnCount());

        // Binding
        promptTextProperty().bind(textField.promptTextProperty());
        editableProperty().bind(textField.editableProperty());
        prefColumnCountProperty().bind(textField.prefColumnCountProperty());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new BoundDoubleField.CustomTextFieldSkin(this);
    }

    public void dispose() {
        textField = null;
    }


    private class CustomTextFieldSkin extends DoubleFieldSkin {

        public CustomTextFieldSkin(DoubleField field) {
            super(field);

            // This is needed because there's no way to distinguish the text node
            // from the prompt text node (both have .text style class, no id)
            // The prompt text node is present only when the prompt text is not empty
            // and it's always placed at the beginning of the children list
            // so the text node we want is the last
            Text textNode;
            if (!field.getPromptText().isEmpty()) {
                try {
                    List<Node> textNodes = new ArrayList<>(lookupAll(".text"));
                    textNode = (Text) textNodes.get(textNodes.size() - 1);
                } catch (Exception ex) {
                    textNode = (Text) lookup(".text");
                }
            } else {
                textNode = (Text) lookup(".text");
            }

        }

    }
}
