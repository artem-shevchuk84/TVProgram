package ua.tools.escondido.tvprogram.services.parser;

import ua.tools.escondido.tvprogram.data.Channels;

public class UAPershiyContentParser extends ChannelContentParser {
    @Override
    public Channels getChannel() {
        return Channels.UA_PERVYJ;
    }
}
