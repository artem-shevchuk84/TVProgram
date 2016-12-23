package ua.tools.escondido.tvprogram.data;


public enum Channels {
    //NOVIY_CANAL("http://novy.tv/ua/tv/","POST","UTF-8","<div id=\"schedule\">","<!-- [END] TIMETABLE -->"),
    NOVIY_CANAL("https://tvgid.ua/channels/noviy_kanal/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    STB("https://tvgid.ua/channels/stb/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    ICTV("https://tvgid.ua/channels/ictv/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    ONE_PLUS_ONE("https://tvgid.ua/channels/1plus1/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    MEGA("https://tvgid.ua/channels/mega/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    UKRAINA("https://tvgid.ua/channels/ukraina/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    UA_PERVYJ("https://tvgid.ua/channels/ua_pervyj/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    INTER("https://tvgid.ua/channels/inter/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>"),
    FIVE_KANAL("https://tvgid.ua/channels/5kanal/","GET","windows-1251","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"container\">","<noindex>");

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
