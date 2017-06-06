package ua.tools.escondido.tvprogram.services.parser;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import ua.tools.escondido.tvprogram.data.News;

public class NewsParser {

    public List<News> parse(String content){
        List<News> news = null;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(content));
            Document doc = db.parse(is);

            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.compile("/rss/channel/item").evaluate(doc, XPathConstants.NODESET);
            news = new ArrayList<>();
            for (int i = 1; i <= nodeList.getLength(); i++) {
                News newsItem = new News();
                Node node = nodeList.item(i-1);
                String basePath = "/rss/channel/item["+ i +"]";
                Node title = (Node) xPath.compile(basePath + "/title").evaluate(node, XPathConstants.NODE);
                newsItem.setTitle(title.getTextContent());

                Node link = (Node) xPath.compile(basePath + "/link").evaluate(node, XPathConstants.NODE);
                newsItem.setLink(link.getTextContent());

                Node imagePath = (Node) xPath.compile(basePath + "/enclosure").evaluate(node, XPathConstants.NODE);
                String imageUrl = imagePath.getAttributes().getNamedItem("url").getTextContent();
                newsItem.setImagePath("https:" + imageUrl);

                Node publicationDate = (Node) xPath.compile(basePath + "/pubDate").evaluate(node, XPathConstants.NODE);
                newsItem.setPublicationDate(publicationDate.getTextContent());

                Node description = (Node) xPath.compile(basePath + "/description").evaluate(node, XPathConstants.NODE);
                newsItem.setDescription(description.getTextContent());

                news.add(newsItem);
            }
        } catch (ParserConfigurationException | IOException | XPathExpressionException | SAXException e) {
            //TODO: add log
        }
        return news;
    }

    public String parseNewsDescription(String content){
        content = content.substring(content.indexOf("<div id=\"ncnt\">"), content.indexOf("<div id=\"error-warning\">"));
        content = content + "</div></div></div>";
        return content.replace("<strong>Читайте також: </strong>","");
    }

    public Integer getPageCount(String content) {
        Integer result = 0;
        Integer index = content.indexOf("<div class=\"pagebar\">");
        if (index != -1) {
            String pagebarContext = content.substring(index);
            pagebarContext = pagebarContext.substring(0, pagebarContext.indexOf("</div>")).concat("</div>");
            pagebarContext = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + pagebarContext;

            try {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(pagebarContext));
                Document doc = db.parse(is);

                XPath xPath = XPathFactory.newInstance().newXPath();
                result = ((NodeList) xPath.compile("//a").evaluate(doc, XPathConstants.NODESET)).getLength() - 1;
            } catch (ParserConfigurationException | IOException | XPathExpressionException | SAXException e) {
                //TODO: add log
            }
        }

        return result;
    }
}
