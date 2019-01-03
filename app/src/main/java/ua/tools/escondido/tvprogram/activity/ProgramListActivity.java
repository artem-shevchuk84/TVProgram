package ua.tools.escondido.tvprogram.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import java.util.ArrayList;
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

    private static final int DISPLAYED_DAYS = 7;
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
        final HorizontalScrollView scrollView = findViewById(R.id.scrool_view_date_list);
        final List<Button> weekDays = new ArrayList<>(DISPLAYED_DAYS);
        weekDays.add((Button) findViewById(R.id.day1));
        weekDays.add((Button) findViewById(R.id.day2));
        weekDays.add((Button) findViewById(R.id.day3));
        weekDays.add((Button) findViewById(R.id.day4));
        weekDays.add((Button) findViewById(R.id.day5));
        weekDays.add((Button) findViewById(R.id.day6));
        weekDays.add((Button) findViewById(R.id.day7));

        String today = DateUtils.formatDate(DateUtils.DISPLAY_DATE_FORMAT, DateUtils.getToday());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.getToday());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        final int toDay = DISPLAYED_DAYS - dayOfWeek;

        final Button todayBtn = weekDays.get(0);
        todayBtn.setText(today);
        setButtonSelected(todayBtn);

        todayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(todayBtn);
                unselectButtons(weekDays, toDay, 0);
                String formattedDate = DateUtils.formatChannelAccessDate(DateUtils.getToday());
                load(channelName, formattedDate);
            }
        });
        // scroll to button
/*
        todayBtn.requestFocus();
        final Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);
        if (!todayBtn.getLocalVisibleRect(scrollBounds)) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.smoothScrollTo(todayBtn.getLeft(), 0);
                }
            });
        }
*/
        for (int i = 1; i < DISPLAYED_DAYS; i++){
            final Button day = weekDays.get(i);
            final Calendar nextDays = Calendar.getInstance();
            nextDays.setTime(DateUtils.getToday());
            nextDays.add(Calendar.DATE, i);
            String nextDaysValue = DateUtils.formatDate(DateUtils.DISPLAY_DATE_FORMAT, nextDays.getTime());
            day.setText(nextDaysValue);
            if (dayOfWeek + i - DISPLAYED_DAYS <= 0) {
                day.setBackgroundResource(R.drawable.button_normal);
                day.setTextColor(getResources().getColor(R.color.colorPrimary));
                final int index = i;
                day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setButtonSelected(day);
                        unselectButtons(weekDays, toDay, index);
                        String formattedDate = DateUtils.formatChannelAccessDate(nextDays.getTime());
                        load(channelName, formattedDate);
                    }
                });
            } else {
                day.setBackgroundResource(R.drawable.button_disabled);
                day.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(channelName);

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

    private void setButtonSelected(Button day) {
        day.setBackgroundResource(R.drawable.button_selected);
        day.setTextColor(ColorStateList.valueOf(Color.WHITE));
    }

    private void unselectButtons(List<Button> weekDays, int from, int except) {
        for (int i = 0; i <= from; i++){
            if (i != except) {
                Button day = weekDays.get(i);
                day.setBackgroundResource(R.drawable.button_normal);
                day.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }
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
        ChannelProgramService channelProgramService = new ChannelProgramServiceImpl<>(getBaseContext(), channelContentParser);

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
