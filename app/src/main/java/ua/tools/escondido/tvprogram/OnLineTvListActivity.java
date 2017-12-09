package ua.tools.escondido.tvprogram;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import ua.tools.escondido.tvprogram.services.IAdvertizable;
import ua.tools.escondido.tvprogram.services.impl.Advertise;
import ua.tools.escondido.tvprogram.utils.Constants;

public class OnLineTvListActivity extends ListActivity {

    private IAdvertizable advertizable = new Advertise();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channels);

        advertizable.initBanner(this);

        Button homeBtn = (Button) findViewById(R.id.toolbar_home);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
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

    private void goHome() {
        Intent intent = new Intent(OnLineTvListActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
