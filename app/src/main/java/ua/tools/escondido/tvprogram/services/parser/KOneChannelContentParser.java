package ua.tools.escondido.tvprogram.services.parser;


import ua.tools.escondido.tvprogram.data.Channels;

public class KOneChannelContentParser extends ChannelContentParser{

    @Override
    public Channels getChannel() {
        return Channels.K1;
    }
}

