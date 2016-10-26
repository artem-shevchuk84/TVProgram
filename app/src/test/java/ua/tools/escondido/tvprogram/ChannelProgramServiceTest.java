package ua.tools.escondido.tvprogram;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import ua.tools.escondido.tvprogram.data.Channels;
import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.services.ChannelProgramService;
import ua.tools.escondido.tvprogram.services.impl.ChannelProgramServiceImpl;
import ua.tools.escondido.tvprogram.services.parser.ChannelContentParser;
import ua.tools.escondido.tvprogram.services.parser.ICTVContentParser;
import ua.tools.escondido.tvprogram.services.parser.NovyiTvContentParser;
import ua.tools.escondido.tvprogram.services.parser.StbContentParser;
import ua.tools.escondido.tvprogram.utils.DateUtils;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(DataProviderRunner.class)
public class ChannelProgramServiceTest {

    private static ChannelProgramService channelProgramService;

    @Test
    @UseDataProvider("parserDataProvider")
    public void getChannelProgramContentTest(ChannelContentParser parser) throws Exception {
        channelProgramService = new ChannelProgramServiceImpl<>(parser);
        List<ProgramEvent> programEvents = channelProgramService.getChannelProgram(null);
        assertThat(programEvents, Is.is(IsNull.notNullValue()));
    }

    @Test
    @UseDataProvider("parserDataProvider")
    public void getChannelProgramContentByDateTest(ChannelContentParser parser) throws Exception {
        channelProgramService = new ChannelProgramServiceImpl<>(parser);
        Date date = new Date(System.currentTimeMillis() - 18000000);
        String formattedDate = DateUtils.formatChannelAccessDate(date);
        List<ProgramEvent> programEvents = channelProgramService.getChannelProgram(formattedDate);
        assertThat(programEvents, Is.is(IsNull.notNullValue()));
    }

    @DataProvider
    public static Object[][] parserDataProvider() {
        // @formatter:off
        return new Object[][] {
                { new NovyiTvContentParser() },
                { new StbContentParser() },
                { new ICTVContentParser() },
        };
    }
}