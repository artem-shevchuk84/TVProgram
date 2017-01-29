package ua.tools.escondido.tvprogram.services.parser.tv;

import ua.tools.escondido.tvprogram.data.Channels;


public class ShowContentParser extends BaseTVContentParser {
    @Override
    public Channels getChannel() {
        return Channels.TV_SHOW;
    }
}
