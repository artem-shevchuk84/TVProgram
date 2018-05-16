package ua.tools.escondido.tvprogram.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.data.adapter.ProgramsListAdapter;
import ua.tools.escondido.tvprogram.data.factory.ChannelContentParserFactory;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.loader.async.ProgramListDataLoader;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.utils.Constants;
import ua.tools.escondido.tvprogram.utils.DateUtils;


public class ProgramListActivity extends BaseListActivity {

    private ChannelProgramService channelProgramService;
    private String channelName;
    private String activityToBack;
    private boolean isScheduleAvailable = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_schedule);

        Intent intent = getIntent();
        channelName = intent.getStringExtra(Constants.CHANNEL_NAME);
        activityToBack = intent.getStringExtra(Constants.BACK_ACTIVITY);
        if("Channel".equals(activityToBack)) {
            isScheduleAvailable = true;
        }
        String today = DateUtils.formatDate(DateUtils.DISPLAY_DATE_FORMAT, DateUtils.getToday());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.getToday());
        calendar.add(Calendar.DATE, 1);
        String tomorrow = DateUtils.formatDate(DateUtils.DISPLAY_DATE_FORMAT, calendar.getTime());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(channelName);

        final Button todayBtn = (Button) findViewById(R.id.today);
        final Button tomorrowBtn = (Button) findViewById(R.id.tomorrow);
        todayBtn.setText(today);
        todayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayBtn.setBackgroundResource(R.drawable.button_selected);
                tomorrowBtn.setBackgroundResource(R.drawable.button_normal);
                todayBtn.setTextColor(ColorStateList.valueOf(Color.WHITE));
                tomorrowBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                String formattedDate = DateUtils.formatChannelAccessDate(DateUtils.getToday());
                load(channelName, formattedDate);
            }
        });
        tomorrowBtn.setText(tomorrow);
        tomorrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomorrowBtn.setBackgroundResource(R.drawable.button_selected);
                todayBtn.setBackgroundResource(R.drawable.button_normal);
                tomorrowBtn.setTextColor(ColorStateList.valueOf(Color.WHITE));
                todayBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtils.getToday());
                calendar.add(Calendar.DATE, 1);
                String formattedDate = DateUtils.formatChannelAccessDate(calendar.getTime());
                load(channelName, formattedDate);
            }
        });

        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        String formattedDate = DateUtils.formatChannelAccessDate(DateUtils.getToday());

        load(channelName, formattedDate);

    }

    private void goBack() {
        Intent intent = null;
        if("Channel".equals(activityToBack)) {
            intent = new Intent(ProgramListActivity.this, ChannelActivity.class);
        } else if("TVProgram".equals(activityToBack)){
            intent = new Intent(ProgramListActivity.this, ProgramCategoryActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ProgramEvent programEvent = (ProgramEvent) getListAdapter().getItem(position);
        String programInfoPath = programEvent.getProgramInfoPath();
        if(programInfoPath != null){
            Intent intent = new Intent(this, ProgramInfoActivity.class);
            intent.putExtra(Constants.PROGRAM_INFO_PATH, programInfoPath);
            intent.putExtra(Constants.CHANNEL_NAME, channelName);
            intent.putExtra(Constants.BACK_ACTIVITY, activityToBack);
            startActivity(intent);
        }

    }

    private void load(final String channelName, String formattedDate) {
        ChannelContentParser channelContentParser = ChannelContentParserFactory.build(getBaseContext(), channelName);
        channelProgramService = new ChannelProgramServiceImpl<>(getBaseContext(), channelContentParser);

        String[] data = new String[] {formattedDate};

        new ProgramListDataLoader(this, channelProgramService, true, new AsyncTaskCallback<List<ProgramEvent>>() {
            @Override
            public void run(List<ProgramEvent> result) {
                setListAdapter(new ProgramsListAdapter(getBaseContext(), result, channelName, isScheduleAvailable));
            }

            @Override
            public void handleError() {
                goBack();
            }

        }).execute(data);
    }

}
