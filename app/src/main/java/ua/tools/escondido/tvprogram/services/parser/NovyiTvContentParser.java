package ua.tools.escondido.tvprogram.services.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class NovyiTvContentParser extends ChannelContentParser {

    private static Channels CHANNEL = Channels.NOVIY_CANAL;

    @Override
    public Channels getChannel(){
        return CHANNEL;
    }

    @Override
    public List<ProgramEvent> getPrograms(String content) {
        List<ProgramEvent> events = null;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(content));
            Document doc = db.parse(is);

            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.compile("div/div/div/div/div[@class='day-programm current-day active']/div/a/div").evaluate(doc, XPathConstants.NODESET);
            events = new ArrayList<>();
            String time = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String value = node.getAttributes().getNamedItem("class").getTextContent();
                if ("dp-time".compareTo(value) == 0) {
                    time = node.getTextContent();
                } else if ("dp-name".compareTo(value) == 0) {
                    ProgramEvent event = new ProgramEvent(time,
                            node.getTextContent());
                    events.add(event);
                    time = "";
                }
            }
        } catch (ParserConfigurationException | IOException | XPathExpressionException | SAXException e) {
            //TODO: add log
        }
        return events;
    }
}
