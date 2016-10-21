package ua.tools.escondido.tvprogram.data;

/**
 * Created by artem.shevchuk on 10/19/2016.
 */
public class ProgramEvent {

    private String time;
    private String name;

    public ProgramEvent() {
    }

    public ProgramEvent(String time, String name) {
        this.time = time;
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
