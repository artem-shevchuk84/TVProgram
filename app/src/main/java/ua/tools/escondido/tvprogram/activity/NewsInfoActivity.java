package ua.tools.escondido.tvprogram.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.News;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.loader.async.ImageLoader;
import ua.tools.escondido.tvprogram.services.loader.async.NewsInfoDataLoader;
import ua.tools.escondido.tvprogram.utils.Constants;
import ua.tools.escondido.tvprogram.utils.Navigate;


public class NewsInfoActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_info);
        Intent intent = getIntent();
        String newsInfoPath = intent.getStringExtra(Constants.NEWS_INFO_PATH);
        final String title = intent.getStringExtra(Constants.NEWS_TITLE);
        final String image = intent.getStringExtra(Constants.NEWS_IMAGE);

        ImageView newsImage = (ImageView) findViewById(R.id.program_image);
        TextView newsTitle = (TextView) findViewById(R.id.program_title);
        final TextView newsContent = (TextView) findViewById(R.id.program_description);

        Picasso.with(getBaseContext())
                .load(image)
                .error(R.drawable.ic_menu_gallery)
                .into(newsImage);
        newsTitle.setText(title);

        homeBtn.setVisibility(View.GONE);

        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigate.goBack(NewsInfoActivity.this, NewsActivity.class);
            }
        });

        new NewsInfoDataLoader(this, new AsyncTaskCallback<News>() {

            @Override
            public void run(News result) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    newsContent.setText(Html.fromHtml(result.getDescription(), new Html.ImageGetter(){
                        @Override
                        public Drawable getDrawable(String source) {
                            return load(source);
                        }
                    }, null));
                } else{
                    newsContent.setText(Html.fromHtml(result.getDescription(), new Html.ImageGetter(){
                        @Override
                        public Drawable getDrawable(String source) {
                            return load(source);
                        }

                    }, null));
                }
            }

            @Nullable
            private Drawable load(String source) {
                if(!source.contains("https:")){
                    source = "https:".concat(source);
                }
                Drawable drawable = null;
                try {
                    drawable = new ImageLoader().execute(source).get();
                    int scale =  newsContent.getWidth()/drawable.getIntrinsicWidth();
                    drawable.setBounds(
                            0,
                            0,
                            newsContent.getWidth(),
                            drawable.getIntrinsicHeight() * scale);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return drawable;
            }
            @Override
            public void handleError() {
                Intent intent = new Intent(NewsInfoActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        }).execute(newsInfoPath);

    }

}
