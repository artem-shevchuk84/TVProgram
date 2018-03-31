package ua.tools.escondido.tvprogram.data;


import static ua.tools.escondido.tvprogram.utils.Constants.*;

public enum Channels {
    //NOVIY_CANAL("http://novy.tv/ua/tv/","POST","UTF-8","<div id=\"schedule\">","<!-- [END] TIMETABLE -->"),
    NOVIY_CANAL(HTTPS_BASE_PATH + "/channels/noviy_kanal/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    STB(HTTPS_BASE_PATH + "/channels/stb/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    ICTV(HTTPS_BASE_PATH + "/channels/ictv/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    ONE_PLUS_ONE(HTTPS_BASE_PATH + "/channels/1plus1/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    MEGA(HTTPS_BASE_PATH + "/channels/mega/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    UKRAINA(HTTPS_BASE_PATH + "/channels/ukraina/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    UA_PERVYJ(HTTPS_BASE_PATH + "/channels/ua_pervyj/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    INTER(HTTPS_BASE_PATH + "/channels/inter/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    FIVE_KANAL(HTTPS_BASE_PATH + "/channels/5kanal/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix", "r1L1J7E4oog"),
    K1(HTTPS_BASE_PATH + "/channels/k1/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    NTN(HTTPS_BASE_PATH + "/channels/ntn/", GET,ENCODING_1251,START_METRIC,"<div id=\"phoenix"),
    TET(HTTPS_BASE_PATH + "/channels/tet/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    TWO_PLUS_TWO(HTTPS_BASE_PATH + "/channels/2plus2/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    PIKSEL(HTTPS_BASE_PATH + "/channels/pixel/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    NLO(HTTPS_BASE_PATH + "/channels/nlo_tv/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    ENTERFILM(HTTPS_BASE_PATH + "/channels/enter_film/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    M1(HTTPS_BASE_PATH + "/channels/m1/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    K2(HTTPS_BASE_PATH + "/channels/k2/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    ZOOM(HTTPS_BASE_PATH + "/channels/zoom/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    ESPRESSO(HTTPS_BASE_PATH + "/channels/espresso_tv/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix", "N9_D6vKPUnM"),
    TONIS(HTTPS_BASE_PATH + "/channels/tonis/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    FOOTBALL1(HTTPS_BASE_PATH + "/channels/futbol_1/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    FOOTBALL2(HTTPS_BASE_PATH + "/channels/futbol_2/", GET,ENCODING_1251,START_METRIC, "<div id=\"phoenix"),
    HROMADSKE("_3Q9cIMJWOI"),
    UKRAINA112("T5WCWpRpHDQ"),
    CHANNEL24("kMKsjmhUt-w"),
    ZIK("hA5-bAgsFGs"),
    EURONEWS_ENG("Ah04R0okNbQ"),


    TV_SERIALS(HTTPS_BASE_PATH + "/serial/", GET,ENCODING_1251,START_METRIC, "<noindex>"),
    TV_FILMS(HTTPS_BASE_PATH + "/film/", GET,ENCODING_1251,START_METRIC, "<noindex>"),
    TV_ENTERTAINMENT(HTTPS_BASE_PATH + "/entertainment/", GET,ENCODING_1251,START_METRIC, "<noindex>"),
    TV_INFORMATION(HTTPS_BASE_PATH + "/information/", GET,ENCODING_1251,START_METRIC, "<noindex>"),
    TV_SOCIOPOLITICAL(HTTPS_BASE_PATH + "/politika/", GET,ENCODING_1251,START_METRIC, "<noindex>"),
    TV_SHOW(HTTPS_BASE_PATH + "/talk-show/", GET,ENCODING_1251,START_METRIC, "<noindex>"),
    TV_SPORT(HTTPS_BASE_PATH + "/sport/", GET,ENCODING_1251,START_METRIC, "<noindex>"),
    TV_KID(HTTPS_BASE_PATH + "/kids/", GET,ENCODING_1251,START_METRIC, "<noindex>");

    private String url;
    private String method;
    private String encoding;
    private String startMetric;
    private String endMetric;
    private String videoId;

    Channels(String url, String method, String encoding, String startMetric, String endMetric) {
        this.url = url;
        this.method = method;
        this.encoding = encoding;
        this.startMetric = startMetric;
        this.endMetric = endMetric;
    }

    Channels(String url, String method, String encoding, String startMetric, String endMetric, String videoId) {
        this.url = url;
        this.method = method;
        this.encoding = encoding;
        this.startMetric = startMetric;
        this.endMetric = endMetric;
        this.videoId = videoId;
    }

    Channels(String videoId) {
        this.videoId = videoId;
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

    public String getVideoId() {
        return videoId;
    }
}
