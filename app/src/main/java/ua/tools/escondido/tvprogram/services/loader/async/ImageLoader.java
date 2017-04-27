package ua.tools.escondido.tvprogram.services.loader.async;


import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ua.tools.escondido.tvprogram.services.parser.URLImageParser;
import ua.tools.escondido.tvprogram.utils.URLDrawable;

public class ImageLoader extends AsyncTask<String, Void, Drawable> {
    URLDrawable urlDrawable;
    URLImageParser urlImageParser;

    public ImageLoader(URLDrawable d, URLImageParser urlImageParser) {
        this.urlDrawable = d;
        this.urlImageParser = urlImageParser;
    }

    @Override
    protected Drawable doInBackground(String... params) {
        String source = params[0];
        return fetchDrawable(source);
    }

    @Override
    protected void onPostExecute(Drawable result) {
        // set the correct bound according to the result from HTTP call
        urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0
                + result.getIntrinsicHeight());

        // change the reference of the current drawable to the result
        // from the HTTP call
        urlDrawable.setDrawable(result);

        // redraw the image by invalidating the container
        urlImageParser.getContainer().invalidate();
    }

    public Drawable fetchDrawable(String urlString) {
        try {
            InputStream is = fetch(urlString);
            Drawable drawable = Drawable.createFromStream(is, "src");
            drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0
                    + drawable.getIntrinsicHeight());
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
