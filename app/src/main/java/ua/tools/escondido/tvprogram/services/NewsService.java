package ua.tools.escondido.tvprogram.services;


import java.util.List;

import ua.tools.escondido.tvprogram.data.News;

public interface NewsService {

    List<News> getNews();
    News getNewsInfo(String path);
}
