package ua.tools.escondido.tvprogram.services.parser;

import ua.tools.escondido.tvprogram.data.Channels;


public class NovyiTvContentParser extends ChannelContentParser {

    private static Channels CHANNEL = Channels.NOVIY_CANAL;

    @Override
    public Channels getChannel(){
        return CHANNEL;
    }

}
