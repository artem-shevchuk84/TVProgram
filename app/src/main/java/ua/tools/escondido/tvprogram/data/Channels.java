package ua.tools.escondido.tvprogram.data;


public enum Channels {
    //NOVIY_CANAL("http://novy.tv/ua/tv/","POST","UTF-8","<div id=\"schedule\">","<!-- [END] TIMETABLE -->"),
    NOVIY_CANAL("http://tvgid.ua/channels/noviy_kanal/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    STB("http://tvgid.ua/channels/stb/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    ICTV("http://tvgid.ua/channels/ictv/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    ONE_PLUS_ONE("http://tvgid.ua/channels/1plus1/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    MEGA("http://tvgid.ua/channels/mega/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    UKRAINA("http://tvgid.ua/channels/ukraina/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    UA_PERVYJ("http://tvgid.ua/channels/ua_pervyj/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    INTER("http://tvgid.ua/channels/inter/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    FIVE_KANAL("http://tvgid.ua/channels/5kanal/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>");

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
