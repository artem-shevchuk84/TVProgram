package ua.tools.escondido.tvprogram.data;


import java.io.Serializable;

public class ProgramInfo implements Serializable{

    private String programName = "";
    private String imagePath;
    private String programDescription = "";

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }
}
