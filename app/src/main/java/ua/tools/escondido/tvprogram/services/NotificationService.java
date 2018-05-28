package ua.tools.escondido.tvprogram.services;


import android.content.Context;

import java.util.List;

import ua.tools.escondido.tvprogram.services.loader.async.ProgramListDataLoader;

public interface NotificationService {

    /**
     * @author Escondido (c) 2017-2018
     * @param context - Application Context
     * @param channelProgramService
     * @param data - input data for run {@link ProgramListDataLoader}
     * @param channelNames - List of channel specified links
     * @param handleError - this flag should be false for background processes
     * @see ProgramListDataLoader
     */
    void setNotification(Context context, ChannelProgramService channelProgramService, String[] data,
                         List<String> channelNames, boolean handleError);

    void setupScheduler(Context context, boolean hardReset);
}
