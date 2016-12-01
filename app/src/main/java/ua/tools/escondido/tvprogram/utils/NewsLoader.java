package ua.tools.escondido.tvprogram.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ua.tools.escondido.tvprogram.data.Channels;

public class NewsLoader {

    public String load() {
        StringBuilder content = new StringBuilder();
        HttpURLConnection conn = null;
        BufferedReader input = null;
        try {
            URL url = new URL("http://tvgid.ua/i/uploads/news.xml");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            input = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "windows-1251"));
            String str;
            while (null != (str = input.readLine())) {
                content.append(str).append("\r\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert input != null;
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }
        return content.toString();
    }
}
