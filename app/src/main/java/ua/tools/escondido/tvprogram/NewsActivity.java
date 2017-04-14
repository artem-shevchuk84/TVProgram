package ua.tools.escondido.tvprogram;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import ua.tools.escondido.tvprogram.data.News;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.NewsService;
import ua.tools.escondido.tvprogram.services.impl.NewsServiceImpl;
import ua.tools.escondido.tvprogram.data.adapter.NewsListAdapter;
import ua.tools.escondido.tvprogram.services.loader.async.NewsListDataLoader;
import ua.tools.escondido.tvprogram.utils.Constants;

public class NewsActivity extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.action_news));
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });


        new NewsListDataLoader(this, new AsyncTaskCallback<List<News>>() {
            @Override
            public void run(List<News> result) {
                setListAdapter(new NewsListAdapter(getBaseContext(), result));
            }

            @Override
            public void handleError() {
                Intent intent = new Intent(NewsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }).execute();

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        News news = (News) getListAdapter().getItem(position);
        String newsInfoPath = news.getLink();
        if(newsInfoPath != null){
            Intent intent = new Intent(this, NewsInfoActivity.class);
            intent.putExtra(Constants.NEWS_INFO_PATH, newsInfoPath);
            intent.putExtra(Constants.NEWS_TITLE, news.getTitle());
            intent.putExtra(Constants.NEWS_IMAGE, news.getImagePath());
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_channels:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_tvprograms:
                intent = new Intent(this, TVProgramActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_news:
                intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
