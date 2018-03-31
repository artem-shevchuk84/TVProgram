package ua.tools.escondido.tvprogram.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.utils.Constants;
import ua.tools.escondido.tvprogram.utils.Navigate;

public class OnLineTvListActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigate.goHome(OnLineTvListActivity.this);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_channels));
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        String[] channels = new String[] {
                getResources().getString(R.string.channel_5kanal),
                getResources().getString(R.string.channel_espresso),
                getResources().getString(R.string.channel_hromadske),
                getResources().getString(R.string.channel_112),
                getResources().getString(R.string.channel_24channel),
                getResources().getString(R.string.channel_ZIK),
                getResources().getString(R.string.channel_euronews_eng)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.channel_list, R.id.label, channels);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        Intent intent = new Intent(this, OnLineTvViewActivity.class);
        intent.putExtra(Constants.CHANNEL_NAME, item);
        intent.putExtra(Constants.BACK_ACTIVITY, "OnLineTvList");
        startActivity(intent);
    }

}