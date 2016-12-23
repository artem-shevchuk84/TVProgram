package ua.tools.escondido.tvprogram.data;

import java.io.Serializable;

public class ProgramEvent implements Serializable{

    private String time;
    private String name;
    private String programInfoPath;

    public ProgramEvent() {
    }

    public ProgramEvent(String time, String name, String programInfoPath) {
        this.time = time;
        this.name = name;
        this.programInfoPath = programInfoPath;
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

    public String getProgramInfoPath() {
        return programInfoPath;
    }

    public void setProgramInfoPath(String programInfoPath) {
        this.programInfoPath = programInfoPath;
    }
}
