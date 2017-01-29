package ua.tools.escondido.tvprogram.services.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.data.ProgramInfo;

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

public class ChannelContentParser {

    public Channels getChannel(){
        return null;
    }

    public List<ProgramEvent> getPrograms(String content){
        List<ProgramEvent> events = null;
        try {
            content = cleanUp(content);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(content));
            Document doc = db.parse(is);

            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.compile(getBaseXPath() + "/tr").evaluate(doc, XPathConstants.NODESET);
            events = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                String programInfoPath = null;
                Node node = nodeList.item(i);
                int index = 2+i;
                String basePath = getBaseXPath() + "/tr["+ index +"]/td/table/tr/td";
                Node timeNode = (Node) xPath.compile(basePath + "[@class = 'time']").evaluate(node, XPathConstants.NODE);
                Node itemNode = (Node) xPath.compile(basePath + "[@class = 'item']/a").evaluate(node, XPathConstants.NODE);
                Node channelNameNode = (Node) xPath.compile(basePath + "[@class = 'item']/a[2]").evaluate(node, XPathConstants.NODE);
                if(itemNode == null){
                    itemNode = (Node) xPath.compile(basePath + "[@class = 'item']").evaluate(node, XPathConstants.NODE);
                } else{
                    Node href = itemNode.getAttributes().getNamedItem("href");
                    if(href != null) {
                        programInfoPath = itemNode.getAttributes().getNamedItem("href").getTextContent();
                    }
                }
                if(timeNode != null && itemNode != null) {
                    String programmName = itemNode.getFirstChild().getTextContent();
                    if (channelNameNode != null){
                        programmName = programmName.concat(" " + channelNameNode.getFirstChild().getTextContent());
                    }
                    ProgramEvent event = new ProgramEvent(timeNode.getFirstChild().getTextContent(),
                            programmName, programInfoPath);
                    events.add(event);
                }
            }
        } catch (ParserConfigurationException | IOException | XPathExpressionException | SAXException e) {
            //TODO: add log
        }
        return events;
    }

    public ProgramInfo getProgramInfo(String content) {
        ProgramInfo programInfo = null;
        try {
            content = cleanUpInfo(content);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(content));
            Document doc = db.parse(is);

            programInfo = new ProgramInfo();

            XPath xPath = XPathFactory.newInstance().newXPath();
            Node programNameNode = (Node) xPath.compile("/table/tr/td/table/tr/td/h1").evaluate(doc, XPathConstants.NODE);
            programInfo.setProgramName(programNameNode.getTextContent());

            String expression = ".//div[@id='ncnt']/p";
            Node baseNode = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);
            programInfo.setProgramDescription(baseNode.getTextContent());

            Node imagePathNode = (Node) xPath.compile(".//div[@id='ncnt']//img").evaluate(doc, XPathConstants.NODE);
            if(imagePathNode != null){
                String imageURL = imagePathNode.getAttributes().getNamedItem("src").getTextContent();
                if(!imageURL.contains("http")){
                    imageURL = "https:" + imageURL;
                }else{
                    imageURL = imageURL.replace("http","https").replace(" ","%20");
                }
                programInfo.setImagePath(imageURL);
            }


        } catch (ParserConfigurationException | IOException | XPathExpressionException | SAXException e) {
            //TODO: add log
        }
        return programInfo;
    }


    protected String cleanUp(String content) {
        return content.replace("<img","img").
                replace("&nbsp;","").
                replace(" language=javascript","");
    }

    protected String cleanUpInfo(String content) {
        return content.replace("<br>","").
                replace("&nbsp;","").
                replace("</br>","").
                replace("<br />","").
                replace("&ndash;","-").
                replace("&rsquo;","'").
                replace("&#039;","'");
    }

    protected String getBaseXPath(){
        return "/table/tr[2]/td/table/tr/td/table[2]";
    }
}
