package ua.tools.escondido.tvprogram;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.utils.Constants;


public class OnLineTvViewActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private String activityToBack;
    private String channelName;

    public static final String API_KEY = "AIzaSyBjTgm30XNETUZS0HfUwU9HrWkywpgRG8A";
    public static final String VIDEO_ID = "9522a1K2qsg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_tv_view);

        Intent intent = getIntent();
        activityToBack = intent.getStringExtra(Constants.BACK_ACTIVITY);
        channelName = intent.getStringExtra(Constants.CHANNEL_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(channelName);

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.on_line_video);
        youTubePlayerView.initialize(API_KEY, this);

        Button backBtn = (Button) findViewById(R.id.toolbar_btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(channelName);
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if(null== youTubePlayer) return;

        // Start buffering
        if (!wasRestored) {
            youTubePlayer.cueVideo(getVideoId(channelName));
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }


    private void goBack(String channelName) {
        Intent intent = new Intent(OnLineTvViewActivity.this, OnLineTvListActivity.class);
        intent.putExtra(Constants.CHANNEL_NAME, channelName);
        intent.putExtra(Constants.BACK_ACTIVITY, activityToBack);
        startActivity(intent);
    }

    private String getVideoId(String channelName){
        if(getResources().getString(R.string.channel_espresso).equalsIgnoreCase(channelName)){
            return Channels.ESPRESSO.getVideoId();
        }
        if(getResources().getString(R.string.channel_5kanal).equalsIgnoreCase(channelName)){
            return Channels.FIVE_KANAL.getVideoId();
        }
        if(getResources().getString(R.string.channel_hromadske).equalsIgnoreCase(channelName)){
            return Channels.HROMADSKE.getVideoId();
        }
        if(getResources().getString(R.string.channel_112).equalsIgnoreCase(channelName)){
            return Channels.UKRAINA112.getVideoId();
        }
        if(getResources().getString(R.string.channel_24channel).equalsIgnoreCase(channelName)){
            return Channels.CHANNEL24.getVideoId();
        }
        if(getResources().getString(R.string.channel_ZIK).equalsIgnoreCase(channelName)){
            return Channels.ZIK.getVideoId();
        }
        if(getResources().getString(R.string.channel_euronews_eng).equalsIgnoreCase(channelName)){
            return Channels.EURONEWS_ENG.getVideoId();
        }
        throw  new IllegalArgumentException("");
    }
}
