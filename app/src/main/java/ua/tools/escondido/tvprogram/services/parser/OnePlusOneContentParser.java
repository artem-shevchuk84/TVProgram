package ua.tools.escondido.tvprogram.services.parser;


import ua.tools.escondido.tvprogram.data.Channels;

public class OnePlusOneContentParser extends ChannelContentParser {

    @Override
    public Channels getChannel() {
        return Channels.ONE_PLUS_ONE;
    }
}
