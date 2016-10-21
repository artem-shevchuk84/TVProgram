package ua.tools.escondido.tvprogram.services.parser;

import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;

import java.util.List;

public abstract class ChannelContentParser {

    public abstract Channels getChannel();

    public abstract List<ProgramEvent> getPrograms(String content);
}
