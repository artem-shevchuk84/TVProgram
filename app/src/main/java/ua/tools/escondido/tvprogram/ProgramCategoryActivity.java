package ua.tools.escondido.tvprogram;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import ua.tools.escondido.tvprogram.services.IAdvertizable;
import ua.tools.escondido.tvprogram.services.impl.Advertise;
import ua.tools.escondido.tvprogram.utils.Constants;


public class ProgramCategoryActivity extends ListActivity {

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
        toolbar.setTitle(getResources().getString(R.string.title_categories));
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        String[] channels = new String[] {getResources().getString(R.string.tv_serials),
                getResources().getString(R.string.tv_films),
                getResources().getString(R.string.tv_entertainment),
                getResources().getString(R.string.tv_information),
                getResources().getString(R.string.tv_sociopolitical),
                getResources().getString(R.string.tv_show),
                getResources().getString(R.string.tv_sport),
                getResources().getString(R.string.tv_kid)
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
        intent.putExtra(Constants.BACK_ACTIVITY, "TVProgram");
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(ProgramCategoryActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
