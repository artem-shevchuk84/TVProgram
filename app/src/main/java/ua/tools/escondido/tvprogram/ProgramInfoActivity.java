package ua.tools.escondido.tvprogram;


import android.app.Activity;
import android.app.ProgressDialog;
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
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.utils.Constants;

public class ProgramInfoActivity extends Activity {

    private ChannelProgramService channelProgramService = new ChannelProgramServiceImpl<>(this, new ChannelContentParser());
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_info);
        Intent intent = getIntent();
        String programInfoPath = intent.getStringExtra(Constants.PROGRAM_INFO_PATH);

        ImageView programImage = (ImageView) findViewById(R.id.program_image);
        TextView name = (TextView) findViewById(R.id.program_title);
        TextView description = (TextView) findViewById(R.id.program_description);

        Button backBtn = (Button) findViewById(R.id.toolbar_btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgramInfoActivity.this, ChannelProgramListActivity.class);
                startActivity(intent);
            }
        });

        try {
            dialog = new ProgressDialog(this);
            //ProgramInfo programInfo = getStub();
            ProgramInfo programInfo = new LoadProgramInfoData().execute(new String[] {programInfoPath}).get();
            if (programInfo != null) {
                Picasso.with(getApplicationContext())
                        .load(programInfo.getImagePath())
                        .error(R.drawable.ic_menu_gallery)
                        .into(programImage);
                name.setText(programInfo.getProgramName());
                description.setText(programInfo.getProgramDescription());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class LoadProgramInfoData extends AsyncTask<String, Void ,ProgramInfo> {

        @Override
        protected ProgramInfo doInBackground(String... params) {
            return channelProgramService.getProgramInfo(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Processing...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(ProgramInfo programInfo) {
            super.onPostExecute(programInfo);
            dialog.dismiss();
        }
    }

    ProgramInfo getStub() throws InterruptedException,ExecutionException {
        ProgramInfo programInfo = new ProgramInfo();
        programInfo.setProgramName("Test program");
        programInfo.setImagePath("http://tvgid.ua/i/uploads/Image/1(12830).jpg");
        programInfo.setProgramDescription("\"Ревізор\" - оригінальне телевізійне соціальне реаліті-шоу Нового каналу. Знімальна група проекту разом із ведучим Вадимом Абрамовим і шеф-редактором Анною Жижею проводять перевірки готелів, ресторанів, супермаркетів, інших точок сфери обслуговування.");
        return programInfo;
    }
}
