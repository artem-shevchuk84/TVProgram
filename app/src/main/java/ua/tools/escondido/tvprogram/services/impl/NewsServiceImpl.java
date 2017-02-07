package ua.tools.escondido.tvprogram.services.impl;

import java.util.List;

import ua.tools.escondido.tvprogram.data.News;
import ua.tools.escondido.tvprogram.services.NewsService;
import ua.tools.escondido.tvprogram.services.parser.NewsParser;
import ua.tools.escondido.tvprogram.services.loader.NewsLoader;
import ua.tools.escondido.tvprogram.utils.Constants;


public class NewsServiceImpl implements NewsService {

    private NewsLoader loader = new NewsLoader();
    private NewsParser parser = new NewsParser();

    @Override
    public List<News> getNews() {
        String content = loader.load(Constants.HTTPS_BASE_PATH + "/i/uploads/news.xml");
        return parser.parse(content);
    }

    @Override
    public News getNewsInfo(String path) {
        News news = new News();
        path = path.replace("%2F","/");
        Integer index = path.indexOf("news");
        if(index == -1){
            index = path.indexOf("fotor");
            if(index == -1){
                throw new IllegalArgumentException("Path \""+path+"\" is wrong");
            }
        }
        path = path.substring(index).replace("/p0/", "");
        String content = loader.load("https://tvgid.ua/"+path);
        Integer pageCount = parser.getPageCount(content);
        String newsDescription = parser.parseNewsDescription(content);
        for(int i = 0; i<pageCount; i++){
            content = loader.load("https://tvgid.ua/"+path+"/p"+(i+2));
            newsDescription = newsDescription.concat(parser.parseNewsDescription(content));
        }
        news.setDescription(newsDescription);
        return news;
    }
}
