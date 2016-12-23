package ua.tools.escondido.tvprogram.data.adapter;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.ProgramEvent;

public class ProgramsListAdapter extends ArrayAdapter<ProgramEvent> {

    private final Context context;
    private final List<ProgramEvent>  values;

    public ProgramsListAdapter(Context context, List<ProgramEvent> values) {
        super(context, R.layout.program_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.program_list, parent, false);
        TextView programTime = (TextView) rowView.findViewById(R.id.program_time);
        TextView programName = (TextView) rowView.findViewById(R.id.program_name);
        programTime.setText(values.get(position).getTime());
        programName.setText(values.get(position).getName());
        if(values.get(position).getProgramInfoPath() != null){
            programName.setTextColor(ColorStateList.valueOf(Color.BLUE));
        }
        return rowView;
    }

}
