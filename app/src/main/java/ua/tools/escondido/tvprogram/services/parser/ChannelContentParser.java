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

public abstract class ChannelContentParser {

    public abstract Channels getChannel();

    public List<ProgramEvent> getPrograms(String content){
        List<ProgramEvent> events = null;
        try {
            content = cleanUp(content);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(content));
            Document doc = db.parse(is);

            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.compile("/table/tr[2]/td/table/tr/td/table[2]/tr").evaluate(doc, XPathConstants.NODESET);
            events = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                int index = 2+i;
                String basePath = "/table/tr[2]/td/table/tr/td/table[2]/tr["+ index +"]/td/table/tr/td";
                Node timeNode = (Node) xPath.compile(basePath + "[@class = 'time']").evaluate(node, XPathConstants.NODE);
                Node itemNode = (Node) xPath.compile(basePath + "[@class = 'item']/a").evaluate(node, XPathConstants.NODE);
                if(itemNode == null){
                    itemNode = (Node) xPath.compile(basePath + "[@class = 'item']").evaluate(node, XPathConstants.NODE);
                }
                if(timeNode != null && itemNode != null) {
                    ProgramEvent event = new ProgramEvent(timeNode.getFirstChild().getTextContent(),
                            itemNode.getFirstChild().getTextContent());
                    events.add(event);
                }
            }
        } catch (ParserConfigurationException | IOException | XPathExpressionException | SAXException e) {
            //TODO: add log
        }
        return events;
    }

    protected String cleanUp(String content) {
        return content.replace("<img","img").
                replace("&nbsp;","").
                replace(" language=javascript","");
    }
}
