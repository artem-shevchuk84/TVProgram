package ua.tools.escondido.tvprogram.services.parser;

import ua.tools.escondido.tvprogram.data.Channels;

public class StbContentParser extends ChannelContentParser {

    @Override
    public Channels getChannel() {
        return Channels.STB;
    }

}
