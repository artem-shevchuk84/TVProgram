package ua.tools.escondido.tvprogram.data;

/**
 * Created by artem.shevchuk on 11/22/2017.
 */

public class MenuCell {

    int iconResId;
    String label;

    public MenuCell(int iconResId, String label) {
        this.iconResId = iconResId;
        this.label = label;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
