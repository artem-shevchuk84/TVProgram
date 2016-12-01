package ua.tools.escondido.tvprogram.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.News;

public class NewsListAdapter extends ArrayAdapter<News> {
    private final Context context;
    private final List<News> values;

    public NewsListAdapter(Context context, List<News> values) {
        super(context, R.layout.news_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.news_list, parent, false);
        ImageView newsImage = (ImageView) rowView.findViewById(R.id.news_image);
        String imagePath = values.get(position).getImagePath();
        Picasso.with(context)
                .load(imagePath)
                .error(R.drawable.ic_menu_gallery)
                .into(newsImage);

        TextView title = (TextView) rowView.findViewById(R.id.news_title);
        TextView description = (TextView) rowView.findViewById(R.id.news_description);
        title.setText(values.get(position).getTitle());
        description.setText(values.get(position).getDescription());
        return rowView;
    }
}
