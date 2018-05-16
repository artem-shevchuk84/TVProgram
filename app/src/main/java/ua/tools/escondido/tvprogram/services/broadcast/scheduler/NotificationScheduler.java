package ua.tools.escondido.tvprogram.services.broadcast.scheduler;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.activity.HomeActivity;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.data.factory.ChannelContentParserFactory;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.SettingsService;
import ua.tools.escondido.tvprogram.services.broadcast.alarm.ProgramNotificationAlarm;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.impl.SettingsServiceImpl;
import ua.tools.escondido.tvprogram.services.loader.async.ProgramListDataLoader;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.utils.Constants;
import ua.tools.escondido.tvprogram.utils.DateUtils;

public class NotificationScheduler extends BroadcastReceiver{

    private static final String TIME_DELIMITER = ":";
    private ChannelProgramService channelProgramService;
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

            new ProgramListDataLoader(context, channelProgramService, false, new AsyncTaskCallback<List<ProgramEvent>>() {
                @Override
                public void run(List<ProgramEvent> result) {
                    setNotificationIfPresent(context, notificationPrograms.get(channelName), result);
                }

                @Override
                public void handleError() {

                }

            }).execute(data);

        }
    }

    private void setNotificationIfPresent(Context context, List<String> programsToNotif, List<ProgramEvent> programs) {
        for(String program : programsToNotif){
            List<ProgramEvent> programEvents = find(program, programs);
            if(!programEvents.isEmpty()) {
                for(ProgramEvent programEvent : programEvents) {
                    setNotificationAlert(context, programEvent, new Random().nextInt(100));
                }
            }
        }
    }

    private void setNotificationAlert(Context context, ProgramEvent programEvent, int notificationId) {
        Calendar calendar = null;
        String time = programEvent.getTime();
        int hour,minutes;
        int delimiterIdx = time.indexOf(TIME_DELIMITER);
        if(delimiterIdx > 0) {
            hour = Integer.parseInt(time.substring(0, delimiterIdx));
            minutes = Integer.parseInt(time.substring(delimiterIdx + 1));
            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.MINUTE, -15);
        }
        if(calendar != null) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(programEvent.getName() + " " + context.getString(R.string.notification_union) + " " + programEvent.getTime())
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap())
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            Intent intent = new Intent(context, HomeActivity.class);
            PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setContentIntent(activity);

            Notification notification = builder.build();

            Intent alarm = new Intent(context, ProgramNotificationAlarm.class);
            alarm.putExtra(Constants.NOTIFICATION_ID, notificationId);
            alarm.putExtra(Constants.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, alarm, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    private List<ProgramEvent> find(String program, List<ProgramEvent> programs) {
        List<ProgramEvent> result = new ArrayList<>();
        for(ProgramEvent programEvent : programs){
            if(program.equals(programEvent.getProgramInfoPath())){
                result.add(programEvent);
            }
        }
        return result;
    }
}
