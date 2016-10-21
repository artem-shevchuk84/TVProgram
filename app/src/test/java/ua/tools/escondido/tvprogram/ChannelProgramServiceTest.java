package ua.tools.escondido.tvprogram;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.parser.NovyiTvContentParser;
import ua.tools.escondido.tvprogram.services.parser.StbContentParser;

import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

public class ChannelProgramServiceTest {

    private static ChannelProgramService channelProgramService;


    @Test
    public void getNovyiChannelProgramContentTest() throws Exception {
        channelProgramService = new ChannelProgramServiceImpl<>(new NovyiTvContentParser());
        List<ProgramEvent> programEvents = channelProgramService.getChannelProgram(null);
        assertThat(programEvents, Is.is(IsNull.notNullValue()));
    }

    @Test
    public void getStbChannelProgramContentTest() throws Exception {
        channelProgramService = new ChannelProgramServiceImpl<>(new StbContentParser());
        List<ProgramEvent> programEvents = channelProgramService.getChannelProgram(null);
        assertThat(programEvents, Is.is(IsNull.notNullValue()));
    }
}