package ua.tools.escondido.tvprogram;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.services.parser.tv.BaseTVContentParser;
import ua.tools.escondido.tvprogram.utils.Constants;
import ua.tools.escondido.tvprogram.utils.DateUtils;
import ua.tools.escondido.tvprogram.data.adapter.ProgramsListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ProgramListActivity extends ListActivity {

    private ChannelProgramService channelProgramService = new ChannelProgramServiceImpl<>(this, new ChannelContentParser());
    private ProgressDialog dialog;
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
                Intent intent = null;
                if("Home".equals(activityToBack)) {
                    intent = new Intent(ProgramListActivity.this, HomeActivity.class);
                } else if("TVProgram".equals(activityToBack)){
                    intent = new Intent(ProgramListActivity.this, TVProgramActivity.class);
                }
                startActivity(intent);
            }
        });

        String formattedDate = DateUtils.formatChannelAccessDate(DateUtils.getToday());

        load(channelName, formattedDate);

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
        channelProgramService = new ChannelProgramServiceImpl<>(this, channelContentParser);

        String[] data = new String[] {formattedDate};
        try {
            dialog = new ProgressDialog(this);
            //List<ProgramEvent> programEvents = getStub();
            List<ProgramEvent> programEvents = new LoadChannelData().execute(data).get();
            setListAdapter(new ProgramsListAdapter(this, programEvents));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private List<ProgramEvent> getStub() throws InterruptedException,ExecutionException {
        List<ProgramEvent> programEvents = new ArrayList<>();
        for (int i=0;i<20;i++){
            ProgramEvent event = new ProgramEvent();
            event.setName("Test Program " + i);
            event.setTime("08:"+(10+i));
            event.setProgramInfoPath("/entertainment/295608/revizor-7/");
            programEvents.add(event);
        }
        return programEvents;
    }
    class LoadChannelData extends AsyncTask<String, Void, List<ProgramEvent>>{

        @Override
        protected List<ProgramEvent> doInBackground(String... params) {
            return channelProgramService.getChannelProgram(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Processing...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(List<ProgramEvent> programEvents) {
            super.onPostExecute(programEvents);
            dialog.dismiss();
        }
    }
}
