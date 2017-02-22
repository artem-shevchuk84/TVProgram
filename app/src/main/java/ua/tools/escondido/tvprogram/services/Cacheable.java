package ua.tools.escondido.tvprogram.services;


import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Cacheable<E> {

    void writeObject(Context context, String key, E data) throws IOException;

    E readObject(Context context, String key, Boolean isCleanUpNeeded) throws IOException, ClassNotFoundException;
}
