package ua.tools.escondido.tvprogram.services.loader.async;


import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoader extends AsyncTask<String, Void, Drawable> {

    @Override
    protected Drawable doInBackground(String... params) {
        String source = params[0];
        return fetchDrawable(source);
    }

    public Drawable fetchDrawable(String urlString) {
        try {
            InputStream is = fetch(urlString);
            Drawable drawable = Drawable.createFromStream(is, "src");
            return drawable;
        } catch (Exception e) {
            return null;
        }
    }

    private InputStream fetch(String urlString) throws IOException {
        HttpURLConnection conn = null;
        URL url = new URL(urlString);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return conn.getInputStream();
    }
}
