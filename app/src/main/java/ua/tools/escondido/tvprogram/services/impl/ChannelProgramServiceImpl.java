package ua.tools.escondido.tvprogram.services.impl;

import android.app.Application;
import android.content.Context;

import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.data.ProgramInfo;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.services.loader.ChannelContentDataLoader;
import ua.tools.escondido.tvprogram.utils.DateUtils;
import ua.tools.escondido.tvprogram.utils.InternalStorage;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ChannelProgramServiceImpl<T extends ChannelContentParser> implements ChannelProgramService{

    T channelContentParser;
    ChannelContentDataLoader channelContentDataLoader;
    Context context;

    {
        channelContentDataLoader = new ChannelContentDataLoader();
    }

    public ChannelProgramServiceImpl() {
    }

    public ChannelProgramServiceImpl(Context context, T channelContentParser) {
        this.context = context;
        this.channelContentParser = channelContentParser;
    }

    @Override
    public List<ProgramEvent> getChannelProgram(String date){
        Channels channel = channelContentParser.getChannel();
        List<ProgramEvent> resultData = getCachedData(channel, date);
        if(resultData == null){
            String content = null;
            content = channelContentDataLoader.loadContent(channel, date);
            if(content != null){
                resultData = channelContentParser.getPrograms(content);
                setCacheData(channel, date, resultData);
            }

        }
        return resultData;
    }

    @Override
    public ProgramInfo getProgramInfo(String path) {
        ProgramInfo programInfo = null;
        String content = channelContentDataLoader.loadProgramInfoContent(path);
        if(content != null){
            programInfo = channelContentParser.getProgramInfo(content);
        }
        return programInfo;
    }

    private List<ProgramEvent> getCachedData(Channels channel, String date) {
        List<ProgramEvent> programEvents = null;
        if(channel != null) {
            String key = generateKey(channel, date);
            try {
                if (context != null) {
                    programEvents = (List<ProgramEvent>) InternalStorage.readObject(context, key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return programEvents;
    }

    private void setCacheData(Channels channel, String date, List<ProgramEvent> data) {
        if(channel != null) {
            String key = generateKey(channel, date);
            try {
                InternalStorage.writeObject(context, key, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateKey(Channels channel, String date){
        if(date == null){
            date = DateUtils.formatChannelAccessDate(DateUtils.getToday());
        }
        return channel.name().concat(date);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
