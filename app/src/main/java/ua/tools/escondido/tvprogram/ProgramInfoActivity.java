package ua.tools.escondido.tvprogram;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

import ua.tools.escondido.tvprogram.data.ProgramInfo;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.loader.async.ProgramInfoDataLoader;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.utils.Constants;

public class ProgramInfoActivity extends Activity {

    private String activityToBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_info);
        Intent intent = getIntent();
        String programInfoPath = intent.getStringExtra(Constants.PROGRAM_INFO_PATH);
        final String channelName = intent.getStringExtra(Constants.CHANNEL_NAME);
        activityToBack = intent.getStringExtra(Constants.BACK_ACTIVITY);

        final ImageView programImage = (ImageView) findViewById(R.id.program_image);
        final TextView name = (TextView) findViewById(R.id.program_title);
        final TextView description = (TextView) findViewById(R.id.program_description);

        Button backBtn = (Button) findViewById(R.id.toolbar_btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgramInfoActivity.this, ProgramListActivity.class);
                intent.putExtra(Constants.CHANNEL_NAME, channelName);
                intent.putExtra(Constants.BACK_ACTIVITY, activityToBack);
                startActivity(intent);
            }
        });

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
        }).execute(programInfoPath);

    }

}
