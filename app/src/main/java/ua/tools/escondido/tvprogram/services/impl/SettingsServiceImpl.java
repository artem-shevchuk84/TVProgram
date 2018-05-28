package ua.tools.escondido.tvprogram.services.impl;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.tools.escondido.tvprogram.activity.ProgramInfoActivity;
import ua.tools.escondido.tvprogram.data.factory.ChannelContentParserFactory;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.NotificationService;
import ua.tools.escondido.tvprogram.services.SettingsService;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.utils.DateUtils;


public class SettingsServiceImpl implements SettingsService {

    private static final String SETTINGS_PREF = "SETTINGS_PREF";
    private static final String NOTIFICATION_SETTINGS = "NOTIFICATION_SETTINGS";
    private static final String DELIMITER = "::";

    @Override
    public void putNotification(Context context, String channelName, String programPath) {
        Set<String> notifProgramSet = new HashSet<>();
        SharedPreferences sharedPref = context.getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE);
        notifProgramSet = sharedPref.getStringSet(NOTIFICATION_SETTINGS, notifProgramSet);
        notifProgramSet.add(getNotifProgramPath(channelName, programPath));
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(NOTIFICATION_SETTINGS, notifProgramSet);
        editor.apply();

        setNotificationForToday(context, channelName, programPath);
    }

    @Override
    public void deleteFromNotification(Context context, String channelName, String programPath) {
        if (isInNotification(context, channelName, programPath)){
            Set<String> notifProgramSet = new HashSet<>();
            SharedPreferences sharedPref = context.getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE);
            notifProgramSet = sharedPref.getStringSet(NOTIFICATION_SETTINGS, notifProgramSet);
            notifProgramSet.remove(getNotifProgramPath(channelName, programPath));
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putStringSet(NOTIFICATION_SETTINGS, notifProgramSet);
            editor.apply();
        }
    }

    @Override
    public Map<String, List<String>> getAllNotifications(Context context) {
        Map<String, List<String>> programs = new HashMap<>();
        Set<String> notifProgramSet = getPrograms(context);
        String channelName;
        String programPath;
        List<String> programList;
        for(String path : notifProgramSet){
            int delimiterIdx = path.indexOf(DELIMITER);
            if(delimiterIdx > 0) {
                channelName = path.substring(0, delimiterIdx);
                programPath = path.substring(delimiterIdx + 2);
                programList = programs.get(channelName);
                if (programList == null) {
                    programList = new ArrayList<>();
                }
                programList.add(programPath);
                programs.remove(channelName);
                programs.put(channelName, programList);
            }
        }
        return programs;
    }

    @Override
    public boolean isInNotification(Context context, String channelName, String programPath) {
        Set<String> notifProgramSet = getPrograms(context);
        return notifProgramSet.contains(getNotifProgramPath(channelName, programPath));
    }

    private String getNotifProgramPath(String channelName, String programPath){
        return channelName.concat(DELIMITER).concat(programPath);
    }

    private Set<String> getPrograms(Context context){
        Set<String> notifProgramSet = new HashSet<>();
        SharedPreferences sharedPref = context.getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE);
        return sharedPref.getStringSet(NOTIFICATION_SETTINGS, notifProgramSet);
    }

    private void setNotificationForToday(Context context, String channelName, String programInfoPath) {
        NotificationService notificationService = new NotificationServiceImpl();
        ChannelContentParser channelContentParser = ChannelContentParserFactory.build(context, channelName);
        ChannelProgramService channelProgramService = new ChannelProgramServiceImpl<>(context, channelContentParser);
        String[] data = new String[] {DateUtils.formatChannelAccessDate(DateUtils.getToday())};
        List<String> channelLinks = new ArrayList<>(1);
        channelLinks.add(programInfoPath);
        notificationService.setNotification(context, channelProgramService, data, channelLinks, false);
    }
}
