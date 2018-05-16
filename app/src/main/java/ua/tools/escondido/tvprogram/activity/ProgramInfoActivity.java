package ua.tools.escondido.tvprogram.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.ProgramInfo;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.SettingsService;
import ua.tools.escondido.tvprogram.services.impl.SettingsServiceImpl;
import ua.tools.escondido.tvprogram.services.loader.async.ProgramInfoDataLoader;
import ua.tools.escondido.tvprogram.utils.Constants;
import ua.tools.escondido.tvprogram.utils.Navigate;

public class ProgramInfoActivity extends BaseActivity {

    private String activityToBack;
    private boolean isScheduleAvailable = false;
    private SettingsService settingsService = new SettingsServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_info);
        Intent intent = getIntent();
        final String programInfoPath = intent.getStringExtra(Constants.PROGRAM_INFO_PATH);
        final String channelName = intent.getStringExtra(Constants.CHANNEL_NAME);
        activityToBack = intent.getStringExtra(Constants.BACK_ACTIVITY);
        if("Channel".equals(activityToBack)) {
            isScheduleAvailable = true;
        }
        final ImageView programImage = (ImageView) findViewById(R.id.program_image);
        final TextView name = (TextView) findViewById(R.id.program_title);
        final TextView description = (TextView) findViewById(R.id.program_description);

        homeBtn.setVisibility(View.GONE);

        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigate.goBack(ProgramInfoActivity.this, ProgramListActivity.class, activityToBack, channelName);
            }
        });

        if(isScheduleAvailable) {
            notifBtn.setVisibility(View.VISIBLE);
            if (settingsService.isInNotification(this, channelName, programInfoPath)) {
                notifBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_alarm_off_white_24dp, 0, 0);
            } else {
                notifBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_alarm_add_white_24dp, 0, 0);
            }
            notifBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (settingsService.isInNotification(ProgramInfoActivity.this, channelName, programInfoPath)) {
                        settingsService.deleteFromNotification(ProgramInfoActivity.this, channelName, programInfoPath);
                        notifBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_alarm_add_white_24dp, 0, 0);
                    } else {
                        settingsService.putNotification(ProgramInfoActivity.this, channelName, programInfoPath);
                        notifBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_alarm_off_white_24dp, 0, 0);
                    }
                }
            });
        }
        new ProgramInfoDataLoader(this, new AsyncTaskCallback<ProgramInfo>() {
            @Override
            public void run(ProgramInfo programInfo) {
                if(programInfo.getImagePath() != null && !programInfo.getImagePath().isEmpty()) {
                    Picasso.with(getBaseContext())
                            .load(programInfo.getImagePath())
                            .error(R.drawable.ic_menu_gallery)
                            .into(programImage);
                }
                name.setText(programInfo.getProgramName());
                description.setText(programInfo.getProgramDescription());
            }

            @Override
            public void handleError() {
                Navigate.goBack(ProgramInfoActivity.this, ProgramListActivity.class, activityToBack, channelName);
            }

        }).execute(programInfoPath);

    }

}
