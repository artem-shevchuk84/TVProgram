package ua.tools.escondido.tvprogram.services.parser;

import ua.tools.escondido.tvprogram.data.Channels;

public class InterContentParser extends ChannelContentParser {
    @Override
    public Channels getChannel() {
        return Channels.INTER;
    }
}
