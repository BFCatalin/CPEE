package ro.bfc.cpee_233.utils;

import android.content.Context;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import ro.bfc.cpee_233.models.CPEEDocument;

/**
 * Created by catalin on 1/3/15.
 */
public class FileUtils {
    public static String getFileAsString(Context context, Uri uri) throws URISyntaxException, IOException {
        InputStream inputStream = null;
        String str = "";
        StringBuffer buf = new StringBuffer();

        inputStream = context.getContentResolver().openInputStream(uri);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        if (inputStream!=null){
            while((str = reader.readLine())!=null){
                buf.append(str+"\n");
            }
            inputStream.close();
        }
        return buf.toString();
    }

    public static void saveToInternalStorage(Context context, String fileName, CPEEDocument doc){
        File file = new File(context.getFilesDir(), fileName);
        FileOutputStream outputStream;

        try {
            String jsonDoc = doc.toJson();
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(jsonDoc.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CPEEDocument readFromInternalStorage(Context context, String fileName){
        File file = new File(context.getFilesDir(), fileName);
        FileInputStream inputStream = null;
        String str = "";
        StringBuffer buf = new StringBuffer();

        try {
            inputStream = context.openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            if (inputStream!=null){
                while((str = reader.readLine())!=null){
                    buf.append(str+"\n");
                }
                inputStream.close();
            }
            return  CPEEDocument.readJSON(buf.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
