package ua.tools.escondido.tvprogram;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.widget.TextView;
import org.xml.sax.SAXException;
import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.parser.NovyiTvContentParser;
import ua.tools.escondido.tvprogram.utils.Constants;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by artem.shevchuk on 10/19/2016.
 */
public class ChannelProgramListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String channelName = intent.getStringExtra(Constants.CHANNEL_NAME);
        TextView channelNameText = (TextView) findViewById(R.id.channel_name);
        channelNameText.setText(channelName);
        ChannelProgramService channelProgramService = new ChannelProgramServiceImpl<>(new NovyiTvContentParser());
        if(Channels.NOVIY_CANAL.name().equalsIgnoreCase(channelName)){
            channelProgramService = new ChannelProgramServiceImpl<>(new NovyiTvContentParser());
        }
        try {
            List<ProgramEvent> programEvents = channelProgramService.getChannelProgram(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
