package ua.tools.escondido.tvprogram.utils;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class InternalStorage {
    private InternalStorage() {}

    public static void writeObject(Context context, String key, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        cleanUpData(context);
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }

    public static void cleanUpData(Context context){
        String today = DateUtils.formatChannelAccessDate(new Date(System.currentTimeMillis() - 18000000));
        String nextDay = DateUtils.formatChannelAccessDate(new Date(System.currentTimeMillis() + 86400000));
        for(String fileName : context.fileList()){
            if(!(fileName.contains(today) || fileName.contains(nextDay))){
                context.deleteFile(fileName);
            }
        }
    }
}
