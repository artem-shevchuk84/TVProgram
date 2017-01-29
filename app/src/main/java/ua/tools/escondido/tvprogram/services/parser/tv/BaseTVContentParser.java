package ua.tools.escondido.tvprogram.services.parser.tv;

import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;


public class BaseTVContentParser extends ChannelContentParser {
    @Override
    protected String getBaseXPath() {
        return "/table/tr[2]/td/table/tr/td[1]/table[2]";
    }

    @Override
    protected String cleanUp(String content) {
        return super.cleanUp(content).replace("title></div>","></div>");
    }
}
