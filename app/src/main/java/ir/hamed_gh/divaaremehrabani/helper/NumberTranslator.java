package ir.hamed_gh.divaaremehrabani.helper;

/**
 * Created by RezaRg on 11/23/14.
 */
public class NumberTranslator {

    public static String toPersian(int c) {
        return toPersian(String.valueOf(c));
    }

    public static String toEnglish(int c) {
        return toEnglish(String.valueOf(c));
    }

    public static String toPersian(String c) {

        String[] enN = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] faN = {"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};

        if (c == null) {
            return "";
        }

        for (int i = 0; i < 10; i++) {
            c = c.replace(enN[i], faN[i]);
        }

        return c;

    }

    public static String toEnglish(String c) {

        String[] faN = {"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};
        String[] enN = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        if (c == null) {
            return "";
        }

        for (int i = 0; i < 10; i++) {
            c = c.replace(faN[i], enN[i]);
        }

        return c;

    }


}
