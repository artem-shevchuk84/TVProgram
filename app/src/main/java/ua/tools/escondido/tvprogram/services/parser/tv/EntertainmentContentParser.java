package ua.tools.escondido.tvprogram.services.parser.tv;

import ua.tools.escondido.tvprogram.data.Channels;

public class EntertainmentContentParser extends BaseTVContentParser {

    @Override
    public Channels getChannel() {
        return Channels.TV_ENTERTAINMENT;
    }
}
