package ua.tools.escondido.tvprogram.data.adapter;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.services.SettingsService;
import ua.tools.escondido.tvprogram.services.impl.SettingsServiceImpl;

public class ProgramsListAdapter extends ArrayAdapter<ProgramEvent> {

    private final Context context;
    private final List<ProgramEvent>  values;
    private SettingsService settingsService;
    private String channelName;
    private boolean isScheduleAvailable;

    public ProgramsListAdapter(Context context, List<ProgramEvent> values, String channelName, boolean isScheduleAvailable) {
        super(context, R.layout.program_list, values);
        this.context = context;
        this.values = values;
        this.channelName = channelName;
        this.isScheduleAvailable = isScheduleAvailable;
        settingsService = new SettingsServiceImpl();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.program_list, parent, false);
        ImageView icon = (ImageView) rowView.findViewById(R.id.program_icon);
        TextView programTime = (TextView) rowView.findViewById(R.id.program_time);
        TextView programName = (TextView) rowView.findViewById(R.id.program_name);
        programTime.setText(values.get(position).getTime());
        programName.setText(values.get(position).getName());
        String programInfoPath = values.get(position).getProgramInfoPath();

        if(programInfoPath != null){
            programName.setTextColor(ColorStateList.valueOf(Color.BLUE));
            if(isScheduleAvailable) {
                if (settingsService.isInNotification(context, channelName, programInfoPath)) {
                    icon.setImageResource(R.mipmap.ic_alarm_blue_18dp);
                } else {
                    icon.setImageResource(R.mipmap.ic_alarm_black_18dp);
                }
            }
        }
        return rowView;
    }

}
