package ua.tools.escondido.tvprogram.services.impl;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.activity.HomeActivity;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.services.AsyncTaskCallback;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.NotificationService;
import ua.tools.escondido.tvprogram.services.broadcast.alarm.ProgramNotificationAlarm;
import ua.tools.escondido.tvprogram.services.broadcast.scheduler.NotificationScheduler;
import ua.tools.escondido.tvprogram.services.loader.async.ProgramListDataLoader;
import ua.tools.escondido.tvprogram.utils.Constants;

public class NotificationServiceImpl implements NotificationService {

    @Override
    public void setNotification(final Context context, ChannelProgramService channelProgramService,
                                String[] data, final  List<String> channelNames, boolean handleError) {
        new ProgramListDataLoader(context, channelProgramService, handleError, new AsyncTaskCallback<List<ProgramEvent>>() {
            @Override
            public void run(List<ProgramEvent> result) {
                setNotificationIfPresent(context, channelNames, result);
            }

            @Override
            public void handleError() {

            }

        }).execute(data);
    }

    @Override
    public void setupScheduler(Context context, boolean hardReset) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent alarm = new Intent(context, NotificationScheduler.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(context, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if(alarmRunning && hardReset){
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.cancel(pendingIntent);
        }
        if(!alarmRunning) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void setNotificationIfPresent(Context context, List<String> programsToNotif, List<ProgramEvent> programs) {
        for(String program : programsToNotif){
            List<ProgramEvent> programEvents = find(program, programs);
            if(!programEvents.isEmpty()) {
                for(ProgramEvent programEvent : programEvents) {
                    setNotificationAlert(context, programEvent, programEvent.hashCode());
                }
            }
        }
    }

    private void setNotificationAlert(Context context, ProgramEvent programEvent, int notificationId) {
        Calendar calendar = null;
        String time = programEvent.getTime();
        int hour,minutes;
        int delimiterIdx = time.indexOf(Constants.TIME_DELIMITER);
        if(delimiterIdx > 0) {
            hour = Integer.parseInt(time.substring(0, delimiterIdx));
            minutes = Integer.parseInt(time.substring(delimiterIdx + 1));
            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.MINUTE, -15);
        }
        if(calendar != null && calendar.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {

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
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
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
