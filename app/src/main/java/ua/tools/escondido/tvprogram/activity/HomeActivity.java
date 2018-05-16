package ua.tools.escondido.tvprogram.activity;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Calendar;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.MenuCell;
import ua.tools.escondido.tvprogram.data.adapter.CellMenuAdapter;
import ua.tools.escondido.tvprogram.services.broadcast.scheduler.NotificationScheduler;

public class HomeActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);

        homeBtn.setVisibility(View.INVISIBLE);

        MenuCell[] menuCells = new MenuCell[4];
        menuCells[0] = new MenuCell(R.mipmap.ic_list_white_48dp, getResources().getString(R.string.title_channels));
        menuCells[1] = new MenuCell(R.mipmap.ic_list_white_48dp, getResources().getString(R.string.title_categories));
        menuCells[2] = new MenuCell(R.mipmap.ic_speaker_notes_white_48dp, getResources().getString(R.string.title_news));
        menuCells[3] = new MenuCell(R.mipmap.ic_live_tv_white_48dp, getResources().getString(R.string.title_online));
        //menuCells[4] = new MenuCell(ua.tools.escondido.tvprogram.R.mipmap.ic_settings_white_48dp, getResources().getString(ua.tools.escondido.tvprogram.R.string.title_settings));
        //menuCells[5] = new MenuCell(ua.tools.escondido.tvprogram.R.mipmap.ic_info_outline_white_48dp, getResources().getString(ua.tools.escondido.tvprogram.R.string.title_about));

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
                   case 3:
                        intent = new Intent(getBaseContext(), OnLineTvListActivity.class);
                        startActivity(intent);
                        return;
/*                     case 5:
                        intent = new Intent(getBaseContext(), HowToActivity.class);
                        startActivity(intent);
                        return;*/
                    default:
                        break;
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_home));

        setupScheduler();
    }

    private void setupScheduler() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent alarm = new Intent(this, NotificationScheduler.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if(!alarmRunning) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

}
