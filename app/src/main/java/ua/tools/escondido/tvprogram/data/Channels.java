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
    K1(HTTPS_BASE_PATH + "/channels/k1/", GET,ENCODING_1251,START_METRIC,"<noindex>"),


    TV_SERIALS(HTTPS_BASE_PATH + "/serial/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    TV_FILMS(HTTPS_BASE_PATH + "/film/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    TV_ENTERTAINMENT(HTTPS_BASE_PATH + "/entertainment/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    TV_INFORMATION(HTTPS_BASE_PATH + "/information/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    TV_SOCIOPOLITICAL(HTTPS_BASE_PATH + "/politika/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    TV_SHOW(HTTPS_BASE_PATH + "/talk-show/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    TV_SPORT(HTTPS_BASE_PATH + "/sport/", GET,ENCODING_1251,START_METRIC,"<noindex>"),
    TV_KID(HTTPS_BASE_PATH + "/kids/", GET,ENCODING_1251,START_METRIC,"<noindex>");

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
