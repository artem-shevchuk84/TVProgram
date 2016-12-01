package ua.tools.escondido.tvprogram.services.parser;


import ua.tools.escondido.tvprogram.data.Channels;

public class UkrainaTVContentParser extends ChannelContentParser {
    @Override
    public Channels getChannel() {
        return Channels.UKRAINA;
    }
}
