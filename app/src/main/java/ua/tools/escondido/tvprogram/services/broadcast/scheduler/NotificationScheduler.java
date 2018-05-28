package ua.tools.escondido.tvprogram.services.broadcast.scheduler;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.tools.escondido.tvprogram.data.factory.ChannelContentParserFactory;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.NotificationService;
import ua.tools.escondido.tvprogram.services.SettingsService;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.impl.NotificationServiceImpl;
import ua.tools.escondido.tvprogram.services.impl.SettingsServiceImpl;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.utils.DateUtils;

public class NotificationScheduler extends BroadcastReceiver{

    private ChannelProgramService channelProgramService;
    private NotificationService notificationService = new NotificationServiceImpl();
    private SettingsService settingsService = new SettingsServiceImpl();

    @Override
    public void onReceive(final Context context, Intent intent) {
        String today = DateUtils.formatChannelAccessDate(DateUtils.getToday());
        final Map<String, List<String>> notificationPrograms = settingsService.getAllNotifications(context);
        Set<String> channelNames = notificationPrograms.keySet();

        for(final String channelName : channelNames) {
            ChannelContentParser channelContentParser = ChannelContentParserFactory.build(context, channelName);
            channelProgramService = new ChannelProgramServiceImpl<>(context, channelContentParser);

            String[] data = new String[] {today};
            notificationService.setNotification(context, channelProgramService, data, notificationPrograms.get(channelName), false);
        }
    }

}
