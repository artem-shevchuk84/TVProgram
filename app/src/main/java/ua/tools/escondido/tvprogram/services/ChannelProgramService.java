package ua.tools.escondido.tvprogram.services;

import org.xml.sax.SAXException;
import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by artem.shevchuk on 10/19/2016.
 */
public interface ChannelProgramService {

    List<ProgramEvent> getChannelProgram(Date date) throws IOException;
}
