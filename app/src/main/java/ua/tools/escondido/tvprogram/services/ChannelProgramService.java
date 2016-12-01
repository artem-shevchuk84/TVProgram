package ua.tools.escondido.tvprogram.services;

import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.data.ProgramInfo;

import java.util.List;


public interface ChannelProgramService {

    List<ProgramEvent> getChannelProgram(String date);

    ProgramInfo getProgramInfo(String path);
}
