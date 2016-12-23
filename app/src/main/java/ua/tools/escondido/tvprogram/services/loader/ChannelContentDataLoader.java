package ua.tools.escondido.tvprogram.services.loader;

import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChannelContentDataLoader {

    public String loadContent(Channels channel, String date) {
        String content = null;
        HttpURLConnection conn = null;
        BufferedReader input = null;
        String additionalPathByDate = "";
        if (date != null){
            additionalPathByDate = date+"/tmall/";
        }
        try {
            URL url = new URL(channel.getUrl()+additionalPathByDate);
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

        } catch (IOException e) {
            e.printStackTrace();
        } catch (StringIndexOutOfBoundsException siobe){
            siobe.printStackTrace();
        }
        finally {
            try {
                assert input != null;
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }
        return content;
    }

    public String loadProgramInfoContent(String path) {
        String content = null;
        HttpURLConnection conn = null;
        BufferedReader input = null;
        String token = "<!------------------------------ Channel body ------------------------------------------------->";

        try {
            URL url = new URL(Constants.HTTPS_BASE_PATH + path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            input = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), Constants.ENCODING_1251));
            StringBuilder contentB = new StringBuilder();
            String str;
            while (null != (str = input.readLine())) {
                contentB.append(str).append("\r\n");
            }
            content = contentB.toString();
            content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    content.substring(content.indexOf(token) + token.length(),
                            content.indexOf("<div id=\"error-warning\">"));
            content = content.concat("</div></td></tr></table></td></tr></table></td></tr></table>");

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
        return content;
    }

}
