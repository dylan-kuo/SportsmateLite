package tkuo.sportsmate.utility;

import android.content.Context;
import android.widget.EditText;
import android.widget.RadioButton;


public class InputValidation {
    private Context context;

    /**
     * Constructor
     *
     * @param context
     */
    public InputValidation(Context context ) {
        this.context = context;
    }

    /**
     * Method to check empty
     *
     * @param editText
     */
    public boolean isEditTextFilled(EditText editText) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * Method to check empty
     *
     * @param radioButton
     */
    public boolean isRadioButtonFilled(RadioButton radioButton) {
        String value = radioButton.getText().toString();
        if (value.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * Method to check valid format
     *
     * @param editText
     */
    public boolean isEditTextValid(EditText editText) {
        String value = editText.getText().toString().trim();
        // NOT DONE YET
        return true;
    }

    public boolean isPasswordMatched (EditText editText1, EditText editText2) {
        String value1 = editText1.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();
        if (!value1.equals(value2))
            return false;

        return true;
    }
}
