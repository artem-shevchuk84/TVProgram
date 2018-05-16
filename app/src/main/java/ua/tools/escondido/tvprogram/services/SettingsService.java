package ua.tools.escondido.tvprogram.services;


import android.content.Context;

import java.util.List;
import java.util.Map;

public interface SettingsService {

    /**
     * Add program by path to notification list.
     * @param programPath
     */
    void putNotification(Context context, String channelName, String programPath);

    /**
     * Delete program from notification list by path.
     * @param programPath
     */
    void deleteFromNotification(Context context, String channelName, String programPath);

    /**
     * Get all programs from notification list
     * @param context
     * @return map of programs by channel name
     */
    Map<String, List<String>> getAllNotifications(Context context);

    /**
     * Check the program is in notification list
     * @param programPath
     * @return true if the program is in notification list
     */
    boolean isInNotification(Context context, String channelName, String programPath);
}
