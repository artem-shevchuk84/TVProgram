package ua.tools.escondido.tvprogram;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ua.tools.escondido.tvprogram.utils.Constants;

public class ChannelActivity extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.action_channels));
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        String[] channels = new String[] {getResources().getString(R.string.channel_novyj),
                getResources().getString(R.string.channel_stb),
                getResources().getString(R.string.channel_ictv),
                getResources().getString(R.string.channel_one_plus_one),
                getResources().getString(R.string.channel_mega),
                getResources().getString(R.string.channel_ukraina),
                getResources().getString(R.string.channel_ua_pervyj),
                getResources().getString(R.string.channel_inter),
                getResources().getString(R.string.channel_5kanal),
                getResources().getString(R.string.channel_k1),
                getResources().getString(R.string.channel_ntn),
                getResources().getString(R.string.channel_tet),
                getResources().getString(R.string.channel_two_plus_two),
                getResources().getString(R.string.channel_piksel),
                getResources().getString(R.string.channel_nlo),
                getResources().getString(R.string.channel_enterfilm),
                getResources().getString(R.string.channel_m1),
                getResources().getString(R.string.channel_k2),
                getResources().getString(R.string.channel_zoom),
                getResources().getString(R.string.channel_espresso),
                getResources().getString(R.string.channel_tonis),
                getResources().getString(R.string.channel_football1),
                getResources().getString(R.string.channel_football2)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.channel_list, R.id.label, channels);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        Intent intent = new Intent(this, ProgramListActivity.class);
        intent.putExtra(Constants.CHANNEL_NAME, item);
        intent.putExtra(Constants.BACK_ACTIVITY, "Channel");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_channels:
                intent = new Intent(this, ChannelActivity.class);
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
