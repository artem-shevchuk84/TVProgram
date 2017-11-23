package ua.tools.escondido.tvprogram;

import android.app.ListActivity;
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

import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.data.adapter.ProgramsListAdapter;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.loader.async.ProgramListDataLoader;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.services.parser.tv.BaseTVContentParser;
import ua.tools.escondido.tvprogram.utils.Constants;
import ua.tools.escondido.tvprogram.utils.DateUtils;


public class ProgramListActivity extends ListActivity {

    private ChannelProgramService channelProgramService;
    private String channelName;
    private String activityToBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_schedule);

        Intent intent = getIntent();
        channelName = intent.getStringExtra(Constants.CHANNEL_NAME);
        activityToBack = intent.getStringExtra(Constants.BACK_ACTIVITY);
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

        Button backBtn = (Button) findViewById(R.id.toolbar_btn_back);
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

    private void load(String channelName, String formattedDate) {
        ChannelContentParser channelContentParser = new ChannelContentParser();
        if(getResources().getString(R.string.channel_novyj).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.NOVIY_CANAL);
        }else if(getResources().getString(R.string.channel_stb).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.STB);
        }else if(getResources().getString(R.string.channel_ictv).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.ICTV);
        }else if(getResources().getString(R.string.channel_one_plus_one).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.ONE_PLUS_ONE);
        }else if(getResources().getString(R.string.channel_mega).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.MEGA);
        }else if(getResources().getString(R.string.channel_ukraina).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.UKRAINA);
        }else if(getResources().getString(R.string.channel_ua_pervyj).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.UA_PERVYJ);
        }else if(getResources().getString(R.string.channel_inter).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.INTER);
        }else if(getResources().getString(R.string.channel_5kanal).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.FIVE_KANAL);
        }else if(getResources().getString(R.string.channel_k1).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.K1);
        }else if(getResources().getString(R.string.channel_ntn).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.NTN);
        }else if(getResources().getString(R.string.channel_tet).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.TET);
        }else if(getResources().getString(R.string.channel_two_plus_two).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.TWO_PLUS_TWO);
        }else if(getResources().getString(R.string.channel_piksel).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.PIKSEL);
        }else if(getResources().getString(R.string.channel_nlo).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.NLO);
        }else if(getResources().getString(R.string.channel_enterfilm).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.ENTERFILM);
        }else if(getResources().getString(R.string.channel_m1).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.M1);
        }else if(getResources().getString(R.string.channel_k2).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.K2);
        }else if(getResources().getString(R.string.channel_zoom).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.ZOOM);
        }else if(getResources().getString(R.string.channel_espresso).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.ESPRESSO);
        }else if(getResources().getString(R.string.channel_tonis).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.TONIS);
        }else if(getResources().getString(R.string.channel_football1).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.FOOTBALL1);
        }else if(getResources().getString(R.string.channel_football2).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.FOOTBALL2);
        }

        else if(getResources().getString(R.string.tv_serials).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_SERIALS);
        }else if(getResources().getString(R.string.tv_entertainment).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_ENTERTAINMENT);
        }else if(getResources().getString(R.string.tv_information).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_INFORMATION);
        }else if(getResources().getString(R.string.tv_sociopolitical).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_SOCIOPOLITICAL);
        }else if(getResources().getString(R.string.tv_show).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_SHOW);
        }else if(getResources().getString(R.string.tv_sport).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_SPORT);
        }else if(getResources().getString(R.string.tv_kid).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_KID);
        }else if(getResources().getString(R.string.tv_films).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_FILMS);
        }
        channelProgramService = new ChannelProgramServiceImpl<>(getBaseContext(), channelContentParser);

        String[] data = new String[] {formattedDate};

        new ProgramListDataLoader(this, channelProgramService, new AsyncTaskCallback<List<ProgramEvent>>() {
            @Override
            public void run(List<ProgramEvent> result) {
                setListAdapter(new ProgramsListAdapter(getBaseContext(), result));
            }

            @Override
            public void handleError() {
                goBack();
            }

        }).execute(data);
    }

}
