package ua.tools.escondido.tvprogram;


import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import java.util.List;

import ua.tools.escondido.tvprogram.data.News;
import ua.tools.escondido.tvprogram.services.NewsService;
import ua.tools.escondido.tvprogram.services.impl.NewsServiceImpl;
import static org.junit.Assert.*;


public class NewsServiceTest {

    @Test
    public void loadNewsTest(){
        NewsService newsService = new NewsServiceImpl();
        List<News> newsList = newsService.getNews();
        assertThat(newsList, Is.is(IsNull.notNullValue()));
    }
}
