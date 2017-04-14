package ua.tools.escondido.tvprogram.services.loader.async;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.News;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.NewsService;
import ua.tools.escondido.tvprogram.services.impl.NewsServiceImpl;

public class NewsInfoDataLoader extends AsyncTask<String, Void, News> {

    private AsyncTaskCallback<News> callback;
    private ProgressDialog progressDialog;
    private Context context;

    public NewsInfoDataLoader(Context context, AsyncTaskCallback<News> callback) {
        this.callback = callback;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.progressbar_loading_text));
        progressDialog.show();
    }

    @Override
    protected News doInBackground(String... params) {
        NewsService newsService = new NewsServiceImpl();
        return newsService.getNewsInfo(params[0]);
    }

    @Override
    protected void onPostExecute(News news) {
        progressDialog.dismiss();
        if (news == null) {
            final AlertDialog alertDialog = new AlertDialog.Builder(context).
                    setMessage(context.getString(R.string.alert_error_no_internet)).
                    setCancelable(false).
                    setNegativeButton(R.string.alert_btn_close, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                            callback.handleError();
                        }
                    }).
                    create();
            alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setBackgroundResource(R.drawable.button_selected);
                }
            });
            alertDialog.show();
            news = new News();
        }
        callback.run(news);
    }
}
