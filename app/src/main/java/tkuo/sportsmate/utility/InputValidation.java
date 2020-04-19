package tkuo.sportsmate.utility;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
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
     * Check if the string is empty
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
     * Check if the string contains any digit
     *
     * Used for: firstName, lastName
     * @param str
     * @return true if value contains numeric character, false otherwise
     */
    public boolean isNumberChar(String str) {

        String regEx = ".*\\d.*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        return m.matches();
    }


    /**
     * Check if the password is valid
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
     * Check if the two passwords match
     *
     * Used for password
     * @param str1, str2
     * @return true if two passwords match, false otherwise
     */
    public boolean isPasswordMatched (String str1, String str2) {
        if (!str1.equals(str2))
            return false;

        return true;
    }


    /**
     * Check if the date is legal (No prior to today)
     *
     * Used for: date(create personal match)
     * @param date, time
     * @return true if the date is no prior to today, false otherwise
     */
    public boolean isDateValid(String date, String time) {
        SimpleDateFormat sd = new SimpleDateFormat("M/dd/yyyy HH:mm");
        try {
            String input = date + " " + time;
            sd.setLenient(false);
            Date input_date = sd.parse(input);
            Date current_date = sd.parse(sd.format((new Date())));
            long inputTime = input_date.getTime();
            long currentTime = current_date.getTime();
            long diff = inputTime - currentTime;

            if (diff >= 0) {
                return true;
            }
        }
        catch (Exception e) {
            return false;
        }

        return false;
    }


    /**
     * Check if the date is legal (No prior to today)
     *
     * Used for: date(create personal match)
     * @param start, end
     * @return true if the date is no prior to today, false otherwise
     */
    public boolean isTimeValid(String start, String end) {
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm");

        try {
            Date inTime = sd.parse(start);
            Date outTime = sd.parse(end);

            if (inTime.before(outTime))
                return true;
        }
        catch (Exception e) {
            return false;
        }

        return false;
    }



}
