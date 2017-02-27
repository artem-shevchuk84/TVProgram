package ua.tools.escondido.tvprogram.services.impl;

import android.content.Context;

import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.data.ProgramInfo;
import ua.tools.escondido.tvprogram.services.Cacheable;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.services.loader.ChannelContentDataLoader;
import ua.tools.escondido.tvprogram.utils.DateUtils;

import java.io.IOException;
import java.util.List;

public class ChannelProgramServiceImpl<T extends ChannelContentParser>
        implements ChannelProgramService{

    T channelContentParser;
    ChannelContentDataLoader channelContentDataLoader;
    Context context;
    Cacheable<List<ProgramEvent>> programSchedulCache = new InternalStorageCache<>();
    Cacheable<ProgramInfo> programInfoCache = new InternalStorageCache<>();

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
    public List<ProgramEvent> getChannelProgram(String date) {
        Channels channel = channelContentParser.getChannel();
        List<ProgramEvent> resultData = getCachedData(channel, date);
        if(resultData == null){
            String content = channelContentDataLoader.loadContent(channel, date);
            if(content != null){
                resultData = channelContentParser.getPrograms(content);
                setCacheData(channel, date, resultData);
            }

        }
        return resultData;
    }

    @Override
    public ProgramInfo getProgramInfo(String path) {
        ProgramInfo programInfo = getCachedData(path);
        if (programInfo == null) {
            String content = channelContentDataLoader.loadProgramInfoContent(path);
            if (content != null) {
                programInfo = channelContentParser.getProgramInfo(content);
                setCacheData(path, programInfo);
            }
        }
        return programInfo;
    }

    private List<ProgramEvent> getCachedData(Channels channel, String date) {
        List<ProgramEvent> programEvents = null;
        if(channel != null) {
            String key = generateKey(channel, date);
            try {
                if (context != null) {
                    programEvents = programSchedulCache.readObject(context, key, true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return programEvents;
    }

    private ProgramInfo getCachedData(String path) {
        ProgramInfo programInfo = null;
        String key = generateKey(path);
        if (context != null) {
            try {
                programInfo = programInfoCache.readObject(context, key, false);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return programInfo;
    }

    private void setCacheData(Channels channel, String date, List<ProgramEvent> data) {
        if(channel != null) {
            String key = generateKey(channel, date);
            try {
                programSchedulCache.writeObject(context, key, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setCacheData(String path, ProgramInfo data) {
        if(path != null) {
            String key = generateKey(path);
            try {
                programInfoCache.writeObject(context, key, data);
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

    private String generateKey(String path){
        return path.replace("/","_");
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
