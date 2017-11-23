package ua.tools.escondido.tvprogram.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ua.tools.escondido.tvprogram.ChannelActivity;
import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.data.MenuCell;

/**
 * Created by artem.shevchuk on 11/22/2017.
 */

public class CellMenuAdapter extends BaseAdapter{

    private final Context context;
    private final MenuCell[] menuCells;

    public CellMenuAdapter(Context context, MenuCell[] menuCells) {
        this.context = context;
        this.menuCells = menuCells;
    }

    @Override
    public int getCount() {
        return menuCells.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        MenuCell menuCell = menuCells[position];
        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.menu_cell, null);
        }
        TextView label = (TextView)view.findViewById(R.id.menucell_title);
        label.setText(menuCell.getLabel());
        ImageView icon = (ImageView) view.findViewById(R.id.menucell_icon);
        icon.setImageResource(menuCell.getIconResId());
        return view;
    }
}
