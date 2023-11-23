package view;

import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * JTextFieldHintUI is a UI class that provides a hint (placeholder text) for JTextField components.
 * This class extends BasicTextFieldUI and implements FocusListener to handle focus events.
 */
public class JTextFieldHintUI extends BasicTextFieldUI implements FocusListener {

    private final String hint;     // The hint text to be displayed
    private final Color hintColor; // The color of the hint text

    /**
     * Constructs a new JTextFieldHintUI with the specified hint text and hint color.
     *
     * @param hint      The hint text to be displayed.
     * @param hintColor The color of the hint text.
     */
    public JTextFieldHintUI(String hint, Color hintColor) {

        this.hint = hint;
        this.hintColor = hintColor;
    }

    /**
     * Invoked when the component gains focus. If the current text is the hint, it is cleared.
     *
     * @param e The focus event.
     */
    @Override
    public void focusGained(FocusEvent e) {

        if (e.getSource() instanceof JTextComponent textComponent) {

            if (textComponent.getText().equals(hint)) {

                textComponent.setText("");
                textComponent.setForeground(Color.BLACK);
            }
        }
    }

    /**
     * Invoked when the component loses focus. If the current text is empty, the hint is set and its color is applied.
     *
     * @param e The focus event.
     */
    @Override
    public void focusLost(FocusEvent e) {

        if (e.getSource() instanceof JTextComponent textComponent) {

            if (textComponent.getText().isEmpty()) {

                textComponent.setText(hint);
                textComponent.setForeground(hintColor);
            }
        }
    }

    /**
     * Installs the necessary listeners, including the FocusListener for handling focus events.
     */
    @Override
    protected void installListeners() {

        super.installListeners();
        getComponent().addFocusListener(this);
    }

    /**
     * Uninstalls the previously installed listeners, including the FocusListener.
     */
    @Override
    protected void uninstallListeners() {

        super.uninstallListeners();
        getComponent().removeFocusListener(this);
    }
}
