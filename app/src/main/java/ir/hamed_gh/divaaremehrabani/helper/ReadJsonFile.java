package ir.hamed_gh.divaaremehrabani.helper;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Hamed on 10/12/16.
 */

public class ReadJsonFile {

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {

            InputStream is = context.getAssets().open("latest.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
