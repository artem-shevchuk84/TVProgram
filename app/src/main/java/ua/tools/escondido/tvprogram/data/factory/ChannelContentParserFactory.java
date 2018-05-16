package ua.tools.escondido.tvprogram.data.factory;


import android.content.Context;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.services.parser.tv.BaseTVContentParser;

public class ChannelContentParserFactory {

    public static ChannelContentParser build(Context context, String channelName){
        ChannelContentParser channelContentParser = new ChannelContentParser();
        if(context.getResources().getString(R.string.channel_novyj).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.NOVIY_CANAL);
        }else if(context.getResources().getString(R.string.channel_stb).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.STB);
        }else if(context.getResources().getString(R.string.channel_ictv).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.ICTV);
        }else if(context.getResources().getString(R.string.channel_one_plus_one).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.ONE_PLUS_ONE);
        }else if(context.getResources().getString(R.string.channel_mega).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.MEGA);
        }else if(context.getResources().getString(R.string.channel_ukraina).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.UKRAINA);
        }else if(context.getResources().getString(R.string.channel_ua_pervyj).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.UA_PERVYJ);
        }else if(context.getResources().getString(R.string.channel_inter).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.INTER);
        }else if(context.getResources().getString(R.string.channel_5kanal).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.FIVE_KANAL);
        }else if(context.getResources().getString(R.string.channel_k1).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.K1);
        }else if(context.getResources().getString(R.string.channel_ntn).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.NTN);
        }else if(context.getResources().getString(R.string.channel_tet).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.TET);
        }else if(context.getResources().getString(R.string.channel_two_plus_two).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.TWO_PLUS_TWO);
        }else if(context.getResources().getString(R.string.channel_piksel).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.PIKSEL);
        }else if(context.getResources().getString(R.string.channel_nlo).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.NLO);
        }else if(context.getResources().getString(R.string.channel_enterfilm).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.ENTERFILM);
        }else if(context.getResources().getString(R.string.channel_m1).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.M1);
        }else if(context.getResources().getString(R.string.channel_k2).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.K2);
        }else if(context.getResources().getString(R.string.channel_zoom).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.ZOOM);
        }else if(context.getResources().getString(R.string.channel_espresso).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.ESPRESSO);
        }else if(context.getResources().getString(R.string.channel_tonis).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.TONIS);
        }else if(context.getResources().getString(R.string.channel_football1).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.FOOTBALL1);
        }else if(context.getResources().getString(R.string.channel_football2).equalsIgnoreCase(channelName)){
            channelContentParser.setChannels(Channels.FOOTBALL2);
        }

        else if(context.getResources().getString(R.string.tv_serials).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_SERIALS);
        }else if(context.getResources().getString(R.string.tv_entertainment).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_ENTERTAINMENT);
        }else if(context.getResources().getString(R.string.tv_information).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_INFORMATION);
        }else if(context.getResources().getString(R.string.tv_sociopolitical).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_SOCIOPOLITICAL);
        }else if(context.getResources().getString(R.string.tv_show).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_SHOW);
        }else if(context.getResources().getString(R.string.tv_sport).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_SPORT);
        }else if(context.getResources().getString(R.string.tv_kid).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_KID);
        }else if(context.getResources().getString(R.string.tv_films).equalsIgnoreCase(channelName)){
            channelContentParser = new BaseTVContentParser();
            channelContentParser.setChannels(Channels.TV_FILMS);
        }
        return  channelContentParser;
    }
}
