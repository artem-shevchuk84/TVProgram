package ua.tools.escondido.tvprogram.services.impl;

import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.data.ProgramInfo;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.utils.ChannelContentDataLoader;

import java.util.List;

public class ChannelProgramServiceImpl<T extends ChannelContentParser> implements ChannelProgramService{

    T channelContentParser;
    ChannelContentDataLoader channelContentDataLoader;

    {
        channelContentDataLoader = new ChannelContentDataLoader();
    }

    public ChannelProgramServiceImpl() {
    }

    public ChannelProgramServiceImpl(T channelContentParser) {
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
        return null;
    }


}
