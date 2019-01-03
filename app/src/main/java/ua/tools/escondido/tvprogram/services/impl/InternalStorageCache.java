package ua.tools.escondido.tvprogram.services.impl;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import ua.tools.escondido.tvprogram.services.Cacheable;
import ua.tools.escondido.tvprogram.utils.DateUtils;

public class InternalStorageCache<E> implements Cacheable<E>{

    public static final int DAY = 86400000;

    @Override
    public void writeObject(Context context, String key, E data) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(data);
        oos.close();
        fos.close();
    }

    @Override
    public E readObject(Context context, String key, Boolean isCleanUpNeeded) throws IOException, ClassNotFoundException {
        if(isCleanUpNeeded) {
            cleanUpData(context);
        }
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return (E) ois.readObject();
    }

    protected static void cleanUpData(Context context){
        String today = DateUtils.formatChannelAccessDate(new Date(System.currentTimeMillis() - 18000000));
        String day1 = DateUtils.formatChannelAccessDate(new Date(System.currentTimeMillis() + DAY));
        String day2 = DateUtils.formatChannelAccessDate(new Date(System.currentTimeMillis() + 2 * DAY));
        String day3 = DateUtils.formatChannelAccessDate(new Date(System.currentTimeMillis() + 3 * DAY));
        String day4 = DateUtils.formatChannelAccessDate(new Date(System.currentTimeMillis() + 4 * DAY));
        String day5 = DateUtils.formatChannelAccessDate(new Date(System.currentTimeMillis() + 5 * DAY));
        String day6 = DateUtils.formatChannelAccessDate(new Date(System.currentTimeMillis() + 6 * DAY));
        for(String fileName : context.fileList()){
            if(!(fileName.contains(today) || fileName.contains(day1) || fileName.contains(day2)
                    || fileName.contains(day3) || fileName.contains(day4) || fileName.contains(day5) || fileName.contains(day6))
                    && (!fileName.startsWith("_"))){
                context.deleteFile(fileName);
            }
        }
    }
}
