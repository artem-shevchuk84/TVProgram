package ua.tools.escondido.tvprogram.services.loader;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ua.tools.escondido.tvprogram.utils.Constants;

public class NewsLoader {

    public String load(String path) {
        StringBuilder content = new StringBuilder();
        HttpURLConnection conn = null;
        BufferedReader input = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            input = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), Constants.ENCODING_1251));
            String str;
            while (null != (str = input.readLine())) {
                content.append(str).append("\r\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(conn != null) {
                conn.disconnect();
            }
        }
        return content.toString();
    }
}
