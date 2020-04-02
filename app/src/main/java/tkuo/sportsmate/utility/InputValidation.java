package tkuo.sportsmate.utility;

import android.content.Context;
import android.widget.EditText;


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
     * Method to check valid format
     *
     * @param editText
     */
    public boolean isEditTextValid(EditText editText) {
        String value = editText.getText().toString().trim();
        // NOT DONE YET
        return true;
    }
}
