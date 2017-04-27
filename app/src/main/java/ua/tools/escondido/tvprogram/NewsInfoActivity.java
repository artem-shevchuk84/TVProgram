package ua.tools.escondido.tvprogram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ua.tools.escondido.tvprogram.data.News;
import ua.tools.escondido.tvprogram.data.adapter.NewsListAdapter;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.NewsService;
import ua.tools.escondido.tvprogram.services.impl.NewsServiceImpl;
import ua.tools.escondido.tvprogram.services.loader.async.NewsInfoDataLoader;
import ua.tools.escondido.tvprogram.services.loader.async.NewsListDataLoader;
import ua.tools.escondido.tvprogram.services.parser.URLImageParser;
import ua.tools.escondido.tvprogram.utils.Constants;


public class NewsInfoActivity extends Activity{

    private NewsService newsService = new NewsServiceImpl();
    private ProgressDialog dialog;

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

        Button backBtn = (Button) findViewById(R.id.toolbar_btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsInfoActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        dialog = new ProgressDialog(this);
        new NewsInfoDataLoader(this, new AsyncTaskCallback<News>() {
            @Override
            public void run(News result) {
                URLImageParser parser = new URLImageParser(newsContent, getBaseContext());
                Spanned htmlSpan = Html.fromHtml(result.getDescription(), parser, null);
                newsContent.setText(htmlSpan);

/*                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    newsContent.setText(Html.fromHtml(result.getDescription(), Html.FROM_HTML_MODE_LEGACY));
                } else{
                    newsContent.setText(Html.fromHtml(result.getDescription()));
                }*/
            }

            @Override
            public void handleError() {
                Intent intent = new Intent(NewsInfoActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        }).execute(newsInfoPath);

    }

    class LoadNewsInfoData extends AsyncTask<String, Void ,News> {

        @Override
        protected News doInBackground(String... params) {
            News news = new News();
            try{
                news = newsService.getNewsInfo(params[0]);
            } catch (IllegalArgumentException ex){
                news.setDescription(ex.getMessage());
            }
            return news;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Processing...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(News news) {
            super.onPostExecute(news);
            dialog.dismiss();
        }
    }
}
