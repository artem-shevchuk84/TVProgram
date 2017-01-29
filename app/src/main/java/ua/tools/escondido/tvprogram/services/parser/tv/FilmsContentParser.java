package ua.tools.escondido.tvprogram.services.parser.tv;

import ua.tools.escondido.tvprogram.data.Channels;


public class FilmsContentParser extends BaseTVContentParser {
    @Override
    public Channels getChannel() {
        return Channels.TV_FILMS;
    }
}
