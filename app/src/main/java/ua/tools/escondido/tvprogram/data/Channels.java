package ua.tools.escondido.tvprogram.data;


import static ua.tools.escondido.tvprogram.utils.Constants.*;

public enum Channels {
    //NOVIY_CANAL("http://novy.tv/ua/tv/","POST","UTF-8","<div id=\"schedule\">","<!-- [END] TIMETABLE -->"),
    NOVIY_CANAL(HTTPS_BASE_PATH + "/channels/noviy_kanal/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    STB(HTTPS_BASE_PATH + "/channels/stb/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    ICTV(HTTPS_BASE_PATH + "/channels/ictv/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    ONE_PLUS_ONE(HTTPS_BASE_PATH + "/channels/1plus1/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    MEGA(HTTPS_BASE_PATH + "/channels/mega/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    UKRAINA(HTTPS_BASE_PATH + "/channels/ukraina/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    UA_PERVYJ(HTTPS_BASE_PATH + "/channels/ua_pervyj/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    INTER(HTTPS_BASE_PATH + "/channels/inter/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    FIVE_KANAL(HTTPS_BASE_PATH + "/channels/5kanal/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    K1(HTTPS_BASE_PATH + "/channels/k1/", GET,ENCODING_1251,START_METRIC,"<noindex>");

    private String url;
    private String method;
    private String encoding;
    private String startMetric;
    private String endMetric;

    Channels(String url, String method, String encoding, String startMetric, String endMetric) {
        this.url = url;
        this.method = method;
        this.encoding = encoding;
        this.startMetric = startMetric;
        this.endMetric = endMetric;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getStartMetric() {
        return startMetric;
    }

    public String getEndMetric() {
        return endMetric;
    }

    public String getEncoding() {
        return encoding;
    }

}
