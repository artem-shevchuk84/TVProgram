package ua.tools.escondido.tvprogram;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ua.tools.escondido.tvprogram.data.News;
import ua.tools.escondido.tvprogram.services.NewsService;
import ua.tools.escondido.tvprogram.services.impl.NewsServiceImpl;
import ua.tools.escondido.tvprogram.data.adapter.NewsListAdapter;

public class NewsActivity extends ListActivity {

    private ProgressDialog dialog;
    private NewsService newsService = new NewsServiceImpl();

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
        dialog = new ProgressDialog(this);
        List<News> news = null;
        try {
            //news = getStub();
            news = new LoadNews().execute().get();
            setListAdapter(new NewsListAdapter(this, news));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_channels:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_news:
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<News> getStub() throws InterruptedException,ExecutionException {
        List<News> result = new ArrayList<>();
        for (int i = 0; i<10; i++){
            News news = new News();
            news.setTitle("Very long Title to validate how this layout looks for "+i);
            news.setDescription("Some Description what does not mean anything in context of current " +
                    "task and just provide us ability to validate it for "+i);
            news.setImagePath("http://tv.ukr.net/i/uploads/20161102/pr_2C30w.jpg");
            result.add(news);
        }
        return result;
    }

    class LoadNews extends AsyncTask<Void, Void ,List<News>> {

        @Override
        protected List<News> doInBackground(Void... params) {
            return newsService.getNews();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Processing...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(List<News> news) {
            super.onPostExecute(news);
            dialog.dismiss();
        }
    }
}
