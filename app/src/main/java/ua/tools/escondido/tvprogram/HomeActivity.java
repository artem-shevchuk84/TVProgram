package ua.tools.escondido.tvprogram;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import ua.tools.escondido.tvprogram.data.MenuCell;
import ua.tools.escondido.tvprogram.data.adapter.CellMenuAdapter;

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");

        AdView banner = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);

        MenuCell[] menuCells = new MenuCell[3];
        menuCells[0] = new MenuCell(R.mipmap.ic_list_white_48dp, getResources().getString(R.string.title_channels));
        menuCells[1] = new MenuCell(R.mipmap.ic_list_white_48dp, getResources().getString(R.string.title_categories));
        menuCells[2] = new MenuCell(R.mipmap.ic_speaker_notes_white_48dp, getResources().getString(R.string.title_news));

        GridView gridView = (GridView)findViewById(R.id.gridview);
        CellMenuAdapter cellMenuAdapter = new CellMenuAdapter(this, menuCells);
        gridView.setAdapter(cellMenuAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(getBaseContext(), ChannelActivity.class);
                        startActivity(intent);
                        return;
                    case 1:
                        intent = new Intent(getBaseContext(), ProgramCategoryActivity.class);
                        startActivity(intent);
                        return;
                    case 2:
                        intent = new Intent(getBaseContext(), NewsActivity.class);
                        startActivity(intent);
                        return;
                    default:
                        break;
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_home));
    }

}
