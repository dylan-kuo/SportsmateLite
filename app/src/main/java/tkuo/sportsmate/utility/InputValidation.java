package tkuo.sportsmate.utility;

import android.content.Context;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
     * @return true/false
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
     * @return true/false
     */
    public boolean isRadioButtonFilled(RadioButton radioButton) {
        String value = radioButton.getText().toString();
        if (value.isEmpty()) {
            return false;
        }

        return true;
    }


    // **************************************************************************
    /**
     * Check empty value
     *
     * Used for: all input
     * @param str
     * @return true if value is empty, and false otherwise.
     */
    public boolean isValueNotFilled(String str) {
        if (str.isEmpty()) {
            return true;
        }

        return false;
    }


    /**
     * Check special characters
     *
     * Used for: userName, firstName, lastName
     * @param str
     * @return true if value includes special characters, and false otherwise.
     */
    public boolean isSpecialChar(String str) {

        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        return m.find();
    }


    /**
     * Check number character
     *
     * Used for: firstName, lastName
     * @param str
     * @return true if value is number, false otherwise
     */
    public boolean isNumberChar(String str) {

        String regEx = ".*\\d.*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        return m.matches();
    }


    /**
     * Check valid password
     *
     * Used for: password
     * @param str
     * @return true if value is valid, false otherwise
     */
    public boolean isPasswordValid(String str) {

        // REGULAR EXPRESSION RULE:
        // ^                 # start-of-string
        //(?=.*[0-9])       # a digit must occur at least once
        //(?=.*[a-z])       # a lower case letter must occur at least once
        //(?=.*[A-Z])       # an upper case letter must occur at least once
        //(?=.*[@#$%^&+=])  # a special character must occur at least once you can replace with your special characters
        //(?=\\S+$)         # no whitespace allowed in the entire string
        //.{4,}             # anything, at least six places though
        //$                 # end-of-string

        String regEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }









    /**
     * Method to check valid format
     *
     * @param editText
     * @return true/false
     */
    public boolean isEditTextValid(EditText editText) {
        String value = editText.getText().toString().trim();
        // NOT DONE YET
        return true;
    }


    /**
     * Method to check if passwords are matcged
     *
     * @param editText1, editText2
     * @return true/false
     */
    public boolean isPasswordMatched (EditText editText1, EditText editText2) {
        String value1 = editText1.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();
        if (!value1.equals(value2))
            return false;

        return true;
    }
}
