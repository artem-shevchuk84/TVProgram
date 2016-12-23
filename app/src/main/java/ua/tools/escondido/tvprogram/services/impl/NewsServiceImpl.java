package ua.tools.escondido.tvprogram.services.impl;

import java.util.List;

import ua.tools.escondido.tvprogram.data.News;
import ua.tools.escondido.tvprogram.services.NewsService;
import ua.tools.escondido.tvprogram.services.parser.NewsParser;
import ua.tools.escondido.tvprogram.services.loader.NewsLoader;


public class NewsServiceImpl implements NewsService {

    @Override
    public List<News> getNews() {
        NewsLoader loader = new NewsLoader();
        NewsParser parser = new NewsParser();
        String content = loader.load();
        return parser.parse(content);
    }
}
