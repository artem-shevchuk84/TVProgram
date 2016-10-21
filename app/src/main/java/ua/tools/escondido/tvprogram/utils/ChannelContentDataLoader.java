package ua.tools.escondido.tvprogram.utils;

import org.apache.commons.io.IOUtils;
import ua.tools.escondido.tvprogram.data.Channels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

public class ChannelContentDataLoader {

    public String loadContent(Channels channel, Date date) throws IOException {
        String content = null;
        HttpURLConnection conn = null;
        BufferedReader input = null;
        try {//TODO: Currently method return data just for current day!
            URL url = new URL(channel.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(channel.getMethod());

            input = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), channel.getEncoding()));
            StringBuilder contentB = new StringBuilder();
            String str;
            while (null != (str = input.readLine())) {
                contentB.append(str).append("\r\n");
            }
            content = contentB.toString();
            content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    content.substring(content.indexOf(channel.getStartMetric()), content.indexOf(channel.getEndMetric()));

        } catch (ProtocolException e) {
            e.printStackTrace();
        } finally {
            input.close();
            conn.disconnect();
        }
        return content;
    }
}
