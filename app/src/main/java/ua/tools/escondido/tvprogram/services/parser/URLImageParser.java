package ua.tools.escondido.tvprogram.services.parser;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;

import ua.tools.escondido.tvprogram.services.loader.async.ImageLoader;
import ua.tools.escondido.tvprogram.utils.URLDrawable;

public class URLImageParser implements Html.ImageGetter{
    Context c;
    View container;

    public URLImageParser(View t, Context c) {
        this.c = c;
        this.container = t;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();

        // get the actual source
        ImageLoader asyncTask =
                new ImageLoader(urlDrawable, this);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }

    public View getContainer() {
        return container;
    }
}
