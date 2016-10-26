package ua.tools.escondido.tvprogram.services.impl;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.utils.ChannelContentDataLoader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by artem.shevchuk on 10/19/2016.
 */
public class ChannelProgramServiceImpl<T extends ChannelContentParser> implements ChannelProgramService{

    T channelContentParser;
    ChannelContentDataLoader channelContentDataLoader;

    {
        channelContentDataLoader = new ChannelContentDataLoader();
    }

    public ChannelProgramServiceImpl() {
    }

    public ChannelProgramServiceImpl(T channelContentParser) {
        this.channelContentParser = channelContentParser;
    }

    @Override
    public List<ProgramEvent> getChannelProgram(String date) throws IOException {
        Channels channel = channelContentParser.getChannel();
        List<ProgramEvent> resultData = getCachedData(channel, date);
        if(resultData == null){
            String content = channelContentDataLoader.loadContent(channel, date);
            if(content != null){
                resultData = channelContentParser.getPrograms(content);
            }
        }
        return resultData;
    }

    private List<ProgramEvent> getCachedData(Channels channel, String date) {
        return null;
    }

    //@Override
    public String getChannelProgram(Channels channel) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        URL url = new URL("http://novy.tv/ua/tv/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        //ByteArrayInputStream stream = new ByteArrayInputStream(IOUtils.toByteArray(conn.getInputStream()));
        String content = IOUtils.toString(conn.getInputStream());
        content = content.substring(content.indexOf("<div id=\"schedule\">"), content.indexOf("<!-- [END] TIMETABLE -->"));
        content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + content;

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(content));
        Document doc = db.parse(is);

        XPath xPath =  XPathFactory.newInstance().newXPath();
        String expression = "div/div/nav/ul/li/a";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

        List<String> days = new ArrayList<>(7);
        String activeDay = null;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            days.add(node.getTextContent());
            String value = node.getAttributes().getNamedItem("class").getTextContent();
            if("current".compareTo(value) == 0){
                activeDay = node.getAttributes().getNamedItem("data-day").getTextContent();
            }
        }

        nodeList = (NodeList) xPath.compile("div/div/div/div/div[@class='day-programm current-day active']/div/a/div").evaluate(doc, XPathConstants.NODESET);
        List<ProgramEvent> events = new ArrayList<>();
        String time = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            days.add(node.getTextContent());
            String value = node.getAttributes().getNamedItem("class").getTextContent();
            if("dp-time".compareTo(value) == 0){
                time = node.getTextContent();
            }
            else if("dp-name".compareTo(value) == 0){
                ProgramEvent event = new ProgramEvent(time,
                        node.getTextContent());
                events.add(event);
                time = "";
            }
        }
        return content;
    }

}
