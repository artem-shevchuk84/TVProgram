package ua.tools.escondido.tvprogram;

import android.content.Context;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.data.ProgramInfo;
import ua.tools.escondido.tvprogram.mock.TVMockContext;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.utils.DateUtils;

import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(DataProviderRunner.class)
public class ChannelProgramServiceTest {

    private static ChannelProgramService channelProgramService;
    private static Context context;

    @BeforeClass
    public static void setup(){
        context = new TVMockContext();
    }

    @Test
    @UseDataProvider("parserDataProvider")
    public void getChannelProgramContentTest(Channels channels) throws Exception {
        ChannelContentParser parser = new ChannelContentParser();
        parser.setChannels(channels);
        channelProgramService = new ChannelProgramServiceImpl<>(context, parser);
        List<ProgramEvent> programEvents = channelProgramService.getChannelProgram(null);
        assertThat(programEvents, Is.is(IsNull.notNullValue()));
    }

    @Test
    @UseDataProvider("parserDataProvider")
    public void getChannelProgramContentByDateTest(Channels channels) throws Exception {
        ChannelContentParser parser = new ChannelContentParser();
        parser.setChannels(channels);
        channelProgramService = new ChannelProgramServiceImpl<>(context, parser);
        String formattedDate = DateUtils.formatChannelAccessDate(DateUtils.getToday());
        List<ProgramEvent> programEvents = channelProgramService.getChannelProgram(formattedDate);
        assertThat(programEvents, Is.is(IsNull.notNullValue()));
    }

    @Test
    public void getChannelProgramInfoTest() throws Exception {
        channelProgramService = new ChannelProgramServiceImpl<>(context, new ChannelContentParser());
        ProgramInfo programInfo = channelProgramService.getProgramInfo("/entertainment/29096/mastershef/");
        assertThat(programInfo, Is.is(IsNull.notNullValue()));
        programInfo = channelProgramService.getProgramInfo("/kids/44709/horton/");
        assertThat(programInfo, Is.is(IsNull.notNullValue()));
    }

    @DataProvider
    public static Object[][] parserDataProvider() {
        // @formatter:off
        return new Object[][] {
                { Channels.NOVIY_CANAL },
                { Channels.STB },
                { Channels.ICTV },
                { Channels.TV_SERIALS },
        };
    }

}