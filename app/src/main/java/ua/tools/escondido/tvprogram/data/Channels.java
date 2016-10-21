package ua.tools.escondido.tvprogram.data;

/**
 * Created by artem.shevchuk on 10/19/2016.
 */
public enum Channels {
    NOVIY_CANAL("http://novy.tv/ua/tv/","POST","UTF-8","<div id=\"schedule\">","<!-- [END] TIMETABLE -->"),
    STB("http://tvgid.ua/channels/stb/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>");

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
